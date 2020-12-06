import { TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { CustomMaterialModule } from '../../views/material/material.module';
import { MatDialog, MatDialogRef } from '@angular/material';
import { RouterModule } from '@angular/router';
import { GetCuponsService } from './get-cupons.service';

describe('GetCuponsService', () => {
  let dialogSpy: MatDialog;
  let service: GetCuponsService;

  beforeEach(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);
    TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        CustomMaterialModule,
        RouterModule.forRoot([]),
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
    service = TestBed.get(GetCuponsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
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

  it('should open a dialog through a method', () => {
    service.openErrorDialog('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should get Info at getCupons', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    expect(service.getCupons('teste')).toBeTruthy();

    expect(service.getFarol()).toBeTruthy();

    expect(service.getCupons('teste')).toBeFalsy();
  });

  it('should get Info at getCupomUnico', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    expect(service.getCupomUnico('teste', 'teste')).toBeTruthy();

    expect(service.getFarol()).toBeTruthy();

    expect(service.getCupomUnico('teste', 'teste')).toBeFalsy();
  });
});
