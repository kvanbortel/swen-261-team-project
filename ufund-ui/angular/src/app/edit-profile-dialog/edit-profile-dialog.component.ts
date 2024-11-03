import {Component, Inject, Optional} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ValidationErrorsService} from "../validation-errors.service";
import {Region} from "../region";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ProfileData} from "../ProfileData";
import {ProfileInfo} from "../ProfileInfo";
import {ProfileSectionService} from "../profile-section.service";

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
    @Inject(MAT_DIALOG_DATA) public data: ProfileData,
    private profileSectionService: ProfileSectionService
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
    if (this.profileForm.invalid) {
      return;
    }

    const profileData: ProfileInfo = {
      profilePic: this.data.profile?.profilePic,
      alias: this.profileForm.value.alias,
      region: this.getRegionKey(this.profileForm.value.region),
      pronouns: this.profileForm.value.pronouns,
      bio: this.profileForm.value.bio,
      password: this.profileForm.value.password,
      email: this.profileForm.value.email,
      phoneNumber: this.profileForm.value.phoneNumber,
      ssn: this.profileForm.value.ssn
    };

    // Call the service to update the profileInfo
    this.profileSectionService.updateProfileInfo(profileData).subscribe({
      next: (response) => {
        console.log("Profile data region: " + profileData.region);
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
