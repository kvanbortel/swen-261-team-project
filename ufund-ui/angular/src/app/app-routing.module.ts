import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { CupboardNeedComponent } from './cupboard-need/cupboard-need.component';
import { CupboardSearchComponent } from './cupboard-search/cupboard-search.component';
import { HomeComponent } from './home/home.component';

const routes: Routes = [
  
  { path: 'login', component: LoginComponent },
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'home', component: HomeComponent},
  //{ path: 'login', loadChildren: () => import('./login/login.component').then(m => m.LoginComponent )}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  
  exports: [RouterModule]
})
export class AppRoutingModule { }
