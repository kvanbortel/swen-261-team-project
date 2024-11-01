import { Component } from '@angular/core';
import {AuthService} from "../storage/auth.service";
import {ProfileSectionService} from "../profile-section.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile-page.component.html',
  styleUrl: './profile-page.component.css'
})
export class ProfilePageComponent {
  userId!: string;

  constructor(public authService: AuthService, private profileSectionService: ProfileSectionService) {
  }

  ngOnInit(): void {
    let currentUserId = localStorage.getItem("id");
      if (currentUserId != null)
        this.profileSectionService.setUserId("userId");

    let name = localStorage.getItem("name");
    if(name != null)
      this.authService.setName(name);
  }
}
