import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material';
import { RouterModule } from '@angular/router';

import { RelatorioVendaService } from './relatorio-venda.service';

describe('RelatorioVendaService', () => {
  let service: RelatorioVendaService;

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
    service = TestBed.get(RelatorioVendaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get Info at ingressosFesta', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    expect(service.ingressosFesta('teste')).toBeTruthy();
  });

  it('should get Info at ingressosFestaCompradosPagos', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    expect(service.ingressosFestaCompradosPagos('teste')).toBeTruthy();
  });

  it('should get Info at lucroFesta', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    expect(service.lucroFesta('teste')).toBeTruthy();
  });
});
