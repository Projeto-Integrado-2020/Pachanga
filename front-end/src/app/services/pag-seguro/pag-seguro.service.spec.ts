import { TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { CustomMaterialModule } from 'src/app/views/material/material.module';
import { MatDialog, MatDialogRef } from '@angular/material';
import { PagSeguroService } from './pag-seguro.service';

describe('PagSeguroService', () => {
  let dialogSpy: MatDialog;
  let service: PagSeguroService;

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
    service = TestBed.get(PagSeguroService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should open a dialog through a method', () => {
    service.openErrorDialog('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should gerarBoleto', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    expect(service.gerarBoleto({})).toBeTruthy();
  });

  it('should resgatarBoleto', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    expect(service.resgatarBoleto('teste')).toBeTruthy();
  });
});
