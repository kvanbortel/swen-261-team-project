import { Component } from '@angular/core';
import { AuthService } from '../storage/auth.service';

@Component({
  selector: 'app-profile-pic',
  templateUrl: './profile-pic.component.html',
  styleUrl: './profile-pic.component.css'
})
export class ProfilePicComponent {

  selectedFile: File | null = null;

  constructor(public authService: AuthService){}

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
    }

    event.preventDefault();
    if (!this.selectedFile) {
      console.error('No file selected!');
      return;
    }
    
    this.authService.postImage(this.selectedFile)
  }
}
