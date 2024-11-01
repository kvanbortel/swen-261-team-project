import {Component, Input} from '@angular/core';
import {Need} from "../Need";
import {DialogData} from "../create-need/create-need.component";
import {Region} from "../region";

export interface ProfileData {
 alias: string,
 region: Region,
 pronouns: string,
 bio: string,
 password: string,
 email: string,
 phoneNumber: string,
 ssn: string
}

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrl: './edit-profile.component.css'
})
export class EditProfileComponent {
  data: ProfileData = {
    alias: "",
    region: "",
    pronouns: "",
    bio: "",
    password: "",
    email: "",
    phoneNumber: "",
    ssn: ""
  }

  @Input() profileInfo?: ProfileInfo;
  data!: DialogData;
}
