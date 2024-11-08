import { Component } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Account } from '../Account';
import { AuthService } from '../storage/auth.service';

@Component({
  selector: 'app-leaderboard-page',
  templateUrl: './leaderboard-page.component.html',
  styleUrl: './leaderboard-page.component.css'
})
export class LeaderboardPageComponent {


  god: Account | null = null;
  accounts$ = new BehaviorSubject<Account[]>([]);
  constructor(public authService: AuthService){};

  ngOnInit(){

    this.authService.getAccountsSorted().subscribe({
      next: (response) => {
        this.accounts$.next(response);
        console.log(response);
      },
    });

    this.authService.getGod().subscribe({
      next: (response) => {
        this.god = (response);
      },
    });

    let image = localStorage.getItem("image")

    if(image != null){
      this.authService.setImage(image);

    }
  }
}
