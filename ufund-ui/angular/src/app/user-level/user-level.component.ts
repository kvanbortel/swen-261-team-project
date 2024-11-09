import {Component, Input, OnInit} from '@angular/core';
import {UserLevel} from "../UserLevel";
import {Account} from "../Account";
import {UserLevelService} from "../user-level.service";

@Component({
  selector: 'app-user-level',
  templateUrl: './user-level.component.html',
  styleUrl: './user-level.component.css'
})
export class UserLevelComponent implements OnInit {
  @Input() currentUserAccount?: Account;
  @Input() allAccounts: Account[] = [];
  userLevel!: UserLevel | null;
  accountName!: string | null;

  constructor (private userLevelService: UserLevelService) {}

  ngOnInit(): void {
    this.accountName = localStorage.getItem("name");
    if (this.accountName != null) {
      // Fetch all accounts to calculate the user level
      this.userLevelService.getAllAccounts().subscribe(accounts => {
        console.log("All Accounts:", accounts);
        this.allAccounts = accounts;
        this.currentUserAccount = accounts.find(account => account.name === this.accountName);

        if (this.currentUserAccount) {
          console.log("Current User Account:", this.currentUserAccount);
          this.updateUserLevel();
        }
      });
    }
  }

  private updateUserLevel() {
    if (this.currentUserAccount && this.allAccounts.length > 0) {
      this.userLevel = this.userLevelService.getUserLevel(this.currentUserAccount, this.allAccounts);
    }
  }
}
