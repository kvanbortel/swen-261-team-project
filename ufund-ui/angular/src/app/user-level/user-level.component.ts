import {Component, Input, OnInit} from '@angular/core';
import {UserLevel} from "../UserLevel";
import {Account} from "../Account";
import {UserLevelService} from "../user-level.service";
import {AuthService} from "../storage/auth.service";

@Component({
  selector: 'app-user-level',
  templateUrl: './user-level.component.html',
  styleUrl: './user-level.component.css'
})
export class UserLevelComponent implements OnInit {
  @Input() currentUserAccount?: Account;
  @Input() allAccounts: Account[] = [];
  userLevel!: UserLevel | null;

  constructor (private userLevelService: UserLevelService, public authService: AuthService) {}

  ngOnInit(): void {
    if (this.currentUserAccount) {
      console.log("Current User Account:", this.currentUserAccount);
      this.updateUserLevel();
    }
  }

  private updateUserLevel() {
    if (this.currentUserAccount && this.allAccounts.length > 0) {
      this.userLevel = this.userLevelService.getUserLevel(this.currentUserAccount, this.allAccounts);
    }
  }
}
