import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { of } from 'rxjs';
import { LoginService } from 'src/app/services/loginService/login.service';
import { SegurancaProblemasService } from 'src/app/services/seguranca-problemas/seguranca-problemas.service';
import { CustomMaterialModule } from '../material/material.module';

import { AlertaSegurancaComponent } from './alerta-seguranca.component';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('AlertaSegurancaComponent', () => {
  let component: AlertaSegurancaComponent;
  let fixture: ComponentFixture<AlertaSegurancaComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);

    TestBed.configureTestingModule({
      declarations: [ AlertaSegurancaComponent ],
      imports: [
        CustomMaterialModule,
        FormsModule,
        ReactiveFormsModule,
        BrowserAnimationsModule,
        TranslateModule.forRoot({
          loader: {
            provide: TranslateLoader,
            useFactory: HttpLoaderFactory,
            deps: [HttpClient]
          }
        }),
        RouterModule.forRoot([]),
        HttpClientTestingModule
      ],
      providers: [
        { provide: MatDialog, useValue: dialogSpy },
        {
          provide: MatDialogRef,
          useValue: {}
        },
        { provide: MAT_DIALOG_DATA,
          useValue: {
            alerta: {
              notificacaoArea:
              {
                codFesta: 'testeCod7',
                nomeFesta: 'teste',
                codProblema: 1,
                descProblema: 'Tumulto',
                codArea: 1,
                nomeArea: 'teste'
              },
              mensagem: 'AREAPROB?1&1',
            }
          }
        },
        {provide: SegurancaProblemasService, useValue: {
          alterarStatus: () => of({}),
          setFarol: () => false,
        }}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AlertaSegurancaComponent);
    component = fixture.componentInstance;
    const service: LoginService = TestBed.get(LoginService);
    service.usuarioInfo = {codUsuario: '1'};
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should getUrlFesta', () => {
    expect(component.getUrlFesta()).toEqual('../festas/teste&testeCod7/painel-seguranca');
  });

  it('should alterarStatus', () => {
    spyOn(component.segProblemaService, 'alterarStatus')
    .and
    .callThrough();

    spyOn(component.segProblemaService, 'setFarol')
    .and
    .callThrough();

    component.notificacao = {
      notificacaoArea: {
        codAreaProblema: 'testeAreaProblema',
        codArea: 'testeAreaSeguranca',
        codFesta: 'testeAreaProblema',
        codProblema: 'testeAreaProblema'
      }
    };
    component.form.get('observacaoSolucao').setValue('obs');

    const problema = {
      codAreaProblema: 'testeAreaProblema',
      codAreaSeguranca: 'testeAreaSeguranca',
      codFesta: 'testeAreaProblema',
      codProblema: 'testeAreaProblema',
      observacaoSolucao: 'obs',
      statusProblema: 'F',
      codUsuarioResolv: '1',
    };
    component.alterarStatus('F');
    expect(component.segProblemaService.alterarStatus).toHaveBeenCalledWith(true, problema);
    expect(component.segProblemaService.setFarol).toHaveBeenCalledWith(false);
    expect(dialogSpy.closeAll).toHaveBeenCalled();
  });
});
