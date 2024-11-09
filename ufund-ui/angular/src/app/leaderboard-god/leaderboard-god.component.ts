import { Component, Input, SimpleChanges } from '@angular/core';
import { AuthService } from '../storage/auth.service';
import { FormBuilder } from '@angular/forms';
import { Account } from '../Account';
import { ProfileInfo } from '../profile-info';
import { Region } from '../region';
import { Router } from '@angular/router';

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
    profileInfo: new ProfileInfo({alias: '', region: Region.NONE, pronouns: '', bio: '', email: '', phoneNumber: '', ssn: '', profilePic: '', privacy: "Private"}),
  };

  style: String = ""
  rank: number = 0;

  @Input() god: Account | null;
  @Input() isGod: boolean;

  ngOnChanges(changes: SimpleChanges) {
    this.god = changes['god'].currentValue; // fetch the current value
    this.isGod = changes['isGod'].currentValue;
  }

  constructor(
    public authService: AuthService,
    public formBuilder: FormBuilder,
    public router: Router
  ) {
    this.god = null;
    this.isGod = false;
  }

  setProfile(){
    if(this.god){
      localStorage.setItem("profile-image", this.god.imageLink)
      this.authService.profileInfo = this.god.profileInfo
      this.authService.profileImage = this.god.imageLink
    }
  }

  getDynamicStyles() {
    if(this.isGod){
      return {
        border: "4px solid rgb(241, 176, 97)"
      };
    }
    return
  }

  getAlias(): string{
    if(this.isGod){
      return "You"
    }
    if(this.god != null){
      if(this.god.profileInfo.alias == ''){
        return "Anonymous"
      }
      return this.god.profileInfo.alias;
    }
    return ''
  }

  routeProfile(){
    if(this.god!.name == this.authService.getName()){
      this.router.navigate(['/profile']);
      return;
    }
    this.router.navigate(['/user'], {
      state: {
        profileInfo: this.god!.profileInfo,
        profileImg: this.god!.imageLink
      },
    });
  }

  ngOnInit(){
    
  }
}
