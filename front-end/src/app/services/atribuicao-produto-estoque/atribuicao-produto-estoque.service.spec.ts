import { TestBed } from '@angular/core/testing';

import { AtribuicaoProdutoEstoqueService } from './atribuicao-produto-estoque.service';
import { HttpClientModule } from '@angular/common/http';
import { CustomMaterialModule } from 'src/app/views/material/material.module';
import { MatDialog, MatDialogRef } from '@angular/material';

describe('AtribuicaoProdutoEstoqueService', () => {
  let dialogSpy: MatDialog;
  let service: AtribuicaoProdutoEstoqueService;

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

    service = TestBed.get(AtribuicaoProdutoEstoqueService);
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

  it('should post Info at addProdutoEstoque', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    expect(service.addProdutoEstoque('teste', 'teste')).toBeTruthy();
  });
});
