import { Component } from '@angular/core';
import { Need } from '../Need';
import { CupboardService } from '../cupboard.service';

@Component({
  selector: 'app-cupboard',
  templateUrl: './cupboard.component.html',
  styleUrl: './cupboard.component.css'
})
export class CupboardComponent {
  needs: Need[] = [];

  constructor(private cupboardService: CupboardService) { }

  ngOnInit(): void {
    this.getNeeds();
  }

  getNeeds(): void {
    this.cupboardService.getNeeds()
    .subscribe(needs => this.needs = needs);
  }

}
