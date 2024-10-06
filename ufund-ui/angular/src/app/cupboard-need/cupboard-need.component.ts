import { Component, Input } from '@angular/core';
import { Need } from '../Need';

@Component({
  selector: 'app-cupboard-need',
  templateUrl: './cupboard-need.component.html',
  styleUrl: './cupboard-need.component.css',
})
export class CupboardNeedComponent {
  @Input() need?: Need;

  constructor() {}

  retrieveNeed() {
    return this.need ? Object.keys(this.need) : [];
  }
}
