import { Component, ElementRef, HostListener } from '@angular/core';
import { AuthService } from '../storage/auth.service';

@Component({
  selector: 'app-pic-button',
  templateUrl: './pic-button.component.html',
  styleUrl: './pic-button.component.css'
})
export class PicButtonComponent {
  constructor(public authService: AuthService, private elementRef: ElementRef){}

  dropdownOpen = false;

  toggleDropdown() {
    this.dropdownOpen = !this.dropdownOpen;
  }

  @HostListener('document:click', ['$event'])
  clickOutside(event: Event) {
    if (!this.elementRef.nativeElement.contains(event.target)) {
      this.dropdownOpen = false;
    }
  }

  profile() {
    // go to the profile page
  }

  logout() {
    this.authService.logout();
  }
}
