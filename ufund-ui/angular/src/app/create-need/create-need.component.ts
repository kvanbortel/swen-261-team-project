import { Component, Input, } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { CreateNeedDialogComponent } from '../create-need-dialog/create-need-dialog.component';
import { AuthService } from '../storage/auth.service';

export interface DialogData {
  name: String;
  description: String;
  cost: number;
  demandRating: number;
  quantity: number;
  mode: 'add' | 'update';
  need?: any;
}

@Component({
  selector: 'app-create-need',
  templateUrl: './create-need.component.html',
  styleUrl: './create-need.component.css'
})
export class CreateNeedComponent {
  isAdmin: number = this.authService.isAdmin;

  data: DialogData = {
    name: "",
    description: "",
    cost: 0,
    demandRating: 0,
    quantity: 0,
    mode: 'add'
  }

  constructor(public dialog: MatDialog, public authService: AuthService) {}

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
