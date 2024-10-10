import { Component, Input, } from '@angular/core';
import { Need } from '../Need';
import { MatDialog } from '@angular/material/dialog';
import { CreateNeedDialogComponent } from '../create-need-dialog/create-need-dialog.component';

export interface DialogData {
  name: string;
  description: string;
  cost: number;
  demandRating: number;
  quantity: number;
}

@Component({
  selector: 'app-create-need',
  templateUrl: './create-need.component.html',
  styleUrl: './create-need.component.css'
})
export class CreateNeedComponent {
  @Input() need?: Need;
  isAdmin: number = 1; // TODO: Implement once login is done

  data: DialogData = {
    name: "",
    description: "",
    cost: 0,
    demandRating: 0,
    quantity: 0
  }

  constructor(
    public dialog: MatDialog
  ) {}

  openDialog(): void {
    const dialogRef = this.dialog.open(CreateNeedDialogComponent, {
      data: this.data,
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.data = result;
    });
  }
}
