import { TestBed } from '@angular/core/testing';

import { EditarAreaSegurancaService } from './editar-area-seguranca.service';

describe('EditarAreaSegurancaService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: EditarAreaSegurancaService = TestBed.get(EditarAreaSegurancaService);
    expect(service).toBeTruthy();
  });
});
