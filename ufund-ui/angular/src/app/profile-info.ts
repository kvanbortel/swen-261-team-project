import {Region} from "./region";
import {ProfileInfoJSON} from "./ProfileInfoJSON";

export class ProfileInfo {
  alias: string;
  region: Region;
  pronouns: string;
  bio: string;
  email: string;
  phoneNumber: string;
  ssn: string;
  profilePic: string;

  constructor(data: ProfileInfoJSON) {
    this.alias = data.alias;
    this.region = Region[data.region as keyof typeof Region];
    this.pronouns = data.pronouns;
    this.bio = data.bio;
    this.email = data.email;
    this.phoneNumber = data.phoneNumber;
    this.ssn = data.ssn;
    this.profilePic = data.profilePic;
  }

  get json(): ProfileInfoJSON {
    return {
      alias: this.alias,
      region: Object.keys(Region).find(k => Region[k as keyof typeof Region] === this.region) ?? Region.NONE,
      pronouns: this.pronouns,
      bio: this.bio,
      email: this.email,
      phoneNumber: this.phoneNumber,
      ssn: this.ssn,
      profilePic: this.profilePic,
    }
  }
}
