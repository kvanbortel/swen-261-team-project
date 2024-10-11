import { Component, Input } from '@angular/core';
import { Need } from '../Need';
import { LoginComponent } from '../login/login.component';

@Component({
  selector: 'app-cupboard-need',
  templateUrl: './cupboard-need.component.html',
  styleUrl: './cupboard-need.component.css',
})
export class CupboardNeedComponent {
  isAdmin: number = this.LoginComponent.isAdmin();
  @Input() need?: Need;

  constructor(private LoginComponent: LoginComponent) {}

  retrieveNeed() {
    return this.need ? Object.keys(this.need) : [];
  }
}
