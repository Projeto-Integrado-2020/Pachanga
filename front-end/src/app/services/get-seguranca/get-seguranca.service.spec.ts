import { TestBed } from '@angular/core/testing';

import { GetSegurancaService } from './get-seguranca.service';

describe('GetSegurancaService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: GetSegurancaService = TestBed.get(GetSegurancaService);
    expect(service).toBeTruthy();
  });
});
