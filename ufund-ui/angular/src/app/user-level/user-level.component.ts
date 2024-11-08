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
  @Input() currentUserAccount: Account | null = null;
  @Input() allAccounts: Account[] = [];
  userLevel: UserLevel = UserLevel.NOOB;

  constructor (private userLevelService: UserLevelService) {}

  ngOnInit(): void {
    if (this.currentUserAccount && this.allAccounts)
      this.userLevel = this.userLevelService.getUserLevel(this.currentUserAccount, this.allAccounts);
  }
}
