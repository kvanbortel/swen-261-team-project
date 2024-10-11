import { Component, Input } from '@angular/core';
import { CupboardService } from '../cupboard.service';
import { Need } from '../Need';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-single-need',
  templateUrl: './single-need.component.html',
  styleUrl: './single-need.component.css'
})
export class SingleNeedComponent {

  // Should this fetch the need again? It doesn't really need to...

  @Input() need?: Need;

  constructor(private cupboardService: CupboardService) {}

  retrieveNeed() {
    return this.need ? Object.keys(this.need) : [];
  }
}
