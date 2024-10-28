import { Component, Injectable } from '@angular/core';
import { AuthService } from '../storage/auth.service';
import { catchError, Observable, of, tap } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { MessageService } from '../message.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  account: string = '';
  admin: number = 0;

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  };

  constructor(
    public authService: AuthService,
    private http: HttpClient,
    private router: Router,
    public messageLoginService: MessageService
  ) {}

  login(name: string) {
    this.messageLoginService.clear()
    if(name == '' || name == null){
      return
    }
    if(!this.onlyLettersAndNumbers(name)){
      this.messageLoginService.add("Only usernames containing alphanumeric characters allowed.", false)
      return
    }
    if(name.length > 13){
      this.messageLoginService.add("Only usernames of length 13 or less allowed.", false)
      return
    }
    if(!this.isAlpha(name.substring(0, 1))){
      this.messageLoginService.add("Only usernames starting with a letter allowed.", false)
      return
    }
    this.authService.setName(name);
    localStorage.setItem("isAdmin", this.authService.isAdmin().toString() );
    localStorage.setItem("name", name);
    this.authService.addAccount(name);  


    this.router.navigate(['/home'], {});
  }

  submitEnter(event: KeyboardEvent, value: string) {
    if (event.key === 'Enter') {
      this.login(value);
    }
  }

  ngOnInit(): void {
    this.messageLoginService.add('', false);
    this.authService.setName('');
    localStorage.setItem("name", '');
  }

  onlyLettersAndNumbers(str : string) {
    return Boolean(str.match(/^[A-Za-z0-9]*$/));
  }

  isAlpha(str: string) {
    return Boolean(str.match("[a-zA-Z]+"));
}
}
