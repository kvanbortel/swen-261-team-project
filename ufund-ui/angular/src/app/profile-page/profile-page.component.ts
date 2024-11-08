import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {AuthService} from "../storage/auth.service";
import {ProfileSectionService} from "../profile-section.service";
import {ProfileInfo} from "../profile-info";
import {MessageService} from '../message.service';
import {animate, style, transition, trigger} from '@angular/animations';
import {UserLevelService} from "../user-level.service";
import {UserLevel} from "../UserLevel";
import {Account} from "../Account";

@Component({
  selector: 'app-profile',
  templateUrl: './profile-page.component.html',
  styleUrl: './profile-page.component.css',
  animations: [
    trigger('fade', [
      transition(':enter', [
        style({ opacity: 0 }),
        animate('500ms', style({ opacity: 1 }))
      ]),
      transition(':leave', [
        animate('500ms', style({ opacity: 0 }))
      ])
    ])
  ]
})
export class ProfilePageComponent implements OnInit {
  accountName!: string | null;
  profileInfo!: ProfileInfo;
  selectedFile: File | null = null;
  errored = false;
  userLevel: UserLevel = UserLevel.NOOB;
  allAccounts: Account[] = [];
  currentUserAccount?: Account;

  constructor(public authService: AuthService,
              private profileSectionService: ProfileSectionService,
              public messageService: MessageService,
              public userLevelService: UserLevelService
  ) {
  }

  @Output() dataFromChild = new EventEmitter<null>();

  ngOnInit(): void {
    this.accountName = localStorage.getItem("name");
    if (this.accountName != null) {
      this.authService.setName(this.accountName);
      this.profileSectionService.getProfileInfo(this.accountName).subscribe(profile => {
        this.profileInfo = profile
      });

      // Fetch all accounts to calculate the user level
      this.profileSectionService.getAllAccounts().subscribe(accounts => {
        console.log("All Accounts:", accounts);
        this.allAccounts = accounts;
        this.currentUserAccount = accounts.find(account => account.name === this.accountName);

        if (this.currentUserAccount) {
          console.log("Current User Account:", this.currentUserAccount);
          this.updateUserLevel();
        }
      });
    }

    let image = localStorage.getItem("image")
    if(image != null){
      this.authService.setImage(image);
    }
  }

  setProfileInfo(profileInfo: ProfileInfo) {
    this.profileInfo = profileInfo;
  }

  private updateUserLevel() {
    if (this.currentUserAccount && this.allAccounts.length > 0) {
      this.userLevel = this.userLevelService.getUserLevel(this.currentUserAccount, this.allAccounts);
    }
  }

  // whenever a file is uploaded:
  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
    }

    if(this.selectedFile)
    if (this.selectedFile.type === 'image/jpeg' ||
        this.selectedFile.type === 'image/png' ||
        this.selectedFile.type ==='image/jpg') {
      if (this.selectedFile.size > 1000000) {
        // file is too large
        // TODO: Handle error
        this.errored = true;
        console.log("file too large")
        this.messageService.add("File is too large!", true)
        return
      }
    }
    this.errored = false;

    event.preventDefault();
    if (!this.selectedFile) {
      console.error('No file selected!');
      return;
    }

    this.authService.postImage(this.selectedFile)
  }
}
