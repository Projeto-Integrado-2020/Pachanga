import { TestBed } from '@angular/core/testing';

import { DeletarProdutoEstoqueService } from './deletar-produto-estoque.service';
import { MatDialog, MatDialogRef } from '@angular/material';
import { HttpClientModule } from '@angular/common/http';
import { CustomMaterialModule } from 'src/app/views/material/material.module';

describe('DeletarProdutoEstoqueService', () => {
  let dialogSpy: MatDialog;
  let service: DeletarProdutoEstoqueService;

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

    service = TestBed.get(DeletarProdutoEstoqueService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should set farol', () => {
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
    service.setFarol(false);
    expect(service.getFarol()).toBeFalsy();
  });

  it('should get farol', () => {
    expect(service.getFarol()).toBeFalsy();
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
  });

  it('should open a dialog through a method', () => {
    service.openErrorDialog('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should delete Info at deletarProdutoEstoque', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};

    expect(service.deletarProdutoEstoque('teste', 'teste')).toBeTruthy();

  });
});
