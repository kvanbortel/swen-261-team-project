import {Component, EventEmitter, Input, Output} from '@angular/core';
import {DialogData} from "../create-need/create-need.component";
import {Region} from "../region";
import {MatDialog} from "@angular/material/dialog";
import {AuthService} from "../storage/auth.service";
import {CreateNeedDialogComponent} from "../create-need-dialog/create-need-dialog.component";
import {EditProfileDialogComponent} from "../edit-profile-dialog/edit-profile-dialog.component";
import {ProfileInfo} from "../profile-info";

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrl: './edit-profile.component.css'
})
export class EditProfileComponent {
  @Input() profileInfo?: ProfileInfo;
  isAdmin: boolean = this.authService.isAdmin();

  constructor(public dialog: MatDialog, public authService: AuthService) {}

  @Output() dataFromChild = new EventEmitter<ProfileInfo>();

  openDialog(): void {
    const dialogRef = this.dialog.open(EditProfileDialogComponent, {
      data: this.profileInfo,
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.profileInfo = result;
      this.dataFromChild.emit();
    });
  }
}
