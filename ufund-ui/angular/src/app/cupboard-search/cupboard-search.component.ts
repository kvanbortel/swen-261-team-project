import { Component, HostListener } from '@angular/core';
import { BehaviorSubject, debounceTime, distinctUntilChanged, Observable, startWith, Subject, switchMap, take } from 'rxjs';
import { CupboardService } from '../cupboard.service';
import { Need } from '../Need';

import { AuthService } from '../storage/auth.service';

@Component({
  selector: 'app-cupboard-search',
  templateUrl: './cupboard-search.component.html',
  styleUrl: './cupboard-search.component.css'
})
export class CupboardSearchComponent {
  needs$ = new BehaviorSubject<Need[]>([]);

  private searchTerms = new Subject<string>();
  selected!: Need;

  constructor(private cupboardService: CupboardService, public authService: AuthService) {}

  private currentSearchTerm: string = "";
  public basketSwitch: boolean = false;

  // Push a search term into the observable stream.
  search(term: string): void {
    this.currentSearchTerm = term; 
    this.searchTerms.next(term);
  }

  selectNeed(data: Need) {
    console.log("Sent", data)
    this.selected = data;
  }

  updateNeeds(){
    this.cupboardService.searchNeeds(this.currentSearchTerm).subscribe({
      next: (response) => this.needs$.next(response) 
    });
  }

  isMobile = false;

  @HostListener('window:resize', ['$event'])
  onResize(event: any) {
    this.checkScreenSize();
  }
  checkScreenSize() {
    this.isMobile = window.innerWidth <= 600;
  }

  getSizeStyles(){
    if(this.authService.isAdmin() && !this.isMobile){
      return {
        "max-width" : "40vw",
        "min-width" : "40vw",
      };
    }
    return
  }

  ngOnInit(): void {
    this.checkScreenSize()
    let name = localStorage.getItem("name");
    if(name != null){
      this.authService.setName(name);
    }
    let image = localStorage.getItem("image")
    if(image != null){
      this.authService.setImage(image);
    }
    this.cupboardService.searchNeeds("").subscribe({ 
      next: (response) => this.needs$.next(response)
    });

    this.searchTerms.pipe(

      // first search is an empty string, should return all needs
      startWith(""),

      // wait 300ms after each keystroke before considering the term
      debounceTime(300),

      // ignore new term if same as previous term
      distinctUntilChanged(),

      // switch to new search observable each time the term changes
      switchMap((term: string) => this.cupboardService.searchNeeds(term))
    ).subscribe({
      next: (response) => this.needs$.next(response)  // Manually emit the result
    });
  }

  updateBasketSwitch() : void {
    this.basketSwitch = !this.basketSwitch
  }
}
