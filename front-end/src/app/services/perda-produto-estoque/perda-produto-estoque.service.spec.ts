import { TestBed } from '@angular/core/testing';

import { PerdaProdutoEstoqueService } from './perda-produto-estoque.service';
import { MatDialog, MatDialogRef } from '@angular/material';
import { HttpClientModule } from '@angular/common/http';
import { CustomMaterialModule } from 'src/app/views/material/material.module';

let dialogSpy: MatDialog;

describe('PerdaProdutoEstoqueService', () => {
  dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);
  let service: PerdaProdutoEstoqueService;

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

    service = TestBed.get(PerdaProdutoEstoqueService);
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

  it('should put Info at perdaProdutoEstoque', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    expect(service.perdaProdutoEstoque('teste', 'teste', 'teste', 'teste')).toBeTruthy();
  });
});
