import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { TestBed } from '@angular/core/testing';
import { MatDialog, MatDialogRef } from '@angular/material';
import { RouterModule } from '@angular/router';

import { RelatorioEstoqueService } from './relatorio-estoque.service';

describe('RelatorioEstoqueService', () => {
  let service: RelatorioEstoqueService;

  beforeEach(() => {

    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        RouterModule.forRoot([])
      ],
      providers: [
        { provide: MatDialog, useValue: MatDialog },
        { provide: MatDialogRef, useValue: {} }
      ],
      schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
    });
    const token = {
      timeToken: '2020-09-21T01:14:04.028+0000',
      token: 'teste'
    };
    localStorage.setItem('token', JSON.stringify(token));
    service = TestBed.get(RelatorioEstoqueService);
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

  it('should get Info at consumoItemEstoque', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    expect(service.consumoItemEstoque('teste')).toBeTruthy();
  });

  it('should get Info at perdaItemEstoque', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    expect(service.perdaItemEstoque('teste')).toBeTruthy();
  });

  it('should get Info at quantidadeItemEstoque', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    expect(service.quantidadeItemEstoque('teste')).toBeTruthy();
  });

  it('should get Info at consumoProduto', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    expect(service.consumoProduto('teste')).toBeTruthy();
  });
});
