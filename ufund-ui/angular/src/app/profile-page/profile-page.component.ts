import { Component } from '@angular/core';
import {AuthService} from "../storage/auth.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile-page.component.html',
  styleUrl: './profile-page.component.css'
})
export class ProfilePageComponent {
  constructor(public authService: AuthService) {
  }

  ngOnInit(): void {
    let name = localStorage.getItem("name");
    if(name != null)
      this.authService.setName(name);
  }
}
