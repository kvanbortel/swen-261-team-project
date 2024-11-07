import { Basket } from "./Basket";

export interface Account {
    name: String,
    basket: Basket,
    passwordHash: String,
    imageLink: string,
    moneyFunded: number,
    needsFunded: number;

    
  }
