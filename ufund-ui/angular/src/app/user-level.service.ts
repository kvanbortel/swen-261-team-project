import {Injectable} from '@angular/core';
import {UserLevel} from "./UserLevel";
import {Account} from "./Account";

@Injectable({
  providedIn: 'root'
})
export class UserLevelService {
  private PERCENT_INDEX = 0.05;
  private PRO_MIN_FUNDED = 1000;

  constructor() {
  }

  getUserLevel(account: Account, allAccounts: Account[]) {
    let userLevel: UserLevel = UserLevel.NOOB;

    const moneyFunded = account.moneyFunded;
    const maxDonation = Math.max(...allAccounts.map(acc => acc.moneyFunded));

    // Sort accounts based on moneyFunded in descending order
    const sortedAccounts = allAccounts.sort((a, b) => b.moneyFunded - a.moneyFunded);

    const topPercentIndex = Math.ceil(sortedAccounts.length * -this.PERCENT_INDEX) - 1;

    if (moneyFunded === maxDonation) {
      userLevel = UserLevel.CHAMPION
    }
    else if (sortedAccounts.indexOf(account) <= topPercentIndex) {
      userLevel = UserLevel.MASTER;
    }
    else if (moneyFunded >= this.PRO_MIN_FUNDED) {
      userLevel = UserLevel.PRO;
    }

    return userLevel;
  }
}
