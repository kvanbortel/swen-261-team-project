import { Component } from '@angular/core';
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

  ngOnInit(): void {
    if(this.authService.getName() == '' && !this.authService.isAdmin){
      window.location.href = 'login'
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
}
