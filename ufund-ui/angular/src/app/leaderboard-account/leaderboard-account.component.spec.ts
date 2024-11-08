import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeaderboardAccountComponent } from './leaderboard-account.component';

describe('LeaderboardAccountComponent', () => {
  let component: LeaderboardAccountComponent;
  let fixture: ComponentFixture<LeaderboardAccountComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LeaderboardAccountComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LeaderboardAccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
