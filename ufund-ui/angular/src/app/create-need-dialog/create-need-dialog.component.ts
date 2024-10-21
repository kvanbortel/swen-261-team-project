import { Component, Inject } from '@angular/core';
import { DialogData } from '../create-need/create-need.component';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CupboardService } from '../cupboard.service';
import { ValidationErrorsService } from '../validation-errors.service';
import { Need } from '../Need';

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
    private cupboardService: CupboardService,
    private validationService: ValidationErrorsService
  ) {
    // Initialize the form with form controls
    this.createNeedForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      description: ['', [Validators.required]],
      cost: ['', [Validators.required, Validators.min(0), Validators.pattern(/^-?\d+(\.\d{1,2})?$/)]],
      demandRating: ['', [Validators.required, Validators.min(0), Validators.max(100),
                          Validators.pattern(/^-?\d+(\.\d{1})?$/)]],
      quantity: ['', [Validators.required, Validators.min(1), Validators.pattern(/^-?\d+$/)]],
    });

    if (data.mode === 'update') {
      this.createNeedForm.patchValue({
        name: data.name,
        description: data.description,
        cost: data.cost,
        demandRating: data.demandRating,
        quantity: data.quantity
      });
    }
  }

  getErrorMessage(control: AbstractControl | null, fieldName: string, errorMessages: { [key: string]: string }): string {
    return this.validationService.getErrorMessage(control, fieldName, errorMessages);
  }

  // This method is called when the form is submitted
  onSubmit() {
    if (this.createNeedForm.invalid) {
      return;
    }

    const needData: Need = {
      id: this.data.need?.id, // Include the ID for updates
      name: this.createNeedForm.value.name,
      description: this.createNeedForm.value.description,
      cost: this.createNeedForm.value.cost,
      demandRating: this.createNeedForm.value.demandRating,
      quantity: this.createNeedForm.value.quantity,
      fulfillmentStatus: this.data.need?.fulfillmenStatus
    };

    // TODO: Set the mode back, right now it bugs out if we try to open the add form twice
    if (this.data.mode === 'add') {
      // Call the service to add the need
      this.cupboardService.addNeed(needData).subscribe({
        next: (response) => {
          console.log(`Sucessfully added need: ${response}`)
          this.dialogRef.close(response);
        },
        error: (error) => {
          console.error('Error adding need', error);
        }
      });
    }
    else if (this.data.mode === 'update' && this.data.need) {
      // Call the service to update the need
      this.cupboardService.updateNeed(needData).subscribe({
        next: (response) => {
          console.log(`Sucessfully updated need: ${response}`)
          this.dialogRef.close(response);
        },
        error: (error) => {
          console.error('Error updating need', error);
        }
      });
    }
  }
}
