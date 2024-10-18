import {
  Component,
  EventEmitter,
  Input,
  Output,
  SimpleChanges,
} from '@angular/core';
import { BasketNeed } from '../BasketNeed';
import { AuthService } from '../storage/auth.service';
import { Need } from '../Need';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';

@Component({
  selector: 'app-basket-need',
  templateUrl: './basket-need.component.html',
  styleUrl: './basket-need.component.css',
})
export class BasketNeedComponent {
  emptyNeed: Need = {
    id: '',
    name: '',
    description: '',
    fulfillmentStatus: false,
    demandRating: 1.0,
    cost: 1.0,
    quantity: 1,
  };

  emptyBNeed: BasketNeed = { quantity: 1, need: this.emptyNeed };

  @Input() need: BasketNeed;

  ngOnChanges(changes: SimpleChanges) {
    this.need = changes['need'].currentValue; // fetch the current value
    this.quantityForm = this.formBuilder.group({
      quantity: [
        this.need.quantity,
        [Validators.min(1), Validators.max(this.getNeed().quantity)],
      ],
    });
  }

  getNeed() {
    return this.need ? this.need.need : this.emptyNeed;
  }

  quantityForm: FormGroup; // Instantiating our form

  constructor(
    public authService: AuthService,
    public formBuilder: FormBuilder
  ) {
    this.need = this.emptyBNeed;
    this.quantityForm = formBuilder.group({
      quantity: [
        '',
        [Validators.min(1), Validators.max(this.getNeed().quantity)],
      ],
    });
  }

  addQuantity() {
    this.authService.addToBasket()
  }

  retrieveNeed() {
    return this.need ? Object.keys(this.need) : [];
  }

  @Output() dataFromChild = new EventEmitter<BasketNeed>();

  sendData() {
    this.dataFromChild.emit(this.need);
  }

  sendUpdateNeeds() {
    // tell the parent to update its needs
    this.dataFromChild.emit();
  }
}
