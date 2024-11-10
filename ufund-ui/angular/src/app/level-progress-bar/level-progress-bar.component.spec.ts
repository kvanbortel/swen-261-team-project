import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LevelProgressBarComponent } from './level-progress-bar.component';

describe('LevelProgressBarComponent', () => {
  let component: LevelProgressBarComponent;
  let fixture: ComponentFixture<LevelProgressBarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LevelProgressBarComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LevelProgressBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
