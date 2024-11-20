import {
  Component,
  ElementRef,
  EventEmitter,
  HostBinding,
  HostListener,
  Input,
  Output,
  SimpleChanges,
} from '@angular/core';
import { BasketNeed } from '../BasketNeed';
import { AuthService } from '../storage/auth.service';
import { Need } from '../Need';
import {
  AbstractControl,
  FormBuilder,
  FormControl,
  FormGroup,
  ValidationErrors,
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

  style: String = ""

  emptyBNeed: BasketNeed = { quantity: 1, need: this.emptyNeed };

  @Input() need: BasketNeed;

  ngOnChanges(changes: SimpleChanges) {
    this.need = changes['need'].currentValue; // fetch the current value
    this.quantityForm = this.formBuilder.group({
      quantity: [
        this.need.quantity,
        [Validators.min(1), Validators.max(this.getNeed().quantity), Validators.required, this.integerValidator],
      ],
    });
  }

  getNeed() {
    return this.need ? this.need.need : this.emptyNeed;
  }

  quantityForm: FormGroup; // Instantiating our form

  constructor(
    public authService: AuthService,
    public formBuilder: FormBuilder,
    private elementRef: ElementRef
  ) {
    this.need = this.emptyBNeed;
    this.quantityForm = formBuilder.group({
      quantity: [
        '',
        [Validators.min(1), Validators.max(this.getNeed().quantity), Validators.required, this.integerValidator],
      ],
    });
  }

  integerValidator(control: AbstractControl): ValidationErrors | null {
    if (control.value && !Number.isInteger(Number(control.value))) {
      return { notInteger: true };
    }
    return null;
  }

  @HostListener('document:click', ['$event'])
  clickOutside(event: Event) {
    if (!this.elementRef.nativeElement.contains(event.target)) {
      this.quantityForm.setValue({"quantity": this.need.quantity})
    }
  }

  addQuantity() {
    if (this.quantityForm.valid && this.quantityForm.value.quantity > 0) {
      this.authService.addToBasket(
        this.quantityForm.value.quantity - this.need.quantity,
        this.need.need
      ).subscribe();
      this.need.quantity = this.quantityForm.value.quantity
      var lastBasket = JSON.parse(localStorage.getItem("basket")!) as BasketNeed[];
      console.log("last", lastBasket)
      for(var i = 0; i < lastBasket.length; i++){
        if(lastBasket[i].need.id == this.need.need.id){
          lastBasket[i].quantity = this.quantityForm.value.quantity
          localStorage.setItem("basket", JSON.stringify(lastBasket))
        }
      }
      console.log("new", lastBasket)
    }
  }

  retrieveNeed() {
    return this.need ? Object.keys(this.need) : [];
  }

  @Output() deleteNeed = new EventEmitter<BasketNeed>();

  delete(){
    this.authService.addToBasket(
      -this.need.quantity,
      this.need.need
    ).subscribe();
    this.deleteNeed.emit(this.need);
  }

  isMobile = false;

  @HostListener('window:resize', ['$event'])
  onResize(event: any) {
    this.checkScreenSize();
  }
  checkScreenSize() {
    this.isMobile = window.innerWidth <= 600;
  }

  ngOnInit(){
    this.checkScreenSize()
  }
  
}
