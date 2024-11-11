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
  showPassword = false;

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
    console.log(this.authService.getAccountsSorted().subscribe({ //add new account if it doesn't exist
      next: (response) => {
          console.log(response)
      },
      error: (err) => {
          console.error("hmm...")
      }
  }))
  
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
    if(name == '' || name == null){//check username and password aren't empty
      this.messageLoginService.add("Username must have substance.", false)
      return
    }
    if(password == '' || password == null){//check username and password aren't empty
      this.messageLoginService.add("Password must have substance.", false)
      return
    }
    if(!this.onlyLettersAndNumbers(name)){//check that username only contains alphanumeric numbers
      this.messageLoginService.add("Only usernames containing numbers and letters allowed.", false)
      return
    }
    if(name.length > 13){ 
      //check that usernames and passwords  <= 13 in length
      this.messageLoginService.add("Only usernames of length less than or equal to 13 allowed.", false)
      return
    }
    if(name.length < 4){ 
      //check that usernames and passwords are >= than 4 in length
      this.messageLoginService.add("Only usernames of length greater than or equal to 4 allowed.", false)
      return
    }
    if(password.length < 4){ 
      //check that usernames and passwords are >= than 4 and <= 13 in length
      this.messageLoginService.add("Only passwords of length greater than or equal to 4 allowed.", false)
      return
    }
    if(password.length > 13){ 
      //check that usernames and passwords are >= than 4 and <= 13 in length
      this.messageLoginService.add("Only passwords of length less than or equal to 13 allowed.", false)
      return
    }
    if(!this.containsLetter(password)){
      this.messageLoginService.add("Passwords must contain a letter.", false)
      return
    }
    if(!this.containsSymbol(password)){
      this.messageLoginService.add("Passwords must contain a symbol.", false)
      return
    }
    if(!this.containsNumber(password)){
      this.messageLoginService.add("Passwords must contain a number.", false)
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
        localStorage.setItem("image", result.imageLink)
        console.log("logged in as user", result)
        this.router.navigate(['/home']);  
      }
      else{
        this.authService.addAccount(name, passwordHash).subscribe({ //add new account if it doesn't exist
          next: (response) => {
              this.authService.setName(name);
              localStorage.setItem("name", name);
              localStorage.setItem("image", response.imageLink)
              console.log("logged in as user", response)
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
  
  popup = true;
  closeDiv() {
    this.popup = false;
  }

  showDiv(){
    if(!this.popup){
      return{
        display: "none"
      }
    }
    return
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
    localStorage.setItem("image", '');
  }

  //regex function that returns whether a string only consists of letters and numbers
  onlyLettersAndNumbers(str : string) {
    return Boolean(str.match(/^[A-Za-z0-9]*$/));
  }

  containsSymbol(str: string){
    return Boolean(str.match(/[-!$%^&@#*()_+|~=`{}\[\]:";'<>?,.\/]/));
  }

  containsLetter(str: string){
    return Boolean(str.match(/[A-Za-z]/));
  }

  containsNumber(str: string){
    return Boolean(str.match(/\d/));
  }



  //regex function that returns whether a string only consists of letters
  isAlpha(str: string) {
    return Boolean(str.match("[a-zA-Z]+"));
}

  
}
