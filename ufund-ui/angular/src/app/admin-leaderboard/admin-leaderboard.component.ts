import { Component } from '@angular/core';
import { AuthService } from '../storage/auth.service';
import { BehaviorSubject, map, Observable } from 'rxjs';
import { Account } from '../Account';
import { ProfileInfo } from '../profile-info';
import { Region } from '../region';

@Component({
  selector: 'app-admin-leaderboard',
  templateUrl: './admin-leaderboard.component.html',
  styleUrl: './admin-leaderboard.component.css'
})
export class AdminLeaderboardComponent {

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
  isGod: boolean = false;
  accounts$ = new BehaviorSubject<Account[]>([]);
  constructor(public authService: AuthService){};
  isAdmin: boolean = this.authService.isAdmin();
  onLeaderboardBool: boolean = false;

  ngOnInit(){
    if(!this.authService.isAdmin()){
      this.authService.getAccount(localStorage.getItem("name")).subscribe({
        next: (response) => {
          this.account = (response);
          console.log(response);
        },
      });
    }

    this.authService.getGod().subscribe({
      next: (response) => {
        this.god = (response);
        if(this.account.name == this.god.name){
          this.isGod = true;
          console.log("I AM GOD")
        }
      },
    });

    this.authService.getAccountsSorted().subscribe({
      next: (response) => {
        this.accounts$.next(response);
        console.log(response)
      },
    });
    
  }

  onLeaderboard(k: number): Observable<boolean> {
    return this.accounts$.pipe(
      map(response => {
        if (response[k] && response[k].name && response[k].name === this.account.name) {
          this.onLeaderboardBool = true
          return true;
        } else {
          return false;
        }
      })
    );
  }

}
