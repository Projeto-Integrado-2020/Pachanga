import { TestBed } from '@angular/core/testing';

import { DeletarEstoqueService } from './deletar-estoque.service';
import { HttpClientModule } from '@angular/common/http';
import { CustomMaterialModule } from '../../views/material/material.module';
import { MatDialog, MatDialogRef } from '@angular/material';

describe('DeletarEstoqueService', () => {
  let dialogSpy: MatDialog;
  let service: DeletarEstoqueService;

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

    service = TestBed.get(DeletarEstoqueService);
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

  it('should delete Info at deleteEstoque', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};

    expect(service.deleteEstoque('teste', 'teste')).toBeTruthy();

    expect(service.getFarol()).toBeTruthy();

    expect(service.deleteEstoque('teste', 'teste')).toBeFalsy();
  });
});
