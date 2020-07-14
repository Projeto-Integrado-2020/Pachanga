import { TestBed } from '@angular/core/testing';

import { CadastrarFestaService } from './cadastrar-festa.service';

import { HttpClientModule } from '@angular/common/http';

import { CustomMaterialModule } from '../../views/material/material.module';

describe('CadastrarFestaService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [
      HttpClientModule,
      CustomMaterialModule
    ]
  }));

  it('should be created', () => {
    const service: CadastrarFestaService = TestBed.get(CadastrarFestaService);
    expect(service).toBeTruthy();
  });
});
