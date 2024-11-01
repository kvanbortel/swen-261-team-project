import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {catchError, Observable, tap} from "rxjs";
import {Need} from "./Need";

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

  constructor(private http: HttpClient) {
    let accountName = localStorage.getItem("name");
  }

  getUserProfile(accountName: string): Observable<UserProfile> {
    return this.http.get<UserProfile>(`${this.usersUrl}/${accountName}/profileInfo`);
  }

  // TODO
  updateProfileInfo(profileInfo: ProfileInfo): Observable<any> {
      return this.http.put<ProfileInfo>(this.usersUrl, profileInfo, this.httpOptions).pipe(
        tap(_ => console.log(`updated profileInfo id=${profileInfo.id}`)),
        catchError(this.handleError<ProfileInfo>('updateProfileInfo'))
      );
  }

}
