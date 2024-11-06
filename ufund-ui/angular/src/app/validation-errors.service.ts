import { Injectable } from '@angular/core';
import { AbstractControl } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class ValidationErrorsService {

  constructor() { }

  getErrorMessage(control: AbstractControl | null, fieldName: string, errorMessages: { [key: string]: string }): string {
    if (control?.hasError('required')) {
      return `${fieldName} is required.`;
    }
    if (control?.hasError('pattern') && errorMessages['pattern']) {
      return errorMessages['pattern'];
    }
    if (control?.hasError('min') && errorMessages['min']) {
      return errorMessages['min'];
    }
    if (control?.hasError('max') && errorMessages['max']) {
      return errorMessages['max'];
    }
    if (control?.hasError('maxlength') && errorMessages['maxLength']) {
      return errorMessages['maxLength'];
    }
    if (control?.hasError('email') && errorMessages['email']) {
      return errorMessages['email'];
    }
    return ''; // No error
  }
}
