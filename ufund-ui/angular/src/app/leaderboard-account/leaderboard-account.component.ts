import {
  Component,
  EventEmitter,
  HostBinding,
  Input,
  OnChanges,
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
import { map, Observable } from 'rxjs';

@Component({
  selector: 'app-leaderboard-account',
  templateUrl: './leaderboard-account.component.html',
  styleUrl: './leaderboard-account.component.css',
})
export class LeaderboardAccountComponent{

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

  @Input() account: Account = this.emptyAccount;
  @Input() index: number = -1;
  @Input() you: boolean | null = false;
  
  rank = 0;

  ngOnChanges(changes: SimpleChanges) {
    // fetch updated values
    if (changes['account']) {
      this.account = changes['account'].currentValue;
    }
    if (changes['index']) {
      this.index = changes['index'].currentValue;
      this.rank = this.index + 1
      if(this.index == -1){
        this.getRank().subscribe((result) => {
          this.rank = result
        })
      }
    }
    if (changes['you']) {
      this.you = changes['you'].currentValue;
    }
  }

  constructor(
    public authService: AuthService,
    public formBuilder: FormBuilder,
    public router: Router
  ) {}

  getDynamicStyles() {
    if(this.authService.isAdmin()){
      return {
        padding: "5px",
        "padding-right" : "2vw"
      }
    }
    if(this.you){
      return {
        border: "4px solid rgb(241, 176, 97)"
      };
    }
    return
  }

  formatMoney(): string{
    return numeral(this.account.moneyFunded).format("($ 0.00 a)")
  }

  getAlias(): string{
    if(this.you){
      return "You"
    }
    if(this.account.profileInfo.alias == ''){
      return "Anonymous"
    }
    return this.account.profileInfo.alias;
  }

  setProfile(){
    localStorage.setItem("profile-image", this.account.imageLink)
    this.authService.profileInfo = this.account.profileInfo
    this.authService.profileImage = this.account.imageLink
  }

  routeProfile(){
    if(this.account.name == this.authService.getName()){
      this.router.navigate(['/profile']);
      return;
    }
    this.router.navigate(['/user'], {
      state: {
        accountName: this.account.name,
        profileImg: this.account.imageLink
      },
    });
  }

  getRank(): Observable<number> {
    return this.authService.getRank(localStorage.getItem("name")).pipe(
      map(response => {
        if(response){ 
          return response;
        }
        else{
          console.log(this.account.name + "could not find account")
          return -1;
        }})
      );
  }
  

  ngOnInit(){
  
  }
  
}
