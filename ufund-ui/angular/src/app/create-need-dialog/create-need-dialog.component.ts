import { Component, Inject } from '@angular/core';
import { DialogData } from '../create-need/create-need.component';
import {
  MAT_DIALOG_DATA,
  MatDialog,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle,
} from '@angular/material/dialog';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CupboardService } from '../cupboard.service';

@Component({
  selector: 'app-create-need-dialog',
  templateUrl: './create-need-dialog.component.html',
  styleUrl: './create-need-dialog.component.css'
})
export class CreateNeedDialogComponent {
  createNeedForm: FormGroup;

  constructor(
    public dialogRef: MatDialogRef<CreateNeedDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    private formBuilder: FormBuilder,
    private cupboardService: CupboardService
  ) {
    // Initialize the form with form controls
    this.createNeedForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      description: ['', [Validators.required]],
      cost: ['', [Validators.required, Validators.min(0), Validators.pattern(/^\d+(\.\d{1,2})?$/)]],
      demandRating: ['', [Validators.required, Validators.min(0), Validators.max(100), Validators.pattern(/^\d+$/)]],
      quantity: ['', [Validators.required, Validators.min(0), Validators.pattern(/^\d+$/)]],
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  // This method is called when the form is submitted
  onSubmit() {
    if (this.createNeedForm.invalid) {
      return;
    }

    // Call the service to add the need
    this.cupboardService.addNeed(this.createNeedForm.value).subscribe({
      next: (response) => {
        console.log(`Sucessfully added need: ${response}`)
      },
      error: (error) => {
        console.error('Error adding need', error);
      }
    });
    this.dialogRef.close();
  }
}
