import { Component, Input } from '@angular/core';
import { Need } from '../Need';
import { CupboardService } from '../cupboard.service';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationDialogComponent } from '../confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-delete-need',
  templateUrl: './delete-need.component.html',
  styleUrl: './delete-need.component.css'
})
export class DeleteNeedComponent {
  @Input() need?: Need;
  isAdmin: number = 1; // TODO: Implement once login is done

  constructor(private cupboardService: CupboardService, public dialog: MatDialog) {}
  
  promptDelete(): void {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      disableClose: false
    });
    dialogRef.componentInstance.confirmMessage = `Are you sure you want to delete ${this.need?.name}?`

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      if (result) {
        this.deleteNeed();
      }
    });
  }

  private deleteNeed() {
    if(!this.need) {
      console.error('No Need to delete');
      return;
    }

    const id = this.need.id;

    this.cupboardService.deleteNeed(id).subscribe({
      next: (response) => {
        console.log('Sucessfully deleted Need', response);
      },
      error: (error) => {
        console.error('Error deleting Need', error);
      }
    });
  }
}
