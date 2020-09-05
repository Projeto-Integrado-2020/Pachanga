import { TestBed } from '@angular/core/testing';

import { PerdaProdutoEstoqueService } from './perda-produto-estoque.service';
import { MatDialog, MatDialogRef } from '@angular/material';
import { HttpClientModule } from '@angular/common/http';
import { CustomMaterialModule } from 'src/app/views/material/material.module';

let dialogSpy: MatDialog;

describe('PerdaProdutoEstoqueService', () => {
  dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);

  beforeEach(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);

    TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        CustomMaterialModule
      ],
      providers: [
        { provide: MatDialog, useValue: dialogSpy },
        { provide: MatDialogRef, useValue: {} }
      ]
    });
  });

  it('should be created', () => {
    const service: PerdaProdutoEstoqueService = TestBed.get(PerdaProdutoEstoqueService);
    expect(service).toBeTruthy();
  });
});
