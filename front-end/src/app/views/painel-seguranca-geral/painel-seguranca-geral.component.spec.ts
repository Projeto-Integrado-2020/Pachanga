import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { CustomMaterialModule } from '../material/material.module';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { HttpLoaderFactory } from '../edit-dialog/edit-dialog.component.spec';
import { HttpClient } from '@angular/common/http';
import { MatDialog } from '@angular/material';
import { LoginService } from 'src/app/services/loginService/login.service';
import { PainelSegurancaGeralComponent } from './painel-seguranca-geral.component';
import { GetSegurancaService } from 'src/app/services/get-seguranca/get-seguranca.service';
import { of } from 'rxjs';

describe('PainelSegurancaGeralComponent', () => {
  let component: PainelSegurancaGeralComponent;
  let fixture: ComponentFixture<PainelSegurancaGeralComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);
    TestBed.configureTestingModule({
      declarations: [ PainelSegurancaGeralComponent ],
      imports: [
        CustomMaterialModule,
        HttpClientTestingModule,
        BrowserAnimationsModule,
        ReactiveFormsModule,
        RouterModule.forRoot([]),
        TranslateModule.forRoot({
          loader: {
            provide: TranslateLoader,
            useFactory: HttpLoaderFactory,
            deps: [HttpClient]
          }
        }),
      ],
      providers: [
        { provide: MatDialog, useValue: dialogSpy },
        {provide: GetSegurancaService, useValue: {
          getAreaSeguranca: () => of([{
            codArea: 1,
            nomeArea: 'Area Teste',
            codAreaProblema: 3,
            problemasArea: [
              {
                descProblema: 'EMERGMED',
                statusProblema: 'A',
                horarioInicio: '2020-11-10T12:30:40'
              }
            ]
          }])
        }},
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PainelSegurancaGeralComponent);
    component = fixture.componentInstance;
    const service: LoginService = TestBed.get(LoginService);
    service.usuarioInfo = {codUsuario: '1'};
    const token = {
      timeToken: '2020-09-21T01:14:04.028+0000',
      token: 'teste'
    };
    localStorage.setItem('token', JSON.stringify(token));
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should open a add security area dialog through a method', () => {
    component.verDetalhesProblema({});
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should processarDataCompra', () => {
    const result = component.processarDataCompra('2020-11-10T12:30:40');
    expect(result).toEqual('10/11/2020 - 12:30:40');
  });

  it('should resgatarAreaSeguranca', () => {
    spyOn(component.getSeguranca, 'getAreaSeguranca')
    .and
    .callThrough();

    component.resgatarAreaSeguranca();
    expect(component.getSeguranca.getAreaSeguranca).toHaveBeenCalled();
  });

  it('should destroy interval', () => {
    component.ngOnDestroy();
    expect(component.source).toBeFalsy();
    expect(component.subscription.closed).toBeTruthy();
  });

  it('should comparator entries', () => {
    let resultado;

    resultado = component.comparator({statusProblema: 'A'}, {statusProblema: 'F'});
    expect(resultado).toBe(-1);

    resultado = component.comparator(
      {
        statusProblema: 'A', horarioInicio: '2020-11-10T12:30:40'
      },
      {
        statusProblema: 'A', horarioInicio: '2020-11-10T11:30:40'
      }
    );
    expect(resultado).toBe(-1);

    resultado = component.comparator(
      {
        statusProblema: 'A', horarioInicio: '2020-11-10T11:30:40'
      },
      {
        statusProblema: 'A', horarioInicio: '2020-11-10T12:30:40'
      }
    );
    expect(resultado).toBe(1);

    resultado = component.comparator(
      {
        statusProblema: 'A', horarioInicio: '2020-11-10T12:30:40', descProblema: 'ABC'
      },
      {
        statusProblema: 'A', horarioInicio: '2020-11-10T11:30:40', descProblema: 'CBA'
      }
    );
    expect(resultado).toBe(-1);

    resultado = component.comparator(
      {
        statusProblema: 'A', horarioInicio: '2020-11-10T12:30:40', descProblema: 'CBA'
      },
      {
        statusProblema: 'A', horarioInicio: '2020-11-10T12:30:40', descProblema: 'ABC'
      }
    );
    expect(resultado).toBe(1);

    resultado = component.comparator(
      {
        statusProblema: 'A', horarioInicio: '2020-11-10T12:30:40', descProblema: 'ABC', nomeArea: 'ABC'
      },
      {
        statusProblema: 'A', horarioInicio: '2020-11-10T12:30:40', descProblema: 'ABC', nomeArea: 'CBA'
      }
    );
    expect(resultado).toBe(-1);

    resultado = component.comparator(
      {
        statusProblema: 'A', horarioInicio: '2020-11-10T12:30:40', descProblema: 'ABC', nomeArea: 'CBA'
      },
      {
        statusProblema: 'A', horarioInicio: '2020-11-10T12:30:40', descProblema: 'ABC', nomeArea: 'ABC'
      }
    );
    expect(resultado).toBe(1);
  });
});
