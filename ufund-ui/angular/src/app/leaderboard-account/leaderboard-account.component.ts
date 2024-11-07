import {
  Component,
  EventEmitter,
  HostBinding,
  Input,
  Output,
  SimpleChanges,
} from '@angular/core';
import { Account } from '../Account';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { AuthService } from '../storage/auth.service';
import numeral, { Numeral } from 'numeral'

@Component({
  selector: 'app-leaderboard-account',
  templateUrl: './leaderboard-account.component.html',
  styleUrl: './leaderboard-account.component.css',
})
export class LeaderboardAccountComponent {
  

  emptyAccount: Account = {
    name: '',
    passwordHash: '',
    imageLink: '',
    basket: {needs: []},
    moneyFunded: 0.0,
    needsFunded: 0

  };

  style: String = ""

  ngOnInit(){console.log(this.account.basket)}

  @Input() account: Account;
  @Input() index: number;

  ngOnChanges(changes: SimpleChanges) {
    this.account = changes['account'].currentValue; // fetch the current value
  }

  constructor(
    public authService: AuthService,
    public formBuilder: FormBuilder
  ) {
    this.account = this.emptyAccount;
    this.index = 1;
  }

  formatMoney(): string{
    return numeral(this.account.moneyFunded).format("($ 0.00 a)")
  }
  
}
