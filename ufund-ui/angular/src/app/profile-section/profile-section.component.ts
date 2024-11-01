import {Component, Input} from '@angular/core';
import {UserProfile, ProfileSectionService} from "../profile-section.service";

@Component({
  selector: 'app-profile-section',
  templateUrl: './profile-section.component.html',
  styleUrl: './profile-section.component.css'
})
export class ProfileSectionComponent {
  @Input() accountName!: string;
  userProfile!: UserProfile;

  constructor(private profileSectionService: ProfileSectionService) {}

  ngOnInit(): void {
    this.profileSectionService.getUserProfile(this.accountName).subscribe(profile => {
      this.userProfile = profile;
    });
  }
}
