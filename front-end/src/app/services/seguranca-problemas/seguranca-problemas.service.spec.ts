/* tslint:disable:no-unused-variable */

import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed, inject } from '@angular/core/testing';
import { RouterModule } from '@angular/router';
import { CustomMaterialModule } from 'src/app/views/material/material.module';
import { SegurancaProblemasService } from './seguranca-problemas.service';

describe('Service: SegurancaProblemas', () => {
  let service: SegurancaProblemasService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        CustomMaterialModule,
        RouterModule.forRoot([])
      ],
      providers: [
        SegurancaProblemasService,
      ]
    });
    const token = {
      timeToken: '2020-09-21T01:14:04.028+0000',
      token: 'teste'
    };
    localStorage.setItem('token', JSON.stringify(token));
    service = TestBed.get(SegurancaProblemasService);
  });

  it('should ...', inject([SegurancaProblemasService], (serviceProblema: SegurancaProblemasService) => {
    expect(serviceProblema).toBeTruthy();
  }));

  it('should get Info at listarProblemas', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    expect(service.listarProblemas()).toBeTruthy();
  });

  it('should post Info at adicionarProblema', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    const imagem = {
      _files: ['teste']
    };
    const problemaTO = {
      codAreaProblema: 7,
      codAreaSeguranca: 1,
      codFesta: 2,
      codProblema: 4,
      codUsuarioResolv: 0,
      statusProblema: 'A',
      horarioInicio: '2020-12-19T13:39:53.474387',
      horarioFim: null,
      codUsuarioEmissor: 1,
      descProblemaEmissor: 'tryrteytryeyetryter',
      observacaoSolucao: null,
      descProblema: 'FURTOOBJ',
      imagemProblema: '75462'
    };
    expect(service.adicionarProblema(problemaTO, imagem)).toBeTruthy();
  });

  it('should get Info at getAllProblemasArea', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    const codArea = 1;
    const codFesta = 1;
    expect(service.getAllProblemasArea(codArea, codFesta)).toBeTruthy();
  });

  it('should get Info at getAllProblemasFesta', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    const codFesta = 1;
    expect(service.getAllProblemasFesta(codFesta)).toBeTruthy();
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
});
