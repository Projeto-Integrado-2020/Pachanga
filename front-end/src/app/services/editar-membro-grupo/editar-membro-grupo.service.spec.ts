import { TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { CustomMaterialModule } from '../../views/material/material.module';
import { MatDialog, MatDialogRef } from '@angular/material';
import { EditarMembroGrupoService } from './editar-membro-grupo.service';

describe('EditarMembroGrupoService', () => {
  let dialogSpy: MatDialog;
  let service: EditarMembroGrupoService;

  beforeEach(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);
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

    service = TestBed.get(EditarMembroGrupoService);
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

  it('should put Info at editarMembroColaborador', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};

    expect(service.editarMembroColaborador('teste', 'teste', 'teste')).toBeTruthy();

    expect(service.getFarol()).toBeTruthy();

    expect(service.editarMembroColaborador('teste', 'teste', 'teste')).toBeFalsy();
  });
});
