import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateNeedDialogComponent } from './create-need-dialog.component';

describe('CreateNeedDialogComponent', () => {
  let component: CreateNeedDialogComponent;
  let fixture: ComponentFixture<CreateNeedDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CreateNeedDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CreateNeedDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
