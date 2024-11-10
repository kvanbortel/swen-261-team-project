import { Component } from '@angular/core';
import { BehaviorSubject, map, Observable } from 'rxjs';
import { Account } from '../Account';
import { AuthService } from '../storage/auth.service';
import { ProfileInfo } from '../profile-info';
import { Region } from '../region';

@Component({
  selector: 'app-leaderboard-page',
  templateUrl: './leaderboard-page.component.html',
  styleUrl: './leaderboard-page.component.css'
})
export class LeaderboardPageComponent {

  account: Account = {
    name: '',
    passwordHash: '',
    imageLink: '',
    basket: {needs: []},
    moneyFunded: 0.0,
    needsFunded: 0,
    profileInfo: new ProfileInfo({alias: '', region: Region.NONE, pronouns: '', bio: '', email: '', phoneNumber: '', ssn: '', profilePic: '', privacy: 'Private'})
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
        },
      });
    }

    this.authService.getAccountsSorted().subscribe({
      next: (response) => {
        this.accounts$.next(response);

        this.authService.getGod().subscribe({
          next: (response) => {
            this.god = (response);
            if(this.account.name == this.god.name){
              this.isGod = true;
              console.log("I AM GOD")
            }
          },
        });
      },
    });

    let image = localStorage.getItem("image")

    if(image != null){
      this.authService.setImage(image);

    }
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
