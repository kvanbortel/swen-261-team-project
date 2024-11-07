import { Component } from '@angular/core';
import { AuthService } from '../storage/auth.service';
import { BehaviorSubject } from 'rxjs';
import { Account } from '../Account';

@Component({
  selector: 'app-leaderboard',
  templateUrl: './leaderboard.component.html',
  styleUrl: './leaderboard.component.css'
})
export class LeaderboardComponent {

  accounts$ = new BehaviorSubject<Account[]>([]);
  constructor(public authService: AuthService){};

  ngOnInit(){

    this.authService.getAccountsSorted().subscribe({
      next: (response) => {
        this.accounts$.next(response);
        console.log(response);
      },
    });
  }

}
