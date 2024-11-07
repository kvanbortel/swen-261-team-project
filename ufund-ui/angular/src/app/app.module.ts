import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { HeaderComponent } from './header/header.component';
import { CupboardNeedComponent } from './cupboard-need/cupboard-need.component';
import { CupboardSearchComponent } from './cupboard-search/cupboard-search.component';
import { SingleNeedComponent } from './single-need/single-need.component';
import { CreateNeedComponent } from './create-need/create-need.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import {MatButtonModule} from '@angular/material/button';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogModule, MatDialogRef,
  MatDialogTitle,
} from '@angular/material/dialog';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import { CreateNeedDialogComponent } from './create-need-dialog/create-need-dialog.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { EditNeedComponent } from './edit-need/edit-need.component';
import { DeleteNeedComponent } from './delete-need/delete-need.component';
import { ConfirmationDialogComponent } from './confirmation-dialog/confirmation-dialog.component';
import { LoginComponent } from './login/login.component';
import { AuthService } from './storage/auth.service';
import { BasketListComponent } from './basket-list/basket-list.component';
import { HomeComponent } from './home/home.component';
import { ViewChangeButtonComponent } from './view-change-button/view-change-button.component';
import { BasketPageComponent } from './basket-page/basket-page.component';
import { BasketNeedComponent } from './basket-need/basket-need.component';
import { PostCheckoutComponent } from './post-checkout/post-checkout.component';
import { MatTooltipModule } from '@angular/material/tooltip';
import { ProfilePicComponent } from './profile-pic/profile-pic.component';
import { ProfilePageComponent } from './profile-page/profile-page.component';
import {MatOption, MatSelect} from "@angular/material/select";
import { ProfileSectionComponent } from './profile-section/profile-section.component';
import { EditProfileDialogComponent } from './edit-profile-dialog/edit-profile-dialog.component';
import { EditProfileComponent } from './edit-profile/edit-profile.component';
import { AdminDashComponent } from './admin-dash/admin-dash.component';
import { PicButtonComponent } from './pic-button/pic-button.component';
import { LeaderboardAccountComponent } from './leaderboard-account/leaderboard-account.component';
import { LeaderboardComponent } from './leaderboard/leaderboard.component';
import { LeaderboardPageComponent } from './leaderboard-page/leaderboard-page.component';
import { LeaderboardAccountProfileComponent } from './leaderboard-account-profile/leaderboard-account-profile.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    CupboardNeedComponent,
    CupboardSearchComponent,
    LoginComponent,
    SingleNeedComponent,
    CreateNeedComponent,
    CreateNeedDialogComponent,
    EditNeedComponent,
    DeleteNeedComponent,
    ConfirmationDialogComponent,
    BasketListComponent,
    HomeComponent,
    ViewChangeButtonComponent,
    BasketPageComponent,
    BasketNeedComponent,
    PostCheckoutComponent,
    ProfilePicComponent,
    LeaderboardComponent,
    LeaderboardAccountComponent,
    AdminDashComponent,
    PicButtonComponent,
    PostCheckoutComponent,
    ProfilePageComponent,
    ProfileSectionComponent,
    EditProfileDialogComponent,
    EditProfileComponent,
    LeaderboardPageComponent,
    LeaderboardAccountProfileComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatTooltipModule,
    FormsModule,
    MatButtonModule,
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    MatDialogClose,
    MatDialogModule,
    BrowserAnimationsModule,
    MatSelect,
    MatOption
  ],
  providers: [LoginComponent,
              {provide: MatDialogRef, useValue: {}},
              {provide: MAT_DIALOG_DATA, useValue: {}}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
