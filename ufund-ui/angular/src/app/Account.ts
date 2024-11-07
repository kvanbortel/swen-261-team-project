import { Basket } from "./Basket";
import { ProfileInfo } from "./profile-info";

export interface Account {
    name: string,
    basket: Basket,
    passwordHash: string,
    imageLink: string,
    moneyFunded: number,
    needsFunded: number,
    profileInfo: ProfileInfo;
    
  }
