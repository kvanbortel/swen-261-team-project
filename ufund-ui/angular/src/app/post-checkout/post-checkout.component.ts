import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-post-checkout',
  templateUrl: './post-checkout.component.html',
  styleUrl: './post-checkout.component.css',
})
export class PostCheckoutComponent {
  amount: number = 0;
  cost: number = 0;
  error: boolean = false;

  constructor(private router: Router) {
    const navigation = this.router.getCurrentNavigation();
    console.log(navigation)
    if (navigation?.extras.state) {
      this.error = navigation.extras.state['error'];
      this.amount = navigation.extras.state['amount'];
      this.cost = navigation.extras.state['cost'];
      console.log('Amount:', this.amount);
      console.log('Cost:', this.cost);
    } 
    else {
      console.log('No data received');
    }
  }
}
