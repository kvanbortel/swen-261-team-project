import { Component } from '@angular/core';
import { AuthService } from '../storage/auth.service';

@Component({
  selector: 'app-basket-page',
  templateUrl: './basket-page.component.html',
  styleUrl: './basket-page.component.css'
})
export class BasketPageComponent {
  constructor(public authService: AuthService) {}

  ngOnInit(){

    let name = localStorage.getItem("name");
    if(name != null){
      this.authService.setName(name);
    }
    let image = localStorage.getItem("image")
    if(image != null){
      this.authService.setImage(image);
    }
  }
}
