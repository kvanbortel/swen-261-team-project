import {Region} from "./region";

export interface ProfileData {
  alias: string,
  region: Region,
  pronouns: string,
  bio: string,
  password: string,
  email: string,
  phoneNumber: string,
  ssn: string
  profile?: any;
}
