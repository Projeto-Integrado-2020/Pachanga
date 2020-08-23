import { TestBed } from '@angular/core/testing';

import { AtribuicaoProdutoEstoqueService } from './atribuicao-produto-estoque.service';
import { HttpClientModule } from '@angular/common/http';
import { CustomMaterialModule } from 'src/app/views/material/material.module';
import { MatDialog, MatDialogRef } from '@angular/material';

describe('AtribuicaoProdutoEstoqueService', () => {
  let dialogSpy: MatDialog;

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
    const service: AtribuicaoProdutoEstoqueService = TestBed.get(AtribuicaoProdutoEstoqueService);
    expect(service).toBeTruthy();
  });

  it('should set farol', () => {
    const service: AtribuicaoProdutoEstoqueService = TestBed.get(AtribuicaoProdutoEstoqueService);
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
    service.setFarol(false);
    expect(service.getFarol()).toBeFalsy();
  });

  it('should get farol', () => {
    const service: AtribuicaoProdutoEstoqueService = TestBed.get(AtribuicaoProdutoEstoqueService);
    expect(service.getFarol()).toBeFalsy();
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
  });

  it('should open a dialog through a method', () => {
    const service: AtribuicaoProdutoEstoqueService = TestBed.get(AtribuicaoProdutoEstoqueService);
    service.openErrorDialog('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });
});
