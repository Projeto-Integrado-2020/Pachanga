import { TestBed } from '@angular/core/testing';

import { EditarFestaService } from './editar-festa.service';

import { HttpClientModule } from '@angular/common/http';

import { CustomMaterialModule } from '../../views/material/material.module';

describe('EditarFestaService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [
      HttpClientModule,
      CustomMaterialModule
    ]
  }));

  it('should be created', () => {
    const service: EditarFestaService = TestBed.get(EditarFestaService);
    expect(service).toBeTruthy();
  });
});
