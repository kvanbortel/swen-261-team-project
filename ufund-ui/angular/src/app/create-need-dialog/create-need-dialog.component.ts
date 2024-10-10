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
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CupboardService } from '../cupboard.service';

@Component({
  selector: 'app-create-need-dialog',
  templateUrl: './create-need-dialog.component.html',
  styleUrl: './create-need-dialog.component.css'
})
export class CreateNeedDialogComponent {
  createNeedForm: FormGroup;
  formSubmitted = false;
  showForm: boolean = false;

  constructor(
    public dialogRef: MatDialogRef<CreateNeedDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    private formBuilder: FormBuilder,
    private router: Router,
    private cupboardService: CupboardService
  ) {
    // Initialize the form with form controls
    this.createNeedForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      description: ['', [Validators.required, Validators.minLength(10)]],
      cost: ['', [Validators.required]],
      demand: ['', [Validators.required]],
      quantity: ['', [Validators.required]],
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  // This method is called when the form is submitted
  onSubmit() {
    this.formSubmitted = true;

    if (this.createNeedForm.invalid) {
      return;
    }

    // Call the service to add the need
    this.cupboardService.addNeed(this.createNeedForm.value).subscribe(
      (response) => {
        // Navigate back to the list or show a success message
        this.router.navigate(['/needs']);
      },
      (error) => {
        console.error('Error adding need', error);
      }
    );
  }
}
