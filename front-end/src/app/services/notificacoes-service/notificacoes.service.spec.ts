/* tslint:disable:no-unused-variable */

import { TestBed, inject } from '@angular/core/testing';
import { NotificacoesService } from './notificacoes.service';
import { HttpClientModule } from '@angular/common/http';
import { MatDialog } from '@angular/material';

describe('Service: Notificacoes', () => {
  let service: NotificacoesService;

  beforeEach(() => {
    const dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);
    TestBed.configureTestingModule({

      imports: [
        HttpClientModule,
      ],
      providers: [
        NotificacoesService,
        {provide: MatDialog, useValue: dialogSpy}
      ]
    });

    service = TestBed.get(NotificacoesService);
    const token = {
      timeToken: '2020-09-21T01:14:04.028+0000',
      token: 'teste'
    };
    localStorage.setItem('token', JSON.stringify(token));
  });

  it('should ...', () => {
    expect(service).toBeTruthy();
  });

  it('should get Info at getNotificacoes', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    expect(service.getNotificacoes()).toBeTruthy();
  });

  it('should put Info at atualizarNotificacoes', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    expect(service.atualizarNotificacoes([1])).toBeTruthy();
  });

  it('should put Info at destacarNotificacao', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    expect(service.destacarNotificacao(1)).toBeTruthy();
  });

  it('should delete Info at deletarNotificacao', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    expect(service.deletarNotificacao('teste')).toBeTruthy();
  });
});
