import { Component } from '@angular/core';
import { debounceTime, distinctUntilChanged, Observable, startWith, Subject, switchMap } from 'rxjs';
import { CupboardService } from '../cupboard.service';
import { Need } from '../Need';
import { LoginComponent } from '../login/login.component';
import { StorageComponent } from '../storage/storage.component';

@Component({
  selector: 'app-cupboard-search',
  templateUrl: './cupboard-search.component.html',
  styleUrl: './cupboard-search.component.css'
})
export class CupboardSearchComponent {
  needs$!: Observable<Need[]>;
  private searchTerms = new Subject<string>();

  constructor(private cupboardService: CupboardService, public storageComponent: StorageComponent) {}

  // Push a search term into the observable stream.
  search(term: string): void {
    this.searchTerms.next(term);
  }

  ngOnInit(): void {
    
    this.needs$ = this.searchTerms.pipe(

      // first search is an empty string, should return all needs
      startWith(""),

      // wait 300ms after each keystroke before considering the term
      debounceTime(300),

      // ignore new term if same as previous term
      distinctUntilChanged(),

      // switch to new search observable each time the term changes
      switchMap((term: string) => this.cupboardService.searchNeeds(term)),


    );
  }
}
