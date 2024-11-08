import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeaderboardAccountProfileComponent } from './leaderboard-account-profile.component';

describe('LeaderboardAccountProfileComponent', () => {
  let component: LeaderboardAccountProfileComponent;
  let fixture: ComponentFixture<LeaderboardAccountProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LeaderboardAccountProfileComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LeaderboardAccountProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
