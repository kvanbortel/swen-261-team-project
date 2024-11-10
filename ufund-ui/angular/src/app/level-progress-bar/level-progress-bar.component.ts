import {Component, Input, OnInit} from '@angular/core';
import {Account} from "../Account";
import {UserLevelService} from "../user-level.service";
import {UserLevel} from "../UserLevel";

@Component({
  selector: 'app-level-progress-bar',
  templateUrl: './level-progress-bar.component.html',
  styleUrl: './level-progress-bar.component.css'
})
export class LevelProgressBarComponent implements OnInit {
  @Input() currentUserAccount?: Account;
  @Input() allAccounts: Account[] = [];
  @Input() PERCENT_INDEX: number;
  @Input() PRO_MIN_FUNDED: number;

  progress: number = 0;
  proRequired: number = 0;
  masterRequired: number = 0;
  championRequired: number = 0;
  userLevel: UserLevel = UserLevel.NOOB;

  constructor(private userLevelService: UserLevelService) {
    this.PERCENT_INDEX = this.userLevelService['PERCENT_INDEX'];
    this.PRO_MIN_FUNDED = this.userLevelService['PRO_MIN_FUNDED'];
  }

  ngOnInit(): void {
    this.calculateProgress();
  }

  calculateProgress(): void {
    if (this.currentUserAccount && this.allAccounts.length > 0) {
      this.userLevel = this.userLevelService.getUserLevel(this.currentUserAccount, this.allAccounts);

      const sortedAccounts = [...this.allAccounts].sort((a, b) => b.moneyFunded - a.moneyFunded);
      const maxDonation = sortedAccounts[0]?.moneyFunded;
      const topPercentIndex = Math.ceil(sortedAccounts.length * this.PERCENT_INDEX) - 1;

      switch (this.userLevel) {
        case UserLevel.MASTER:
          this.championRequired = maxDonation;
          this.progress = (this.currentUserAccount.moneyFunded / this.championRequired) * 100;
          break;
        case UserLevel.PRO:
          this.masterRequired = sortedAccounts[topPercentIndex]?.moneyFunded || 0;
          this.progress = (this.currentUserAccount.moneyFunded / this.masterRequired) * 100;
          break;
        case UserLevel.NOOB:
          this.proRequired = this.PRO_MIN_FUNDED;
          this.progress = (this.currentUserAccount.moneyFunded / this.PRO_MIN_FUNDED) * 100;
          break;
        default:
          this.progress = 100; // You are already a Master
          break;
      }
      this.progress = Math.min(this.progress, 100);
    }
  }

  protected readonly UserLevel = UserLevel;
}
