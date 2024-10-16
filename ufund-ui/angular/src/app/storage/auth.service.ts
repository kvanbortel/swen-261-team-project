import { Injectable } from '@angular/core';
import { Account } from '../Account';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, Observable, of, tap } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  isAdmin: number = 0;
  name: string = '';

  constructor(private http: HttpClient){};

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  setisAdmin(isAdmin:number){
    this.isAdmin = isAdmin;
  }
  
  setName(name:string){
    this.name = name;
  }

  getName(){
    return this.name;
  }

  addAccount(account: Account): void {
    this.http.post("http://localhost:8080/accounts", account, this.httpOptions).pipe(
      catchError(this.handleError<Account>('addAccount'))
    ).subscribe({
      next: (response) => {
        console.log('Account created successfully:', response);
      },
      error: (err) => {
        console.error('Error creating account:', err);
      }
    });
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
