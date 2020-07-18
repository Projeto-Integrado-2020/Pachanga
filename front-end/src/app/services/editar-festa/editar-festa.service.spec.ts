import { TestBed } from '@angular/core/testing';

import { EditarFestaService } from './editar-festa.service';

import { HttpClientModule } from '@angular/common/http';

import { CustomMaterialModule } from '../../views/material/material.module';

import { RouterModule } from '@angular/router';

describe('EditarFestaService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [
      HttpClientModule,
      CustomMaterialModule,
      RouterModule.forRoot([])
    ]
  }));

  it('should be created', () => {
    const service: EditarFestaService = TestBed.get(EditarFestaService);
    expect(service).toBeTruthy();
  });
});
