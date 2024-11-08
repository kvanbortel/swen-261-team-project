import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeaderboardGodComponent } from './leaderboard-god.component';

describe('LeaderboardGodComponent', () => {
  let component: LeaderboardGodComponent;
  let fixture: ComponentFixture<LeaderboardGodComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LeaderboardGodComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LeaderboardGodComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
