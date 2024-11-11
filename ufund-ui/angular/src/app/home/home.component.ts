import { Component, HostListener } from '@angular/core';
import { AuthService } from '../storage/auth.service';
import { MessageService } from '../message.service';
import { trigger, style, animate, transition } from '@angular/animations';


@Component({
  selector: 'app-home-page',
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

  isMobile = false;

  @HostListener('window:resize', ['$event'])
  onResize(event: any) {
    this.checkScreenSize();
  }
  checkScreenSize() {
    this.isMobile = window.innerWidth <= 600;
  }

  isAdmin: boolean = this.authService.isAdmin();

  ngOnInit(){
    let name = localStorage.getItem("name");
    if(name != null){
      this.authService.setName(name);
    }
    let image = localStorage.getItem("image")
    if(image != null){
      this.authService.setImage(image);
    }
    this.isAdmin = this.authService.isAdmin();

    this.checkScreenSize();
  }
}
