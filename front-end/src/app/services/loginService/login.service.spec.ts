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
});
