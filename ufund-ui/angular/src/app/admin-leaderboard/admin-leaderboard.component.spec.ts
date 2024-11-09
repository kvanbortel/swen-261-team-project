import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminLeaderboardComponent } from './admin-leaderboard.component';

describe('AdminLeaderboardComponent', () => {
  let component: AdminLeaderboardComponent;
  let fixture: ComponentFixture<AdminLeaderboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AdminLeaderboardComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AdminLeaderboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
