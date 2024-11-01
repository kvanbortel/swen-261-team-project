import { TestBed } from '@angular/core/testing';

import { ProfileSectionService } from './profile-section.service';

describe('ProfileSectionService', () => {
  let service: ProfileSectionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProfileSectionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
