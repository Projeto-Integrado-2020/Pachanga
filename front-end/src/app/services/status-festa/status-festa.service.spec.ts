import { TestBed } from '@angular/core/testing';

import { StatusFestaService } from './status-festa.service';

describe('StatusFestaService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: StatusFestaService = TestBed.get(StatusFestaService);
    expect(service).toBeTruthy();
  });
});
