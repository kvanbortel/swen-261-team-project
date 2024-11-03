import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {catchError, Observable, of, tap} from "rxjs";
import {ProfileInfo} from "./ProfileInfo";
import {Account} from "./Account";
import {AuthService} from "./storage/auth.service";

export interface UserProfile {
  alias?: string;
  region?: string;
  pronouns?: string;
  bio?: string;
  email?: string;
  phoneNumber?: string;
  ssn?: string;
}

@Injectable({
  providedIn: 'root'
})
export class ProfileSectionService {
  private usersUrl = 'http://localhost:8080/accounts';

  constructor(private http: HttpClient, public authService: AuthService) {
    let accountName = localStorage.getItem("name");
  }

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  getUserProfile(accountName: string): Observable<UserProfile> {
    return this.http.get<UserProfile>(`${this.usersUrl}/${accountName}/profileInfo`);
  }

  updateProfileInfo(profileInfo: ProfileInfo): Observable<any> {
      return this.http.put<ProfileInfo>(this.usersUrl, profileInfo, this.httpOptions).pipe(
        tap(_ => console.log(`updated profileInfo for account name=${this.authService.name}`)),
        catchError(this.handleError<ProfileInfo>('updateProfileInfo'))
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
      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      console.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

}
