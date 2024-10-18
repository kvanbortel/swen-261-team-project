import { Component, EventEmitter, Input, Output } from '@angular/core';
import { BasketNeed } from '../BasketNeed';
import { AuthService } from '../storage/auth.service';
import { Need } from '../Need';

@Component({
  selector: 'app-basket-need',
  templateUrl: './basket-need.component.html',
  styleUrl: './basket-need.component.css'
})
export class BasketNeedComponent {
  @Input() need?: BasketNeed;

  emptyNeed: Need = {id: "",
    name: "",
    description: "",
    fulfillmentStatus: false,
    demandRating: 1.0,
    cost: 1.0,
    quantity: 1 }

  getNeed(){
    return this.need ?  this.need.need : this.emptyNeed
  }

  constructor(public authService: AuthService) {}

  retrieveNeed() {
    return this.need ? Object.keys(this.need) : [];
  }

  @Output() dataFromChild = new EventEmitter<BasketNeed>();

  sendData() {
    this.dataFromChild.emit(this.need);
  }

  sendUpdateNeeds(){
    // tell the parent to update its needs
    this.dataFromChild.emit();
  }
}
