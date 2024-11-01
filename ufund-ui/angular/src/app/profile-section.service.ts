import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

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
  private usersUrl = 'http://localhost:8080/users';

  constructor(private http: HttpClient) { }

  getUserProfile(userId: string): Observable<UserProfile> {
    return this.http.get<UserProfile>(`${this.usersUrl}/${userId}`);
  }
}
