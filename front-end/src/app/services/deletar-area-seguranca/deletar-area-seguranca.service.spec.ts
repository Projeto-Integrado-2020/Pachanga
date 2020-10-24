import { TestBed } from '@angular/core/testing';

import { DeletarAreaSegurancaService } from './deletar-area-seguranca.service';

describe('DeletarAreaSegurancaService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: DeletarAreaSegurancaService = TestBed.get(DeletarAreaSegurancaService);
    expect(service).toBeTruthy();
  });
});
