import { Component } from '@angular/core';
import {AbstractControl, FormBuilder, Validators} from "@angular/forms";
import {ValidationErrorsService} from "../validation-errors.service";

@Component({
  selector: 'app-profile-info',
  templateUrl: './profile-info.component.html',
  styleUrl: './profile-info.component.css'
})
export class ProfileInfoComponent {
  profileForm: any;

  constructor(
    private formBuilder: FormBuilder,
    private validationService: ValidationErrorsService
  ) {}

  ngOnInit(): void {
    this.profileForm = this.formBuilder.group({
      profilePic: [''],
      alias: [''],
      password: ['', Validators.required],
      email: ['', Validators.email],
      phoneNumber: ['', Validators.pattern(/(?:\(\d{3}\)|\d{3})[-.\s]?\d{3}[-.\s]?\d{4}$/)],
      pronouns: [''],
      bio: ['', Validators.max(150)],
      region: ['', Validators.required],
      ssn: ['', Validators.pattern(/^\d{3}-\d{2}-\d{4}$/)]
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
