import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CustomMaterialModule } from '../../views/material/material.module';
import { GetGruposService } from './get-grupos.service';
import { MatDialog } from '@angular/material';

describe('GetGruposService', () => {
  let dialogSpy: MatDialog;
  let service: GetGruposService;

  beforeEach(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);

    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        CustomMaterialModule
      ],
      providers: [
        { provide: MatDialog, useValue: dialogSpy }
      ]
    });

    service = TestBed.get(GetGruposService);
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

  it('should get Info at getGrupos', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    expect(service.getGrupos('teste')).toBeTruthy();

    expect(service.getFarol()).toBeTruthy();

    expect(service.getGrupos('teste')).toBeFalsy();
  });

  it('should get Info at getGrupoUnico', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    expect(service.getGrupoUnico('teste')).toBeTruthy();

    expect(service.getFarol()).toBeTruthy();

    expect(service.getGrupoUnico('teste')).toBeFalsy();
  });
});
