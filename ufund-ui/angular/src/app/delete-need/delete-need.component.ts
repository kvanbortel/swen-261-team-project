import { Component, Input } from '@angular/core';
import { Need } from '../Need';
import { CupboardService } from '../cupboard.service';

@Component({
  selector: 'app-delete-need',
  templateUrl: './delete-need.component.html',
  styleUrl: './delete-need.component.css'
})
export class DeleteNeedComponent {
  @Input() need?: Need;
  isAdmin: number = 1; // TODO: Implement once login is done

  constructor(private cupboardService: CupboardService){}
  
  deleteNeed(): void {
    if(!this.need) {
      console.error('No need to delete');
      return;
    }

    const id = this.need.id;

    this.cupboardService.deleteNeed(id).subscribe({
      next: (response) => {
        console.log('Sucessfully deleted need', response);
      },
      error: (error) => {
        console.error('Error deleting need', error);
      }
    });
  }
}
