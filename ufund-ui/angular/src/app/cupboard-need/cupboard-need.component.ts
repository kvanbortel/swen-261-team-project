import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Need } from '../Need';

@Component({
  selector: 'app-cupboard-need',
  templateUrl: './cupboard-need.component.html',
  styleUrl: './cupboard-need.component.css',
})
export class CupboardNeedComponent {
  @Input() need?: Need;

  isAdmin: boolean = true;

  constructor() {}

  retrieveNeed() {
    return this.need ? Object.keys(this.need) : [];
  }

  @Output() dataFromChild = new EventEmitter<Need>();

  sendData() {
    this.dataFromChild.emit(this.need);
  }

  addToBasket(){
  }

  sendUpdateNeeds(){
    // tell the parent to update its needs
    this.dataFromChild.emit();
  }

}
