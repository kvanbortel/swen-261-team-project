import { Injectable } from '@angular/core';


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  isAdmin: number = 0;
  name: string = '';

  setisAdmin(isAdmin:number){
    this.isAdmin = isAdmin;
  }
  
  setName(name:string){
    this.name = name;
  }

  getName(){
    return this.name;
  }
}
