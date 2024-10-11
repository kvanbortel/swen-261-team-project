import { Component, Injectable } from '@angular/core';

@Component({
  selector: 'app-storage',
  templateUrl: './storage.component.html',
  styleUrl: './storage.component.css'
})

@Injectable({
  providedIn: 'root'
})
export class StorageComponent {
  isAdmin: number = 0;
  name: string = '';

  setisAdmin(isAdmin:number){
    this.isAdmin = isAdmin;
  }
  
  setName(name:string){
    this.name = name;
  }

  getName(){
    return name;
  }
}
