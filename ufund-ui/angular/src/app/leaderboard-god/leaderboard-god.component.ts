import { Component, Input, SimpleChanges } from '@angular/core';
import { AuthService } from '../storage/auth.service';
import { FormBuilder } from '@angular/forms';
import { Account } from '../Account';
import { ProfileInfo } from '../profile-info';
import { Region } from '../region';

@Component({
  selector: 'app-leaderboard-god',
  templateUrl: './leaderboard-god.component.html',
  styleUrl: './leaderboard-god.component.css'
})
export class LeaderboardGodComponent {
  emptyAccount: Account = {
    name: '',
    passwordHash: '',
    imageLink: '',
    basket: {needs: []},
    moneyFunded: 0.0,
    needsFunded: 0,
    profileInfo: new ProfileInfo({alias: '', region: Region.FINGER_LAKES, pronouns: '', bio: '', email: '', phoneNumber: '', ssn: '', profilePic: ''})

  };

  style: String = ""
  rank: number = 0;

  @Input() god: Account | null;


  ngOnChanges(changes: SimpleChanges) {
    this.god = changes['god'].currentValue; // fetch the current value
  }

  constructor(
    public authService: AuthService,
    public formBuilder: FormBuilder,
  ) {
    this.god = null;
  }

  setProfile(){
    if(this.god){
      localStorage.setItem("profile-image", this.god.imageLink)
      this.authService.profileInfo = this.god.profileInfo
      this.authService.profileImage = this.god.imageLink
    }
  }

  getAlias(): string{
    if(this.god != null){
      if(this.god.profileInfo.alias == ''){
        return "Anonymous"
      }
      return this.god.profileInfo.alias;
    }
    return ''
  }

  
  

  ngOnInit(){
    
  }
}
