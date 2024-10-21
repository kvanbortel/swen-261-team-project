import { Component, Injectable } from '@angular/core';
import { AuthService } from '../storage/auth.service';
import { catchError, Observable, of, tap } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

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
    private router: Router
  ) {}

  login(name: string) {
    this.authService.addAccount(name);
    this.authService.setName(name);

    this.router.navigate(['/home'], {});
  }

  submitEnter(event: KeyboardEvent, value: string) {
    if (event.key === 'Enter') {
      this.login(value);
    }
  }

  ngOnInit(): void {
    this.authService.setName('');
  }
}
