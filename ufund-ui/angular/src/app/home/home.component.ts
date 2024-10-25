import { Component } from '@angular/core';
import { AuthService } from '../storage/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

  constructor(public authService: AuthService) {}

  isAdmin: boolean = this.authService.isAdmin();

  ngOnInit(){
    let name = localStorage.getItem("name");
    if(name != null){
      this.authService.setName(name);
    }
    this.isAdmin = this.authService.isAdmin();

  }
}