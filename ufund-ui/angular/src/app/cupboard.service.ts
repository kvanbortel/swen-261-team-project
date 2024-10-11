import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MessageService } from './message.service';
import { catchError, Observable, of, tap } from 'rxjs';
import { Need } from './Need';

@Injectable({
  providedIn: 'root',
})
export class CupboardService {
  constructor(
    private http: HttpClient,
    private messageService: MessageService
  ) {}

  private needsUrl = 'http://localhost:8080/needs';

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  /** GET needs from the server */
  getNeeds(): Observable<Need[]> {
    return this.http.get<Need[]>(this.needsUrl).pipe(
      tap((_) => this.log('fetched needs')),
      catchError(this.handleError<Need[]>('getNeeds', []))
    );
  }

  /** GET needs from the server */
  getNeed(id: string): Observable<Need> {
    return this.http.get<Need>(this.needsUrl + "/" + id).pipe(
      tap((_) => this.log('fetched need')),
      catchError(this.handleError<Need>('getNeeds'))
    );
  }

  /** SEARCH needs from the server */
  searchNeeds(text: string): Observable<Need[]> {
    return this.http.get<Need[]>(this.needsUrl + "/?search=" + text).pipe(
      tap((_) => this.log('searched needs')),
      catchError(this.handleError<Need[]>('searchNeeds', []))
    );
  }

  /** POST need to the server */
  addNeed(need: Need): Observable<Need> {
    return this.http.post<Need>(this.needsUrl, need, this.httpOptions).pipe(
      tap((newNeed: Need) => this.log(`added need w/ id=${newNeed.id}`)),
      catchError(this.handleError<Need>('addNeed'))
    );
  }

  updateNeed(need: Need): Observable<any> {
    const url = `${this.needsUrl}/${need.id}`;
    return this.http.put<Need>(url, need, this.httpOptions).pipe(
      tap(_ => this.log(`updated need id=${need.id}`)),
      catchError(this.handleError<Need>('updateNeed'))
    );
  }

  /** Log a CupboardService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`CupboardService: ${message}`);
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
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
