import { Component, EventEmitter, Output } from '@angular/core';
import { ProfileInfo } from '../profile-info';
import { ProfileSectionService } from '../profile-section.service';
import { AuthService } from '../storage/auth.service';
import { MessageService } from '../message.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile-other',
  templateUrl: './profile-other.component.html',
  styleUrl: './profile-other.component.css'
})
export class ProfileOtherComponent {
  accountName!: string | null;
  profileInfo!: ProfileInfo;
  imageLink!: string;
  selectedFile: File | null = null;
  errored = false;

  constructor(public authService: AuthService, private profileSectionService: ProfileSectionService, public messageService: MessageService, public router: Router) {
      const navigation = this.router.getCurrentNavigation();
      console.log(navigation)
      if (navigation?.extras.state) {
        this.profileInfo = navigation.extras.state['profileInfo'];
        this.imageLink = navigation.extras.state['profileImg'];
        console.log(this.imageLink)
      } 
      else {
        this.router.navigate(['/home'])
        console.log('No data received');
      }
  }

  @Output() dataFromChild = new EventEmitter<null>();

  ngOnInit(){
    let name = localStorage.getItem("name");
    if(name != null){
      this.authService.setName(name);
    }
    let image = localStorage.getItem("image")
    if(image != null){
      this.authService.setImage(image);
    }
  }
  
}
