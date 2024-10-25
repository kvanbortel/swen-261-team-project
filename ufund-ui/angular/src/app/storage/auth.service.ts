import { Injectable } from '@angular/core';
import { Account } from '../Account';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, Observable, of, tap } from 'rxjs';
import { Need } from '../Need';
import { BasketNeed } from '../BasketNeed';
import { Basket } from '../Basket';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthService implements CanActivate{
  admin: boolean = false;
  name: string = '';

  constructor(private http: HttpClient,  private router: Router) {}

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  };

  isAdmin(): boolean {
    if (this.name === 'admin') {
      this.admin = true;
    } else {
      this.admin = false;
    }
    return this.admin;
  }

  logout(){
    this.admin = false;
    this.name = '';
    localStorage.setItem("name", '');
  }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): boolean {
      console.log('CanActivate called');
    let isLoggedIn = this.isAuthenticated();
    console.log("logged in?" + isLoggedIn)
    if (isLoggedIn){
      return true;
    } else {
      this.router.navigate(['/login']);
      return false;
    }
    
  }

  isAuthenticated() {
    if(!(localStorage.getItem("isAdmin") === "true") && localStorage.getItem("name") === ''){
      return false;
    } 
    return true;
  }

  setName(name: string) {
    this.name = name;
  }

  getName() {
    return this.name;
  }

  addAccount(name: String): void {
    this.http
      .post('http://localhost:8080/accounts', name, this.httpOptions)
      .pipe(catchError(this.handleError<Account>('addAccount')))
      .subscribe({
        next: (response) => {
          console.log('Now logged in as ' + name + ':', response);
        },
        error: (err) => {
          console.error('Error logging in:', err);
        },
      });
  }

  getBasketNeeds(): Observable<BasketNeed[]> {
    return this.http
      .get<BasketNeed[]>(
        'http://localhost:8080/accounts/' + this.name + '/needs'
      )
      .pipe(
        tap((_) => console.log('get basket needs')),
        catchError(this.handleError<BasketNeed[]>('basketNeeds', []))
      );
  }

  checkoutBasket(): Observable<boolean> {
    return this.http
      .put<boolean>(
        'http://localhost:8080/accounts/' + this.name + '/checkout',
        null,
        this.httpOptions
      ).pipe(
        tap((_) => console.log('get basket needs')),
        catchError(this.handleError<boolean>('basketNeeds', false))
      );
  }

  addToBasket(amount: number, need?: Need): void {
    console.log('changing by', amount);
    console.log(
      'http://localhost:8080/accounts/' + this.name + '/needs/' + amount
    );
    this.http
      .put(
        'http://localhost:8080/accounts/' + this.name + '/needs/' + amount,
        need,
        this.httpOptions
      )
      .pipe(catchError(this.handleError<Account>('updateBasketNeed')))
      .subscribe({
        next: (response) => {
          console.log('Need added to basket successfully:', response);
        },
        error: (err) => {
          console.error('Error adding to basket:', err);
        },
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
      console.error(error); // log to console instead

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
