import { Component, Injectable } from '@angular/core';
import { StorageComponent } from '../storage/storage.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})

export class LoginComponent {

  account: string = "";
  admin: number = 0;
  
  constructor(public storageComponent: StorageComponent) {}

  login(account: string){
    if(account == "admin"){
      this.storageComponent.setisAdmin(1);
      return;
    }
   this.storageComponent.setName(account);
   console.log(account);
    
  }

  ngOnInit(): void{
    this.storageComponent.setisAdmin(0);
    this.storageComponent.setName('');

  }

}
