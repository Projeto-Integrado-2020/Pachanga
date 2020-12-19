import { TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { CustomMaterialModule } from 'src/app/views/material/material.module';
import { MatDialog, MatDialogRef } from '@angular/material';
import { SymplaApiService } from './sympla-api.service';

describe('SymplaApiService', () => {
  let dialogSpy: MatDialog;
  let service: SymplaApiService;

  beforeEach(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);

    TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        CustomMaterialModule
      ],
      providers: [
        { provide: MatDialog, useValue: dialogSpy },
        { provide: MatDialogRef, useValue: {} }
      ]
    });
    const token = {
      timeToken: '2020-09-21T01:14:04.028+0000',
      token: 'teste'
    };
    localStorage.setItem('token', JSON.stringify(token));
    service = TestBed.get(SymplaApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should open a dialog through a method', () => {
    service.openErrorDialog('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should get evento at testSymplaConnection', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    expect(service.testSymplaConnection('teste', 'teste')).toBeTruthy();
  });

  it('should get participants at listarParticipantesEvento', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    expect(service.listarParticipantesEvento('teste', 'teste')).toBeTruthy();
  });

  it('should do checkIn at checkInIngresso', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    expect(service.checkInIngresso('teste', 'teste', 'teste')).toBeTruthy();
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
