import { Component, HostListener } from '@angular/core';

import { AuthService } from '../storage/auth.service';
import { async, BehaviorSubject } from 'rxjs';
import { BasketNeed } from '../BasketNeed';
import { Router } from '@angular/router';
import { ConfirmationDialogComponent } from '../confirmation-dialog/confirmation-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { CurrencyPipe } from '@angular/common';

@Component({
  selector: 'app-basket-list',
  templateUrl: './basket-list.component.html',
  styleUrl: './basket-list.component.css',
  providers: [CurrencyPipe],
})
export class BasketListComponent {
  basketNeeds$ = new BehaviorSubject<BasketNeed[]>([]);
  errored: boolean = false;
  changed: boolean = false;

  constructor(
    public authService: AuthService,
    public dialog: MatDialog,
    private router: Router,
    private currencyPipe: CurrencyPipe
  ) {
    const navigation = this.router.getCurrentNavigation();
    if (navigation?.extras.state) {
      this.errored = navigation.extras.state['error'];
      console.log('Error:', this.errored);
    } 
    else {
      console.log('No data received');
    }
  }

  isMobile = false;

  @HostListener('window:resize', ['$event'])
  onResize(event: any) {
    this.checkScreenSize();
  }
  checkScreenSize() {
    this.isMobile = window.innerWidth <= 600;
  }

  ngOnInit(): void {
    this.checkScreenSize()

    var lastBasket = JSON.parse(localStorage.getItem("basket")!) as BasketNeed[];

    this.authService.getBasketNeeds().subscribe({
      next: (response) => {
        this.changed = false;
        this.basketNeeds$.next(response);
        for(var i = 0; i < lastBasket.length; i++){
          if(i >= response.length || response[i].quantity < lastBasket[i].quantity){
            this.changed = true
          }
        }
      },
    });
  }

  onNeedDeleted(needToDelete: BasketNeed) {
    const currentNeeds = this.basketNeeds$.getValue();

    const updatedNeeds = currentNeeds.filter(
      (need) => need.need.id !== needToDelete.need.id
    );

    this.basketNeeds$.next(updatedNeeds);
    localStorage.setItem("basket", JSON.stringify(updatedNeeds))
    console.log("storage set to", updatedNeeds)
  }

  getTotalQuantity(): number {
    const currentNeeds = this.basketNeeds$.getValue();
    return currentNeeds.reduce((total, need) => total + need.quantity, 0);
  }

  getTotalCost(): number {
    const currentNeeds = this.basketNeeds$.getValue();
    return currentNeeds.reduce(
      (total, need) => total + need.need.cost * need.quantity,
      0
    );
  }

  getDynamicStyle(){
    if(this.changed && !this.isMobile){
      return {
        height: "41vh"
      }
    }
    return
  }

  checkout(): void {
    if (this.getTotalQuantity() > 0) {
      const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
        disableClose: false,
      });
      dialogRef.componentInstance.confirmMessage = `Are you sure you want to fund ${this.getTotalQuantity()} needs for ${this.currencyPipe.transform(
        this.getTotalCost(),
        'USD',
        'symbol',
        '1.2-2'
      )}?`;

      dialogRef.afterClosed().subscribe((result) => {
        console.log('The dialog was closed');
        if (result) {
          this.authService.checkoutBasket().subscribe((result2) => {
            if (result2) {

              const totalQuantity = this.getTotalQuantity();
              const totalCost = this.getTotalCost();
              localStorage.setItem("basket", JSON.stringify([]))

              this.router.navigate(['/post-checkout'], {
                state: {
                  amount: totalQuantity,
                  cost: totalCost,
                },
              });
            }
            else {
              console.log("error checking out in basket")
              this.router.navigate(['/post-checkout'], {
                state: {
                  error: true,
                },
              });
            }
          });
        }
      });
    }
  }
}
