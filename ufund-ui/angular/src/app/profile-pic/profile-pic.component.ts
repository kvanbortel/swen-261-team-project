import { Component } from '@angular/core';
import { AuthService } from '../storage/auth.service';

@Component({
  selector: 'app-profile-pic',
  templateUrl: './profile-pic.component.html',
  styleUrl: './profile-pic.component.css'
})
export class ProfilePicComponent {

  selectedFile: File | null = null;
  errored = false;

  constructor(public authService: AuthService){}

  // whenever a file is uploaded:
  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
    }

    if(this.selectedFile)
    if (this.selectedFile.type === 'image/jpeg' || 
        this.selectedFile.type === 'image/png' || 
        this.selectedFile.type ==='image/jpg') {
      if (this.selectedFile.size > 1000000) {
        // file is too large
        // TODO: Handle error
        this.errored = true;
        console.log("file too large")
        return
      }
    }
    this.errored = false;

    event.preventDefault();
    if (!this.selectedFile) {
      console.error('No file selected!');
      return;
    }

    this.authService.postImage(this.selectedFile)
  }
}
