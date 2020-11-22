import { TestBed } from '@angular/core/testing';

import { DadosCompraIngressoService } from './dados-compra-ingresso.service';

describe('DadosCompraIngressoService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: DadosCompraIngressoService = TestBed.get(DadosCompraIngressoService);
    expect(service).toBeTruthy();
  });
});
