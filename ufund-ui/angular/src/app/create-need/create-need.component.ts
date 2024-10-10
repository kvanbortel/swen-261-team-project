import { Component, inject, Inject, Input, model } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CupboardService } from '../cupboard.service';
import { Need } from '../Need';
import {MatButtonModule} from '@angular/material/button';
import {
  MAT_DIALOG_DATA,
  MatDialog,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle,
} from '@angular/material/dialog';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
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
      width: '250px',
      data: this.data,
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.data = result;
    });
  }
}

// @Component({
//   selector: 'app-create-need-dialog',
//   templateUrl: './create-need-dialog.component.html',
// })
// export class CreateNeedDialog {



// }
