import { Component, Input, SimpleChanges } from '@angular/core';
import { UserLevelService } from '../user-level.service';
import { Account } from '../Account';
import { UserLevel } from '../UserLevel';
import { AuthService } from '../storage/auth.service';

@Component({
  selector: 'app-badges',
  templateUrl: './badges.component.html',
  styleUrl: './badges.component.css'
})
export class BadgesComponent {
  level: UserLevel = UserLevel.NOOB;
  allAccounts: Account[] = [];
  currentUserAccount?: Account
  
  @Input() accountName: string | null = null;

  constructor(private userLevelService: UserLevelService, public authService: AuthService){}

  ngOnInit(){
    this.userLevelService.getAllAccounts().subscribe(accounts => {
      console.log("All Accounts:", accounts);
      this.allAccounts = accounts;
      this.currentUserAccount = accounts.find(account => account.name === this.accountName);
      if(this.currentUserAccount){
        this.level = this.userLevelService.getUserLevel(this.currentUserAccount, this.allAccounts)
      }
      console.log(this.level)
    });
    this.updateUserLevel()
    console.log(this.accountName)
    
  }

  showSuit(){
    if(this.level == UserLevel.NOOB || this.level == UserLevel.PRO || this.level == UserLevel.MASTER || this.level == UserLevel.CHAMPION){
      //console.log("NOOB")
      return true
    }
    return false
  }

  showHat(){
    
    if(this.level == UserLevel.PRO || this.level == UserLevel.MASTER || this.level == UserLevel.CHAMPION){
      //console.log("PRO")
      return true
    }
    return false
  }

  showPipe(){
    if(this.level == UserLevel.MASTER || this.level == UserLevel.CHAMPION){
      //console.log("MASTER")
      return true
    }
    return false
  }

  showCard(){
    if(this.level == UserLevel.CHAMPION){
      //console.log("CHAMPION")
      return true
    }
    return false
  }

  private updateUserLevel() {
    if (this.currentUserAccount && this.allAccounts.length > 0) {
      this.level = this.userLevelService.getUserLevel(this.currentUserAccount, this.allAccounts);
    }
  }

}
