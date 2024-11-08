import { Component, EventEmitter, Output } from '@angular/core';
import { ProfileInfo } from '../profile-info';
import { ProfileSectionService } from '../profile-section.service';
import { AuthService } from '../storage/auth.service';
import { MessageService } from '../message.service';

@Component({
  selector: 'app-profile-other',
  templateUrl: './profile-other.component.html',
  styleUrl: './profile-other.component.css'
})
export class ProfileOtherComponent {
  accountName!: string | null;
  profileInfo!: ProfileInfo;
  selectedFile: File | null = null;
  errored = false;

  constructor(public authService: AuthService, private profileSectionService: ProfileSectionService, public messageService: MessageService) {
  }

  @Output() dataFromChild = new EventEmitter<null>();

  ngOnInit(): void {
    this.accountName = localStorage.getItem("profile");
    console.log(this.authService.profileInfo)
    this.profileInfo = this.authService.profileInfo;
    let image = localStorage.getItem("image")
    let profileImage = localStorage.getItem("profile-image")
    if(image != null){
      this.authService.setImage(image);
      this.authService.profileImage = profileImage;
    }
  }

  
}
