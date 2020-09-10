import { TestBed } from '@angular/core/testing';

import { CadastrarFestaService } from './cadastrar-festa.service';
import { HttpClientModule } from '@angular/common/http';

import { CustomMaterialModule } from '../../views/material/material.module';
import { MatDialog } from '@angular/material';

describe('CadastrarFestaService', () => {
  let dialogSpy: MatDialog;
  let service: CadastrarFestaService;

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

    service = TestBed.get(CadastrarFestaService);
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

  it('should post Info at cadastrarFesta', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    expect(service.cadastrarFesta('teste')).toBeTruthy();
  });
});
