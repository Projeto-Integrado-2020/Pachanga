import { TestBed } from '@angular/core/testing';

import { PainelControleService } from './painel-controle.service';

describe('PainelControleService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PainelControleService = TestBed.get(PainelControleService);
    expect(service).toBeTruthy();
  });
});
