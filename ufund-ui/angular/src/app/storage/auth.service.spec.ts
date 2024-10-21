import { TestBed } from '@angular/core/testing';

import { AuthService } from './auth.service';

describe('StorageComponent', () => {
  let service: AuthService;


  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AuthService]
    })
    .compileComponents();
    
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
