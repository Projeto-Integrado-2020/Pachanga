import { TestBed } from '@angular/core/testing';
import { AtribuicaoGrupoService } from './atribuicao-grupo.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CustomMaterialModule } from '../../views/material/material.module';
import { MatDialog, MatDialogRef } from '@angular/material';


describe('AtribuicaoGrupoService', () => {
  let dialogSpy: MatDialog;
  let service: AtribuicaoGrupoService;

  beforeEach(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);

    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
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
    service = TestBed.get(AtribuicaoGrupoService);
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

  it('should put Info at atribuirMembros', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    expect(service.atribuirMembros('teste', 'teste')).toBeTruthy();

    expect(service.getFarol()).toBeTruthy();

    expect(service.atribuirMembros('teste', 'teste')).toBeFalsy();
  });
});
