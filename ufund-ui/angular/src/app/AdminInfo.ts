export interface AdminInfo {
    userNumber: number;
    needsFunded: number;
    moneyFunded: number;
    lastFundedInstant: string; 
    regions: { [key: string]: number }; 
    fundedByRegion: { [key: string]: number };
}
