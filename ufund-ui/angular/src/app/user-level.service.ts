import {Injectable} from '@angular/core';
import {UserLevel} from "./UserLevel";
import {Account} from "./Account";
import {catchError, Observable, of, tap} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {AuthService} from "./storage/auth.service";

@Injectable({
  providedIn: 'root'
})
export class UserLevelService {
  private PERCENT_INDEX = 0.05;
  private PRO_MIN_FUNDED = 1000;
  private usersUrl = 'http://localhost:8080/accounts';

  constructor(private http: HttpClient, public authService: AuthService) {
  }

  getAllAccounts(): Observable<Account[]> {
    return this.http.get<Account[]>(this.usersUrl).pipe(
      tap((response) => {
        console.log("Fetched Accounts:", response);  // Debugging the fetched accounts
      }),
      catchError(this.handleError<Account[]>('getAllAccounts', []))
    );
  }

  getUserLevel(account: Account, allAccounts: Account[]) {
    let userLevel: UserLevel = UserLevel.NOOB;

    const moneyFunded = account.moneyFunded;
    const maxDonation = Math.max(...allAccounts.map(acc => acc.moneyFunded));

    // Sort accounts based on moneyFunded in descending order
    const sortedAccounts = allAccounts.sort((a, b) => b.moneyFunded - a.moneyFunded);

    const topPercentIndex = Math.ceil(sortedAccounts.length * -this.PERCENT_INDEX) - 1;

    if (moneyFunded >= this.PRO_MIN_FUNDED) {
      userLevel = UserLevel.PRO;
      if (sortedAccounts.indexOf(account) <= topPercentIndex) {
        userLevel = UserLevel.MASTER;
        if (moneyFunded === maxDonation) {
          userLevel = UserLevel.CHAMPION;
        }
      }
    }

    return userLevel;
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
