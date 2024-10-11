import { Component, Injectable } from '@angular/core';
import { AuthService } from '../storage/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})

export class LoginComponent {

  account: string = "";
  admin: number = 0;

  constructor(public authService: AuthService) {}


  login(account: string){
    if(account == "admin"){
      this.authService.setisAdmin(1);
      return;
    }
   this.authService.setName(account);
   console.log(account);
    
  }

  ngOnInit(): void{
    this.authService.setisAdmin(0);
    this.authService.setName('');
  }

}
