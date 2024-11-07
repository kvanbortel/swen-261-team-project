import { Component } from '@angular/core';
import { AuthService } from '../storage/auth.service';
import { AdminInfo } from '../AdminInfo';
import { BehaviorSubject } from 'rxjs';

@Component({
  selector: 'app-admin-dash',
  templateUrl: './admin-dash.component.html',
  styleUrl: './admin-dash.component.css'
})
export class AdminDashComponent {
  constructor(public authService: AuthService){}

  adminInfo$ = new BehaviorSubject<AdminInfo | null>(null);

  ngOnInit(): void {
    this.authService.getAdminInfo().subscribe({
      next: (response) => {
        this.adminInfo$.next(response);
        console.log(response);
      },
    });
  }
}
