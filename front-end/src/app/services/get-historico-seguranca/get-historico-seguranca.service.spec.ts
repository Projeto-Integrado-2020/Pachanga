import { TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { CustomMaterialModule } from '../../views/material/material.module';
import { MatDialog, MatDialogRef } from '@angular/material';
import { RouterModule } from '@angular/router';
import { GetHistoricoSegurancaService } from './get-historico-seguranca.service';

describe('GetHistoricoSegurancaService', () => {
  let dialogSpy: MatDialog;
  let service: GetHistoricoSegurancaService;

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
    service = TestBed.get(GetHistoricoSegurancaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should open a dialog through a method', () => {
    service.openErrorDialog('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should get Info at getIntegracoes', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    expect(service.getHistorico('teste')).toBeTruthy();
  });
});
