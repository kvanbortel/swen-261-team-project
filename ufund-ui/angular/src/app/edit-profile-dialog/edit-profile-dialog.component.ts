import {Component, Inject} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ValidationErrorsService} from "../validation-errors.service";
import {Region} from "../region";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {DialogData} from "../create-need/create-need.component";
import {CupboardService} from "../cupboard.service";

@Component({
  selector: 'app-profile-info',
  templateUrl: './edit-profile-dialog.component.html',
  styleUrl: './edit-profile-dialog.component.css'
})
export class EditProfileDialogComponent {
  profileForm: FormGroup;
  regions = Object.values(Region); // Get enum values for dropdown

  constructor(
    private formBuilder: FormBuilder,
    private validationService: ValidationErrorsService,
    public dialogRef: MatDialogRef<EditProfileDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
  ) {
    this.profileForm = this.formBuilder.group({
      alias: [''],
      region: ['', Validators.required],
      pronouns: [''],
      bio: ['', Validators.max(150)],
      password: ['', Validators.required],
      email: ['', Validators.email],
      phoneNumber: ['', Validators.pattern(/(?:\(\d{3}\)|\d{3})[-.\s]?\d{3}[-.\s]?\d{4}$/)],
      ssn: ['', Validators.pattern(/^\d{3}-\d{2}-\d{4}$/)]
    });

    this.profileForm.patchValue({
      alias: data.alias,
      region: data.region,
      pronouns: data.pronouns,
      bio: data.bio,
      password: data.password,
      email: data.email,
      phoneNumber: data.phoneNumber,
      ssn: data.ssn
    });
  }

  getErrorMessage(control: AbstractControl | null, fieldName: string, errorMessages: { [key: string]: string }): string {
    return this.validationService.getErrorMessage(control, fieldName, errorMessages);
  }

  onSubmit(): void {
    if (this.profileForm?.valid) {
      console.log('Form data:', this.profileForm.value);
    }
  }
}
