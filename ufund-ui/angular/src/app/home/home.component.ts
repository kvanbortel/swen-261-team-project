import { Component } from '@angular/core';
import { AuthService } from '../storage/auth.service';
import { MessageService } from '../message.service';
import { trigger, style, animate, transition } from '@angular/animations';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
  animations: [
    trigger('fade', [
      transition(':enter', [
        style({ opacity: 0 }),
        animate('500ms', style({ opacity: 1 }))
      ]),
      transition(':leave', [
        animate('500ms', style({ opacity: 0 }))
      ])
    ])
  ]
})
export class HomeComponent {

  constructor(public authService: AuthService, public messageService: MessageService) {}

  isAdmin: boolean = this.authService.isAdmin();

  ngOnInit(){
    let name = localStorage.getItem("name");
    if(name != null){
      this.authService.setName(name);
    }
    this.isAdmin = this.authService.isAdmin();

  }
}