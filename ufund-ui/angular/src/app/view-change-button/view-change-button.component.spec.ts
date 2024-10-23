import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewChangeButtonComponent } from './view-change-button.component';

describe('ViewChangeButtonComponent', () => {
  let component: ViewChangeButtonComponent;
  let fixture: ComponentFixture<ViewChangeButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ViewChangeButtonComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ViewChangeButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
