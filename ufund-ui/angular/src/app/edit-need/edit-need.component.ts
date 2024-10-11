import { Component, Input } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { CreateNeedDialogComponent } from '../create-need-dialog/create-need-dialog.component';
import { DialogData } from '../create-need/create-need.component';
import { Need } from '../Need';
import { AuthService } from '../storage/auth.service';

@Component({
  selector: 'app-edit-need',
  templateUrl: './edit-need.component.html',
  styleUrl: './edit-need.component.css'
})
export class EditNeedComponent {
  @Input() need?: Need;
  isAdmin: number = this.authService.isAdmin;
  data!: DialogData;

  constructor(public dialog: MatDialog, public authService: AuthService) {}

  openDialog(): void {
    // Populated with current Need's data
    this.data = {
      name: this.need?.name ?? "",
      description: this.need?.description ?? "",
      cost: this.need?.cost ?? 0,
      demandRating: this.need?.demandRating ?? 0,
      quantity: this.need?.quantity ?? 0,
      mode: 'update',
      need: this.need
    }

    const dialogRef = this.dialog.open(CreateNeedDialogComponent, {
      data: this.data,
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.data = result;
    });
  }
}
