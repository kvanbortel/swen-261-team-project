import { Component } from '@angular/core';
import {AuthService} from "../storage/auth.service";
import {ProfileSectionService} from "../profile-section.service";
import {ProfileInfo} from "../profile-info";

@Component({
  selector: 'app-profile',
  templateUrl: './profile-page.component.html',
  styleUrl: './profile-page.component.css'
})
export class ProfilePageComponent {
  accountName!: string | null;
  profileInfo!: ProfileInfo;

  constructor(public authService: AuthService, private profileSectionService: ProfileSectionService) {
  }

  ngOnInit(): void {
    this.accountName = localStorage.getItem("name");
    if(this.accountName != null){
      this.authService.setName(this.accountName);
      this.profileSectionService.getProfileInfo(this.accountName).subscribe(profile => {
        this.profileInfo = profile
      });
    }
  }

  setProfileInfo(profileInfo: ProfileInfo) {
      this.profileInfo = profileInfo;
  }
}
