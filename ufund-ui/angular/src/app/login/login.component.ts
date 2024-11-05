import { Component, Injectable } from '@angular/core';
import { AuthService } from '../storage/auth.service';
import { catchError, Observable, of, tap } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { MessageService } from '../message.service';
import { md5 } from 'js-md5';
import { Account } from '../Account'
import { waitForAsync } from '@angular/core/testing';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  name: string = '';
  admin: number = 0;

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  };

  constructor(
    public authService: AuthService,
    private http: HttpClient,
    private router: Router,
    public messageLoginService: MessageService
  ) {}

  login(name: string, password: string) {
    this.messageLoginService.clear()
    let passwordHash = md5(password)
    if(name == 'admin'){
      if(passwordHash == "c46b83a30eaa502d9c4630426f3e87e6"){
        this.authService.setName("admin"); 
        localStorage.setItem("name", name); 
        this.router.navigate(['/home']); 
        return 
      }
      this.messageLoginService.add("Incorrect password.", false)//incorrect admin password
      return
    }
    if(name == '' || name == null || password == ''){//check username and password aren't empty
      this.messageLoginService.add("Username and password must have substance.", false)
      return
    }
    if(!this.onlyLettersAndNumbers(name)){//check that username only contains alphanumeric numbers
      this.messageLoginService.add("Only usernames containing alphanumeric characters allowed.", false)
      return
    }
    if(name.length > 13 || password.length > 13 || name.length < 4 || password.length < 4){ 
      //check that usernames and passwords are >= than 4 and <= 13 in length
      this.messageLoginService.add("Only usernames and passwords of length greater than 4 and less than 13.", false)
      return
    }
    if(!this.containsSymbolLetterandNum(password)){
      this.messageLoginService.add("Passwords must contain a letter, number, and symbol.", false)
      return
    }
    if(!this.isAlpha(name.substring(0, 1))){ //check that usernames start with a letter 
      this.messageLoginService.add("Only usernames starting with a letter allowed.", false)
      return
    }
    this.authService.getAccount(name).subscribe( (result) =>{ //get account from backend and subscribe to the result
      if(result){ 
        if(passwordHash != result.passwordHash){ //if account already exists, check if password hashes are equal
          this.messageLoginService.add("Incorrect password.", false)
          return
        }
        this.authService.setName(name); 
        localStorage.setItem("name", name); 
        this.router.navigate(['/home']);  
      }
      else{
        this.authService.addAccount(name, passwordHash).subscribe({ //add new account if it doesn't exist
          next: (response) => {
              this.authService.setName(name);
              localStorage.setItem("name", name);
              this.router.navigate(['/home']);
          },
          error: (err) => {
              console.error("Error creating account:", err);
              this.messageLoginService.add("Failed to create account. Please try again.", false);
          }
      });
      }
    })
  }

  //function mapping enter key to login function for input fields in login component
  submitEnter(event: KeyboardEvent, name: string, passwordHash: string) {
    if (event.key === 'Enter') {
      this.login(name, passwordHash);
    }
  }

  //runs on initialization, resets credentials on login page
  ngOnInit(): void {
    this.messageLoginService.add('', false);
    this.authService.setName('');
    localStorage.setItem("name", '');
  }

  //regex function that returns whether a string only consists of letters and numbers
  onlyLettersAndNumbers(str : string) {
    return Boolean(str.match(/^[A-Za-z0-9]*$/));
  }

  containsSymbolLetterandNum(str: string){
    return Boolean(str.match(/[-!$%^&@#*()_+|~=`{}\[\]:";'<>?,.\/]/)) && 
          Boolean(str.match(/\d/)) &&
          Boolean(str.match(/[A-Za-z]/));
  }

  //regex function that returns whether a string only consists of letters
  isAlpha(str: string) {
    return Boolean(str.match("[a-zA-Z]+"));
}

  
}
