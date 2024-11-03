import {Component, EventEmitter, Input, Output} from '@angular/core';
import {DialogData} from "../create-need/create-need.component";
import {Region} from "../region";
import {MatDialog} from "@angular/material/dialog";
import {AuthService} from "../storage/auth.service";
import {CreateNeedDialogComponent} from "../create-need-dialog/create-need-dialog.component";
import {EditProfileDialogComponent} from "../edit-profile-dialog/edit-profile-dialog.component";
import {ProfileData} from "../ProfileData";
import {ProfileInfo} from "../ProfileInfo";

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrl: './edit-profile.component.css'
})
export class EditProfileComponent {
  @Input() profileInfo?: ProfileInfo;
  isAdmin: boolean = this.authService.isAdmin();

  data: ProfileData = {
    alias: "",
    region: Region.NONE,
    pronouns: "",
    bio: "",
    password: "",
    email: "",
    phoneNumber: "",
    ssn: ""
  }

  constructor(public dialog: MatDialog, public authService: AuthService) {}

  @Output() dataFromChild = new EventEmitter<null>();

  openDialog(): void {
    // Populated with current profile's data
    this.data = {
      alias: this.profileInfo?.alias ?? "",
      region: this.profileInfo?.region ?? Region.NONE,
      pronouns: this.profileInfo?.pronouns ?? "",
      bio: this.profileInfo?.bio ?? "",
      password: this.profileInfo?.password ?? "",
      email: this.profileInfo?.email ?? "",
      phoneNumber: this.profileInfo?.phoneNumber ?? "",
      ssn: this.profileInfo?.ssn ?? "",
      profile: this.profileInfo
    }

    const dialogRef = this.dialog.open(EditProfileDialogComponent, {
      data: this.data,
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.data = result;
      this.dataFromChild.emit();
    });
  }
}
