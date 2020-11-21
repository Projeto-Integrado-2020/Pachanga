import { TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { CustomMaterialModule } from '../../views/material/material.module';
import { MatDialog, MatDialogRef } from '@angular/material';

import { GerarIngressoService } from './gerar-ingresso.service';

describe('GerarIngressoService', () => {
  let dialogSpy: MatDialog;
  let service: GerarIngressoService;

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
    const token = {
      timeToken: '2020-09-21T01:14:04.028+0000',
      token: 'teste'
    };
    localStorage.setItem('token', JSON.stringify(token));
    service = TestBed.get(GerarIngressoService);
  });

  it('should be created', () => {
    service = TestBed.get(GerarIngressoService);
    expect(service).toBeTruthy();
  });

  it('should open a dialog through a method', () => {
    service.openErrorDialog('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should post Info at adicionarIngressos', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};

    expect(service.adicionarIngressos([])).toBeTruthy();
  });
});
