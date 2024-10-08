import { Component, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CupboardService } from '../cupboard.service';
import { Need } from '../Need';

@Component({
  selector: 'app-create-need',
  templateUrl: './create-need.component.html',
  styleUrl: './create-need.component.css'
})
export class CreateNeedComponent {
  @Input() need?: Need;
  createNeedForm: FormGroup;
  formSubmitted = false;
  showForm: boolean = false;

  constructor(
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
