import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Need } from '../Need';
import { AuthService } from '../storage/auth.service';
import { MessageService } from '../message.service';

@Component({
  selector: 'app-cupboard-need',
  templateUrl: './cupboard-need.component.html',
  styleUrl: './cupboard-need.component.css',
})
export class CupboardNeedComponent {
  @Input() need?: Need;

  constructor(public authService: AuthService, private messageService: MessageService) {}

  retrieveNeed() {
    return this.need ? Object.keys(this.need) : [];
  }

  @Output() dataFromChild = new EventEmitter<Need>();

  sendData() {
    this.dataFromChild.emit(this.need);
  }

  addToBasket() {
    console.log(this.need);
    this.authService.addToBasket(1, this.need).subscribe({
        next: (response) => {
          if(response.quantity > response.need.quantity){
            // trying to add over the max
            this.messageService.add("Cannot add more of this need", true)
          }
          else{
            // all good to add
            this.messageService.add("Added 1 " + this.need?.name, true)
          }
        },
        error: (err) => {
          console.error('Error adding to basket:', err);
        },
      }
    );

  }

  sendUpdateNeeds(){
    // tell the parent to update its needs
    this.dataFromChild.emit();
  }

}
