import { Component } from '@angular/core';

import { AuthService } from '../storage/auth.service';
import { BehaviorSubject } from 'rxjs';
import { BasketNeed } from '../BasketNeed';

@Component({
  selector: 'app-basket-list',
  templateUrl: './basket-list.component.html',
  styleUrl: './basket-list.component.css'
})
export class BasketListComponent {
  basketNeeds$ = new BehaviorSubject<BasketNeed[]>([]);

  constructor(public authService: AuthService) {}


  ngOnInit(): void {
    this.authService.getBasketNeeds().subscribe({
      next: (response) => { this.basketNeeds$.next(response); console.log(response)}
    });
  }
}
