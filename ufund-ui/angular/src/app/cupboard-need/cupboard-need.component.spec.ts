import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CupboardNeedComponent } from './cupboard-need.component';

describe('CupboardNeedComponent', () => {
  let component: CupboardNeedComponent;
  let fixture: ComponentFixture<CupboardNeedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CupboardNeedComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CupboardNeedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
