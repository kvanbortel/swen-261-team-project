import { Component } from '@angular/core';
import { AuthService } from '../storage/auth.service';

@Component({
  selector: 'app-leaderboard',
  templateUrl: './leaderboard.component.html',
  styleUrl: './leaderboard.component.css'
})
export class LeaderboardComponent {

  constructor(public authService: AuthService){};

  

}
