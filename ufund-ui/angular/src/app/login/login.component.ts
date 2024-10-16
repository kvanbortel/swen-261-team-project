import { Component, Injectable } from '@angular/core';
import { AuthService } from '../storage/auth.service';
import { catchError, Observable, of, tap } from 'rxjs';
import { Account } from '../Account';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})

export class LoginComponent {

  account: string = "";
  admin: number = 0;

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(public authService: AuthService,  private http: HttpClient) {
    
  }

  login(account: string){
    if(account == "admin"){
      this.authService.setisAdmin(1);
      return;
    }
   this.authService.addAccount(
    {
      name: account,
      basket: {
        needs: []
      }
    }
  );
   this.authService.setName(account);
  }

  

  ngOnInit(): void{
    this.authService.setisAdmin(0);
    this.authService.setName('');
  }

}
