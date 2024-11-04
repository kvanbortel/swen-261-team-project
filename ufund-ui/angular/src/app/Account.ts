import { Basket } from "./Basket";

export interface Account {
    name: String,
    passwordHash: String,
    imageLink: string,
    basket: Basket;
  }
