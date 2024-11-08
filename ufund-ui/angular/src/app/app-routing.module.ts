import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { CupboardNeedComponent } from './cupboard-need/cupboard-need.component';
import { CupboardSearchComponent } from './cupboard-search/cupboard-search.component';
import { HomeComponent } from './home/home.component';
import { BasketPageComponent } from './basket-page/basket-page.component';
import { PostCheckoutComponent } from './post-checkout/post-checkout.component';
import { AuthService } from './storage/auth.service';
import {ProfilePageComponent} from "./profile-page/profile-page.component";
import { LeaderboardComponent } from './leaderboard/leaderboard.component';
import { LeaderboardPageComponent } from './leaderboard-page/leaderboard-page.component';
import { ProfileOtherComponent } from './profile-other/profile-other.component';

const routes: Routes = [

  { path: 'login', component: LoginComponent },
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'home', component: HomeComponent, canActivate: [AuthService]},
  { path: 'basket', component: BasketPageComponent, canActivate: [AuthService]},
  { path: 'post-checkout', component: PostCheckoutComponent, canActivate: [AuthService]},
  { path: 'profile', component: ProfilePageComponent, canActivate: [AuthService]},
  { path: 'profileother', component: ProfileOtherComponent, canActivate: [AuthService]},
  { path: 'leaderboard', component: LeaderboardPageComponent, canActivate: [AuthService]},

  //{ path: 'login', loadChildren: () => import('./login/login.component').then(m => m.LoginComponent )}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],

  exports: [RouterModule]
})
export class AppRoutingModule { }
