import { TestBed } from '@angular/core/testing';

import { LoginService } from './login.service';

import { HttpClientModule } from '@angular/common/http';

import { CustomMaterialModule } from '../../views/material/material.module';
import { MatDialog } from '@angular/material';

describe('LoginService', () => {
  let dialogSpy: MatDialog;

  beforeEach(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);

    TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        CustomMaterialModule
      ],
      providers: [
        { provide: MatDialog, useValue: dialogSpy },
      ]
    });
  });

  it('should be created', () => {
    const service: LoginService = TestBed.get(LoginService);
    expect(service).toBeTruthy();
  });

  it('should set farol', () => {
    const service: LoginService = TestBed.get(LoginService);
    service.finalizarSessao();
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
    service.setFarol(false);
    expect(service.getFarol()).toBeFalsy();
  });

  it('should get farol', () => {
    const service: LoginService = TestBed.get(LoginService);
    service.finalizarSessao();
    expect(service.getFarol()).toBeFalsy();
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
  });

  it('should open a dialog through a method', () => {
    const service: LoginService = TestBed.get(LoginService);
    service.finalizarSessao();
    service.openErrorDialog('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should setUsuarioAutenticado', () => {
    const service: LoginService = TestBed.get(LoginService);
    service.finalizarSessao();
    service.setUsuarioAutenticado(true);
    expect(service.getUsuarioAutenticado()).toBeTruthy();
    service.setUsuarioAutenticado(false);
    expect(service.getUsuarioAutenticado()).toBeFalsy();
  });

  it('should getusuarioInfo', () => {
    const service: LoginService = TestBed.get(LoginService);
    service.finalizarSessao();
    expect(service.getUsuarioAutenticado()).toBeFalsy();

    service.setUsuarioAutenticado(true);
    expect(service.getUsuarioAutenticado()).toBeTruthy();
  });

  it('should setusuarioInfo', () => {
    const service: LoginService = TestBed.get(LoginService);
    service.finalizarSessao();
    const json = {nome: 'teste', dtNasc: '2020-02-01T12:00:00'};
    const jsonMod = {nome: 'teste', dtNasc: '2020-02-01'};
    service.setusuarioInfo(json);
    expect(service.getusuarioInfo()).toEqual(jsonMod);
  });

  it('should getusuarioInfo', () => {
    const service: LoginService = TestBed.get(LoginService);
    service.finalizarSessao();
    expect(service.getusuarioInfo()).toBeFalsy();

    const json = {nome: 'teste', dtNasc: '2020-02-01T12:00:00'};
    const jsonMod = {nome: 'teste', dtNasc: '2020-02-01'};
    service.setusuarioInfo(json);
    expect(service.getusuarioInfo()).toEqual(jsonMod);
  });
});
