import { Injectable } from '@angular/core';
import { Account } from '../Account';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, Observable, of, tap } from 'rxjs';
import { Need } from '../Need';
import { BasketNeed } from '../BasketNeed';
import { Basket } from '../Basket';


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  admin: boolean = false;
  name: string = '';

  constructor(private http: HttpClient){};

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  isAdmin(): boolean {
    if(this.name === "admin"){
      this.admin = true;
    }
    else{
      this.admin = false;
    }
    return this.admin
  }
  
  setName(name:string){
    this.name = name;
  }

  getName(){
    return this.name;
  }

  addAccount(name: String): void {
    this.http.post("http://localhost:8080/accounts", name, this.httpOptions).pipe(
      catchError(this.handleError<Account>('addAccount'))
    ).subscribe({
      next: (response) => {
        console.log('Now logged in as ' + name + ':', response);
      },
      error: (err) => {
        console.error('Error logging in:', err);
      }
    });
  }

  getBasketNeeds(): Observable<BasketNeed[]> {
    return this.http.get<BasketNeed[]>('http://localhost:8080/accounts/' + this.name + '/needs').pipe(
      tap((_) => console.log('get basket needs')),
      catchError(this.handleError<BasketNeed[]>('basketNeeds', []))
    );
  }

  checkoutBasket(): Observable<BasketNeed> {
    return this.http.put<BasketNeed>('http://localhost:8080/accounts/' + this.name + '/checkout', null).pipe(
      tap((_) => console.log('checkout basket needs')),
      catchError(this.handleError<BasketNeed>('basketNeeds'))
    );
  }

  addToBasket(amount: number, need?: Need): void {
    console.log("changing by", amount)
    console.log('http://localhost:8080/accounts/' + this.name + '/needs/' + amount)
    this.http.put('http://localhost:8080/accounts/' + this.name + '/needs/' + amount, need, this.httpOptions).pipe(
      catchError(this.handleError<Account>('updateBasketNeed'))
    ).subscribe({
      next: (response) => {
        console.log('Need added to basket successfully:', response);
      },
      error: (err) => {
        console.error('Error adding to basket:', err)
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
