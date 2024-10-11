import { Component } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  account:string = "";
  admin: number = 0;
  
  constructor() {}

  login(account: string){
    this.account = account;
    console.log(account);
    if(this.account == "admin"){
      this.admin = 1;
    }
  }

  isAdmin(){
    return this.admin;
  }
}
