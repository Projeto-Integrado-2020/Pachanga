import { TestBed } from '@angular/core/testing';

import { EditAccountService } from './edit-account.service';

describe('EditAccountService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: EditAccountService = TestBed.get(EditAccountService);
    expect(service).toBeTruthy();
  });
});
