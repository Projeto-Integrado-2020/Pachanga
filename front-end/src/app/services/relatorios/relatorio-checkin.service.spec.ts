import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { TestBed } from '@angular/core/testing';
import { MatDialog, MatDialogRef } from '@angular/material';
import { RouterModule } from '@angular/router';


import { RelatorioCheckinService } from './relatorio-checkin.service';

describe('RelatorioCheckinService', () => {
  let service: RelatorioCheckinService;

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
    service = TestBed.get(RelatorioCheckinService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get Info at faixaEtaria', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    expect(service.faixaEtaria('teste')).toBeTruthy();
  });

  it('should get Info at checkedUnchecked', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    expect(service.checkedUnchecked('teste')).toBeTruthy();
  });

  it('should get Info at genero', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    expect(service.genero('teste')).toBeTruthy();
  });

  it('should get Info at qtdEntradasHora', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    expect(service.qtdEntradasHora('teste')).toBeTruthy();
  });
});
