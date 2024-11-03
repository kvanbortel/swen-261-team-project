import {Region} from "./region";

export interface ProfileInfo {
  alias: string,
  region: Region,
  pronouns: string,
  bio: string,
  password: string,
  email: string,
  phoneNumber: string,
  ssn: string
  profilePic: string;
}
