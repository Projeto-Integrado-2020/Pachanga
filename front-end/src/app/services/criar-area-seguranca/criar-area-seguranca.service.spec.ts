import { TestBed } from '@angular/core/testing';

import { CriarAreaSegurancaService } from './criar-area-seguranca.service';

describe('CriarAreaSegurancaService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CriarAreaSegurancaService = TestBed.get(CriarAreaSegurancaService);
    expect(service).toBeTruthy();
  });
});
