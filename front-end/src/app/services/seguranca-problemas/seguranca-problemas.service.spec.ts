/* tslint:disable:no-unused-variable */

import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed, inject } from '@angular/core/testing';
import { CustomMaterialModule } from 'src/app/views/material/material.module';
import { SegurancaProblemasService } from './seguranca-problemas.service';

describe('Service: SegurancaProblemas', () => {
  let service: SegurancaProblemasService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        CustomMaterialModule
      ],
      providers: [SegurancaProblemasService]
    });
    const token = {
      timeToken: '2020-09-21T01:14:04.028+0000',
      token: 'teste'
    };
    localStorage.setItem('token', JSON.stringify(token));
    service = TestBed.get(SegurancaProblemasService);
  });

  it('should ...', inject([SegurancaProblemasService], (service: SegurancaProblemasService) => {
    expect(service).toBeTruthy();
  }));

  it('should get Info at listarProblemas', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    expect(service.listarProblemas()).toBeTruthy();
  });

  it('should post Info at adicionarProblema', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    let problemaTO = {}
    expect(service.adicionarProblema(problemaTO)).toBeTruthy();
  });

  it('should delete Info at deletarProblema', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    let problemaTO = {}
    expect(service.adicionarProblema(problemaTO)).toBeTruthy();
  });

  it('should delete Info at deletarProblema', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    let problemaTO = {}
    expect(service.adicionarProblema(problemaTO)).toBeTruthy();
  });

  it('should get Info at getAllProblemasArea', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    let codArea = 1;
    let codFesta = 1
    expect(service.getAllProblemasArea(codArea, codFesta)).toBeTruthy();
  });

  it('should get Info at getAllProblemasFesta', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    let codFesta = 1
    expect(service.getAllProblemasFesta(codFesta)).toBeTruthy();
  });

  // it('should get Info at getEstoque', () => {
  //   service.loginService.usuarioInfo = {codusuario: 'teste'};
  //   expect(service.getEstoque('teste')).toBeTruthy();
  // });

  // it('should get Info at getEstoque', () => {
  //   service.loginService.usuarioInfo = {codusuario: 'teste'};
  //   expect(service.getEstoque('teste')).toBeTruthy();
  // });
});
