import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BasketNeedComponent } from './basket-need.component';

describe('BasketNeedComponent', () => {
  let component: BasketNeedComponent;
  let fixture: ComponentFixture<BasketNeedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BasketNeedComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BasketNeedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
