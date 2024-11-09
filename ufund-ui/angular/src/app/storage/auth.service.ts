import { Injectable } from '@angular/core';
import { Account } from '../Account';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { catchError, Observable, of, tap, throwError } from 'rxjs';
import { Need } from '../Need';
import { BasketNeed } from '../BasketNeed';
import { Basket } from '../Basket';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { AdminInfo } from '../AdminInfo';
import { ProfileInfo } from '../profile-info';
import { Region } from '../region';

@Injectable({
  providedIn: 'root',
})
export class AuthService implements CanActivate{
  admin: boolean = false;
  name: string = '';
  image: string | null= '';
  profileInfo: ProfileInfo =  new ProfileInfo({alias: '', region: Region.NONE, pronouns: '', bio: '', email: '', phoneNumber: '', ssn: '', profilePic: '', privacy: "Private"})
  loadingImg: string = "https://media1.tenor.com/m/On7kvXhzml4AAAAC/loading-gif.gif";
  profileImage: string | null= '';

  private AccountURL = "http://localhost:8080/accounts";

  constructor(private http: HttpClient, private router: Router) {}

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


  getRank(name: string | null): Observable<number>{
    console.log(this.AccountURL + '/' + name + "/rank")
    return this.http
      .get<number>(
        this.AccountURL + '/' + name + "/rank"
      ).pipe(
        tap((_) => console.log('get account rank ' + name)),
        catchError(this.handleError<number>('account'))
      );
  }

  getGod(): Observable<Account>{
    console.log(this.AccountURL + '/god')
    return this.http
      .get<Account>(
        this.AccountURL + '/god'
      ).pipe(
        tap((_) => console.log('get god')),
        catchError(this.handleError<Account>('account'))
      );

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

  setImage(img: string){
    this.image = img; 
  }

  getImage() {
    return this.image;
  }


  addAccount(name: string, passwordHash: string): Observable<any> {
    const body = {name, passwordHash };
    return this.http
      .post(this.AccountURL, body, this.httpOptions)
      .pipe(catchError((error) => {
        console.error('Error occurred:', error);
        return throwError(error);
      }))
  }

  getBasketNeeds(): Observable<BasketNeed[]> {
    return this.http
      .get<BasketNeed[]>(
        this.AccountURL + '/' + this.name + '/needs'
      )
      .pipe(
        tap((_) => console.log('get basket needs')),
        catchError(this.handleError<BasketNeed[]>('basketNeeds', []))
      );
  }

  getAccount(name: string | null): Observable<Account>{
    return this.http
      .get<Account>(
        this.AccountURL + '/' + name
      ).pipe(
        tap((_) => console.log('get account' + name)),
        catchError(this.handleError<Account>('account'))
      );
  }

  getAccountsSorted(): Observable<Account[]>{
    return this.http
      .get<Account[]>(
        this.AccountURL
      ).pipe(
        tap((_) => console.log('get account' + name)),
        catchError(this.handleError<Account[]>('account'))
      );
  }

  getAccountsSortedTop3(): Observable<Account[]>{
    return this.http
      .get<Account[]>(
        this.AccountURL + "/top3"
      ).pipe(
        tap((_) => console.log('get account' + name)),
        catchError(this.handleError<Account[]>('account'))
      );
  }

  checkoutBasket(): Observable<boolean> {
    return this.http
      .put<boolean>(
        this.AccountURL + '/' + this.name + '/checkout',
        null,
        this.httpOptions
      ).pipe(
        tap((_) => console.log('get basket needs')),
        catchError(this.handleError<boolean>('basketNeeds', false))
      );
  }

  addToBasket(amount: number, need?: Need): Observable<BasketNeed> {
    return this.http
      .put<BasketNeed>(
        this.AccountURL + '/' + this.name + '/needs/' + amount,
        need,
        this.httpOptions
      )
      .pipe(
          tap((response) => console.log('need added to basket', response)),
          catchError(this.handleError<BasketNeed>('basketNeeds'))
        );
  }

  postImage(image: File): void{
    const formData = new FormData();
    formData.append('image', image);


    console.log("posting image")
    localStorage.setItem("image", this.loadingImg)
    this.image = this.loadingImg
    console.log(image)
    this.http
      .post(this.AccountURL + '/' + this.name + "/image", formData, { responseType: 'text' })
      .pipe(catchError(this.handleError<String>('addAccount')))
      .subscribe({
        next: (response) => {
          console.log('Image posted successfully:', response);
          localStorage.setItem("image", response as string)
          this.image = response as string
        },
        error: (err) => {
          console.error('Error logging in:', err);
        },
      });
  }

  getAdminInfo(): Observable<AdminInfo> {
    return this.http
      .get<AdminInfo>(
        this.AccountURL + '/adminInfo'
      )
      .pipe(
        tap((_) => console.log('getting admin info')),
        catchError(this.handleError<AdminInfo>("error"))
      );
  }

  togglePrivacy(accountName: string){
    console.log(this.AccountURL + '/' + accountName + "/privacy")
    return this.http
      .put(
        this.AccountURL + '/' + accountName + "/rank", null
      ).pipe(
        tap((_) => console.log('toggle account privacy ' + accountName)),
        catchError(this.handleError('account'))
      );
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
