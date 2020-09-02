import { TestBed } from '@angular/core/testing';

import { PerdaProdutoEstoqueService } from './perda-produto-estoque.service';

describe('PerdaProdutoEstoqueService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PerdaProdutoEstoqueService = TestBed.get(PerdaProdutoEstoqueService);
    expect(service).toBeTruthy();
  });
});
