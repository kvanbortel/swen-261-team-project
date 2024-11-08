import { Component } from '@angular/core';
import { AuthService } from '../storage/auth.service';
import { BehaviorSubject } from 'rxjs';
import { Account } from '../Account';
import { ProfileInfo } from '../profile-info';
import { Region } from '../region';

@Component({
  selector: 'app-leaderboard',
  templateUrl: './leaderboard.component.html',
  styleUrl: './leaderboard.component.css'
})
export class LeaderboardComponent {

  account: Account = {
    name: '',
    passwordHash: '',
    imageLink: '',
    basket: {needs: []},
    moneyFunded: 0.0,
    needsFunded: 0,
    profileInfo: new ProfileInfo({alias: '', region: Region.FINGER_LAKES, pronouns: '', bio: '', email: '', phoneNumber: '', ssn: '', profilePic: ''})

  };

  god: Account | null = null;
  accounts$ = new BehaviorSubject<Account[]>([]);
  constructor(public authService: AuthService){};

  ngOnInit(){

    this.authService.getAccount(localStorage.getItem("name")).subscribe({
      next: (response) => {
        this.account = (response);
        console.log(response);
      },
    });

    this.authService.getGod().subscribe({
      next: (response) => {
        this.god = (response);
      },
    });

    this.authService.getAccountsSortedTop3().subscribe({
      next: (response) => {
        this.accounts$.next(response);
      },
    });
    
  }

}
