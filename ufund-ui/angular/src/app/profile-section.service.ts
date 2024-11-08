import {Injectable, Pipe, PipeTransform} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {catchError, map, Observable, of, tap} from "rxjs";
import {ProfileInfoJSON} from "./ProfileInfoJSON";
import {Account} from "./Account";
import {AuthService} from "./storage/auth.service";
import {Region} from "./region";
import {ProfileInfo} from "./profile-info";

@Injectable({
  providedIn: 'root'
})
export class ProfileSectionService {
  private usersUrl = 'http://localhost:8080/accounts';

  constructor(private http: HttpClient, public authService: AuthService) {
  }

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  getProfileInfo(accountName: string): Observable<ProfileInfo> {
    return this.http.get<ProfileInfoJSON>(`${this.usersUrl}/${accountName}/profileInfo`).pipe(
        map(data => new ProfileInfo(data))
    );
  }

  updateProfileInfo(profileInfo: ProfileInfo): Observable<ProfileInfo> {
      let accountName = this.authService.getName();
      return this.http.put<ProfileInfoJSON>(`${this.usersUrl}/${accountName}/profileInfo`, profileInfo.json, this.httpOptions).pipe(
          map(data => new ProfileInfo(data))
      ).pipe(
        tap(_ => console.log(`updated profileInfo for account name=${this.authService.name}`)),
        catchError(this.handleError<ProfileInfo>('updateProfileInfo'))
      );
  }

  getAllAccounts(): Observable<Account[]> {
    return this.http.get<Account[]>(this.usersUrl).pipe(
      tap((response) => {
        console.log("Fetched Accounts:", response);  // Debugging the fetched accounts
      }),
      catchError(this.handleError<Account[]>('getAllAccounts', []))
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
