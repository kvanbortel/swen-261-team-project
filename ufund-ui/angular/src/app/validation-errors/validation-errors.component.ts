import { Component, Input } from '@angular/core';
import { AbstractControl } from '@angular/forms';

@Component({
  selector: 'app-validation-errors',
  templateUrl: './validation-errors.component.html',
  styleUrl: './validation-errors.component.css',
})
export class ValidationErrorsComponent {
  @Input() control: AbstractControl | null = null;
  @Input() fieldName: string = '';
  @Input() errorMessages: { [key: string]: string } = {};

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
    return ''; // No error
  }
}
