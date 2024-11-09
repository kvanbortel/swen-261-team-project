import { ChangeDetectionStrategy, ChangeDetectorRef, Component, Input, SimpleChanges } from '@angular/core';
import { Account } from '../Account';
import { ProfileInfo } from '../profile-info';
import { Region } from '../region';
import { AuthService } from '../storage/auth.service';
import { FormBuilder } from '@angular/forms';
import numeral from 'numeral';

@Component({
  selector: 'app-leaderboard-account-profile',
  templateUrl: './leaderboard-account-profile.component.html',
  styleUrl: './leaderboard-account-profile.component.css', 
})
export class LeaderboardAccountProfileComponent {
  emptyAccount: Account = {
    name: '',
    passwordHash: '',
    imageLink: '',
    basket: {needs: []},
    moneyFunded: 0.0,
    needsFunded: 0,
    profileInfo: new ProfileInfo({alias: '', region: Region.NONE, pronouns: '', bio: '', email: '', phoneNumber: '', ssn: '', profilePic: '', privacy: "Private"}),
  };

  style: String = ""
  rank: number = 0;

  @Input() god: Account | null;
  @Input() account: Account;
  @Input() index: number;

  ngOnChanges(changes: SimpleChanges) {
    this.account = changes['account'].currentValue; // fetch the current value
  }

  constructor(
    public authService: AuthService,
    public formBuilder: FormBuilder,
  ) {
    this.account = this.emptyAccount;
    this.index = 1;
    this.god = null;
  }

  formatMoney(): string{
    
    return numeral(this.account.moneyFunded).format("($ 0.00 a)")
    
  }

  getAlias(): string{
    if(this.account.profileInfo.alias == ''){
      return "anon"
    }
    return this.account.profileInfo.alias;
    
  }

  setProfile(){
    console.log(this.account.profileInfo)
    localStorage.setItem("profile-image", this.account.imageLink)
    this.authService.profileInfo = this.account.profileInfo
    this.authService.profileImage = this.account.imageLink
  }

  getRank(): string{
    this.authService.getRank(localStorage.getItem("name")).subscribe( (result) =>{ //get account from backend and subscribe to the result
      if(result){ 
        return result.toString();
      }
      else{
        console.log(this.account.name + "could not find account")
        return '';
      }});
      return '';
  }
  

  ngOnInit(){
    this.authService.getRank(localStorage.getItem("name")).subscribe( (result) =>{ //get account from backend and subscribe to the result
      if(result){ 
        this.rank = result;
        return;
      }
      else{
        console.log(this.account.name + "could not find account")
        return;
      }});
      return;
  
  }
}
