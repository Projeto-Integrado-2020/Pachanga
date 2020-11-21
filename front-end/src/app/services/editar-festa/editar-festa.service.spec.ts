import { TestBed } from '@angular/core/testing';

import { EditarFestaService } from './editar-festa.service';

import { HttpClientModule } from '@angular/common/http';

import { CustomMaterialModule } from '../../views/material/material.module';

import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';

import { MatDialog } from '@angular/material';

describe('EditarFestaService', () => {
  let dialogSpy: MatDialog;
  let service: EditarFestaService;
  const router = {
    navigate: jasmine.createSpy('navigate')
  };

  beforeEach(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);

    TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        CustomMaterialModule,
        RouterTestingModule
      ],
      providers: [
        { provide: MatDialog, useValue: dialogSpy },
        { provide: Router, useValue: router }
      ]
    });
    const token = {
      timeToken: '2020-09-21T01:14:04.028+0000',
      token: 'teste'
    };
    localStorage.setItem('token', JSON.stringify(token));
    service = TestBed.get(EditarFestaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should open a dialog through a method', () => {
    service.openErrorDialog('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
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

  it('should put Info at atualizarFesta', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};

    expect(service.atualizarFesta('teste', null)).toBeTruthy();

    expect(service.getFarol()).toBeTruthy();

    expect(service.atualizarFesta('teste', null)).toBeFalsy();
  });
});
