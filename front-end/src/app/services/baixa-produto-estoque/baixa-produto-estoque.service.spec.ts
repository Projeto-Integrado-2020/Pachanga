import { TestBed } from '@angular/core/testing';
import { BaixaProdutoEstoqueService } from './baixa-produto-estoque.service';
import { MatDialog, MatDialogRef } from '@angular/material';
import { HttpClientModule } from '@angular/common/http';
import { CustomMaterialModule } from 'src/app/views/material/material.module';

describe('BaixaProdutoEstoqueService', () => {
  let dialogSpy: MatDialog;
  let service: BaixaProdutoEstoqueService;

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
    const token = {
      timeToken: '2020-09-21T01:14:04.028+0000',
      token: 'teste'
    };
    localStorage.setItem('token', JSON.stringify(token));
    service = TestBed.get(BaixaProdutoEstoqueService);
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

  it('should put Info at baixaProdutoEstoque', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    expect(service.baixaProdutoEstoque('teste', 'teste', 'teste')).toBeTruthy();
  });
});
