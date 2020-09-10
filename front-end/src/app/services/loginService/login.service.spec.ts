import { TestBed } from '@angular/core/testing';

import { LoginService } from './login.service';

import { HttpClientModule } from '@angular/common/http';

import { CustomMaterialModule } from '../../views/material/material.module';
import { MatDialog } from '@angular/material';
import { Observable } from 'rxjs';

describe('LoginService', () => {
  let dialogSpy: MatDialog;
  let service: LoginService;

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

    service = TestBed.get(LoginService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should set farol', () => {
    service.finalizarSessao();
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
    service.setFarol(false);
    expect(service.getFarol()).toBeFalsy();
  });

  it('should get farol', () => {
    service.finalizarSessao();
    expect(service.getFarol()).toBeFalsy();
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
  });

  it('should open a dialog through a method', () => {
    service.finalizarSessao();
    service.openErrorDialog('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should post Info at logar', () => {
    expect(service.logar('teste')).toBeTruthy();

    expect(service.getFarol()).toBeTruthy();

    expect(service.logar('teste')).toEqual(new Observable<never>());
  });

  it('should post Info at cadastrar', () => {
    expect(service.cadastrar('teste')).toBeTruthy();

    expect(service.getFarol()).toBeTruthy();

    expect(service.cadastrar('teste')).toEqual(new Observable<never>());
  });

  it('should getUsuarioAutenticado', () => {
    service.usuarioAutenticado = true;
    expect(service.getUsuarioAutenticado()).toBeTruthy();

    service.usuarioAutenticado = false;
    expect(service.getUsuarioAutenticado()).toBeFalsy();
  });

  it('should getusuarioInfo', () => {
    const usuarioInfo = {nomeUser: 'Teste'};
    service.usuarioInfo = usuarioInfo;
    expect(service.getusuarioInfo()).toEqual(usuarioInfo);
  });

  it('should setUsuarioAutenticado', () => {
    expect(service.getUsuarioAutenticado()).toBeFalsy();
    service.setUsuarioAutenticado(true);
    expect(service.getUsuarioAutenticado()).toBeTruthy();
  });

  it('should setusuarioInfo', () => {
    const usuarioInfo = {nomeUser: 'Teste'};
    service.setusuarioInfo(usuarioInfo);
    expect(service.getusuarioInfo()).toEqual(usuarioInfo);
  });

});
