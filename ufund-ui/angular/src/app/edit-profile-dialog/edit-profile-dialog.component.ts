import {Component, Inject, OnChanges, Optional, SimpleChanges} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ValidationErrorsService} from "../validation-errors.service";
import {Region} from "../region";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ProfileSectionService} from "../profile-section.service";
import {ProfileInfo} from "../profile-info";

@Component({
  selector: 'app-edit-profile-dialog',
  templateUrl: './edit-profile-dialog.component.html',
  styleUrl: './edit-profile-dialog.component.css'
})
export class EditProfileDialogComponent {
  profileForm: FormGroup;
  regions = Object.values(Region); // Get enum values for dropdown

  constructor(
    private formBuilder: FormBuilder,
    private validationService: ValidationErrorsService,
    @Optional() public dialogRef: MatDialogRef<EditProfileDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ProfileInfo,
    private profileSectionService: ProfileSectionService
  ) {
    this.profileForm = this.formBuilder.group({
      alias: ['', [Validators.pattern(/^[a-zA-Z][a-zA-Z0-9]*$/), Validators.maxLength(13)]],
      region: ['', Validators.required],
      pronouns: ['', Validators.maxLength(50)],
      bio: ['', Validators.maxLength(250)],
      email: ['', Validators.email],
      phoneNumber: ['', Validators.pattern(/^\(?\d{3}\)?[-.\s]?\d{3}[-.\s]?\d{4}$/)],
      ssn: ['', Validators.pattern(/^\d{3}-?\d{2}-?\d{4}$/)],
      privacy: ['']
    });

    this.profileForm.get('phoneNumber')?.valueChanges.subscribe(value => {
      if (value)
        this.formatPhoneNumber(value);
    });

    this.profileForm.get('ssn')?.valueChanges.subscribe(value => {
      if (value)
        this.formatSSN(value);
    });

    this.profileForm.patchValue({
      alias: this.data.alias,
      region: this.data.region,
      pronouns: this.data.pronouns,
      bio: this.data.bio,
      email: this.data.email,
      phoneNumber: this.data.phoneNumber || '',
      ssn: this.data.ssn,
      privacy: this.data.privacy
    });
  }

  // Dynamically format phone number as (123) 456-7890
  formatPhoneNumber(value: string): void {
    let cleanedValue = value.replace(/\D/g, '');  // Remove non-digit characters
    if (cleanedValue.length < 3) {
      cleanedValue = `(${cleanedValue}`;
    }
    else if (cleanedValue.length == 3) {
      cleanedValue = `(${cleanedValue})`;
    }
    else if (cleanedValue.length < 6) {
      cleanedValue = `(${cleanedValue.slice(0, 3)}) ${cleanedValue.slice(3)}`;
    }
    else if (cleanedValue.length == 6) {
      cleanedValue = `(${cleanedValue.slice(0, 3)}) ${cleanedValue.slice(3)}-`;
    }
    else {
      cleanedValue = `(${cleanedValue.slice(0, 3)}) ${cleanedValue.slice(3, 6)}-${cleanedValue.slice(6, 10)}`;
    }

    this.profileForm.get('phoneNumber')?.setValue(cleanedValue, { emitEvent: false });
  }

  // Dynamically format SSN as 123-45-6789
  formatSSN(value: string): void {
    let cleanedValue = value.replace(/\D/g, '');  // Remove non-digit characters
    if (cleanedValue.length < 3) {
      cleanedValue = cleanedValue.slice(0, 3);
    }
    else if (cleanedValue.length == 3) {
      cleanedValue = `${cleanedValue.slice(0, 3)}-`;
    }
    else if (cleanedValue.length < 5) {
      cleanedValue = `${cleanedValue.slice(0, 3)}-${cleanedValue.slice(3, 5)}`;
    }
    else if (cleanedValue.length == 5) {
      cleanedValue = `${cleanedValue.slice(0, 3)}-${cleanedValue.slice(3, 5)}-`;
    }
    else {
      cleanedValue = `${cleanedValue.slice(0, 3)}-${cleanedValue.slice(3, 5)}-${cleanedValue.slice(5, 9)}`;
    }

    this.profileForm.get('ssn')?.setValue(cleanedValue, { emitEvent: false });
  }


  getErrorMessage(control: AbstractControl | null, fieldName: string, errorMessages: { [key: string]: string }): string {
    return this.validationService.getErrorMessage(control, fieldName, errorMessages);
  }

  onSubmit(): void {
    if (this.profileForm.invalid) {
      return;
    }

    const profileData: ProfileInfo = new ProfileInfo({
      profilePic: this.data.profilePic,
      alias: this.profileForm.value.alias,
      region: this.getRegionKey(this.profileForm.value.region),
      pronouns: this.profileForm.value.pronouns,
      bio: this.profileForm.value.bio,
      email: this.profileForm.value.email,
      phoneNumber: this.profileForm.value.phoneNumber,
      ssn: this.profileForm.value.ssn,
      privacy: this.profileForm.value.privacy
    });

    // Call the service to update the profileInfo
    this.profileSectionService.updateProfileInfo(profileData).subscribe({
      next: (response) => {
        console.log(`Successfully updated profileInfo: ${response}`);
        this.dialogRef.close(response);
      },
      error: (error) => {
        console.error('Error updating profileInfo', error);
      }
    });
  }



  // Helper function to get the enum key from the string value
  private getRegionKey(regionValue: string): Region {
    const regionKeys = Object.keys(Region).filter(key => Region[key as keyof typeof Region] === regionValue);
    return regionKeys.length > 0 ? regionKeys[0] as Region : Region.NONE; // Default to NONE if not found
  }
}
