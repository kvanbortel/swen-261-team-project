import {
  Component,
  EventEmitter,
  HostBinding,
  Input,
  Output,
  SimpleChanges,
} from '@angular/core';
import { Account } from '../Account';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { AuthService } from '../storage/auth.service';
import numeral, { Numeral } from 'numeral'
import { ProfileInfo } from '../profile-info';
import { ProfileInfoJSON } from '../ProfileInfoJSON';
import { Region } from '../region';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-leaderboard-account',
  templateUrl: './leaderboard-account.component.html',
  styleUrl: './leaderboard-account.component.css',
})
export class LeaderboardAccountComponent {
  

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

  @Input() account: Account;
  @Input() index: number;

  ngOnChanges(changes: SimpleChanges) {
    this.account = changes['account'].currentValue; // fetch the current value
  }

  constructor(
    public authService: AuthService,
    public formBuilder: FormBuilder,
    public router: Router
  ) {
    this.account = this.emptyAccount;
    this.index = 1;
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

  routeProfile(){
    this.router.navigate(['/profileother'], {
      state: {
        profileInfo: this.account.profileInfo,
        profileImg: this.account.imageLink
      },
    });
  }

  ngOnInit(){

  }
  
}
