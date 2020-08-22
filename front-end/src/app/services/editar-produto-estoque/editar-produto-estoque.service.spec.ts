import { TestBed } from '@angular/core/testing';

import { EditarProdutoEstoqueService } from './editar-produto-estoque.service';
import { HttpClientModule } from '@angular/common/http';
import { CustomMaterialModule } from 'src/app/views/material/material.module';
import { MatDialog, MatDialogRef } from '@angular/material';

describe('EditarProdutoEstoqueService', () => {
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
    const service: EditarProdutoEstoqueService = TestBed.get(EditarProdutoEstoqueService);
    expect(service).toBeTruthy();
  });

  it('should set farol', () => {
    const service: EditarProdutoEstoqueService = TestBed.get(EditarProdutoEstoqueService);
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
    service.setFarol(false);
    expect(service.getFarol()).toBeFalsy();
  });

  it('should get farol', () => {
    const service: EditarProdutoEstoqueService = TestBed.get(EditarProdutoEstoqueService);
    expect(service.getFarol()).toBeFalsy();
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
  });

  it('should open a dialog through a method', () => {
    const service: EditarProdutoEstoqueService = TestBed.get(EditarProdutoEstoqueService);
    service.openErrorDialog('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });
});
