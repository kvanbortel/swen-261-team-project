import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SingleNeedComponent } from './single-need.component';

describe('SingleNeedComponent', () => {
  let component: SingleNeedComponent;
  let fixture: ComponentFixture<SingleNeedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SingleNeedComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SingleNeedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
