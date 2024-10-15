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
   this.addAccount(
    {
      name: account,
      basket: {
        needs: []
      }
    }
  );
   this.authService.setName(account);
  }

  addAccount(account: Account): void {
    console.log(account);
    this.http.post("http://localhost:8080/accounts", account, this.httpOptions).pipe(
      catchError(this.handleError<Account>('addAccount'))
    );
  }

  ngOnInit(): void{
    this.authService.setisAdmin(0);
    this.authService.setName('');
  }

   /**
   * Handle Http operation that failed.
   * Let the app continue.
   *
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
   private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

}
