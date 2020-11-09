import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CustomMaterialModule } from '../../views/material/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { LoginService } from 'src/app/services/loginService/login.service';
import { of } from 'rxjs';
import { EditarIntegracoesDialogComponent } from './editar-integracoes-dialog.component';
import { EditarIntegracaoService } from 'src/app/services/editar-integracao/editar-integracao.service';
import { SymplaApiService } from 'src/app/services/sympla-api/sympla-api.service';
import { EventbriteApiService } from 'src/app/services/eventbrite-api/eventbrite-api.service';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('EditarIntegracoesDialogComponent', () => {
  let component: EditarIntegracoesDialogComponent;
  let fixture: ComponentFixture<EditarIntegracoesDialogComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);
    TestBed.configureTestingModule({
      declarations: [ EditarIntegracoesDialogComponent ],
      imports: [
        HttpClientTestingModule,
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
        RouterModule.forRoot([])
      ],
      providers: [
        { provide: MAT_DIALOG_DATA, useValue: {integracao: {codInfo: 'Teste'},
          codFesta: 'teste'}
        },
        { provide: MatDialog, useValue: dialogSpy },
        {provide: EditarIntegracaoService, useValue: {
          editarIntegracao: () => of({}),
          setFarol: () => false,
        }},
        {provide: SymplaApiService, useValue: {
          testSymplaConnection: () => of({})
        }},
        {provide: EventbriteApiService, useValue: {
          testEventbriteConnection: () => of({})
        }}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditarIntegracoesDialogComponent);
    component = fixture.componentInstance;
    component.integracao = {codInfo: 'teste'};
    const service: LoginService = TestBed.get(LoginService);
    service.usuarioInfo = {codUsuario: '1'};
    component.component = {
      ngOnInit: () => {
        return true;
      }
    };
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get f to get form controls', () => {
    expect(component.f).toBe(component.form.controls);
  });

  it('should editarIntegracao', () => {
    spyOn(component.editarService, 'editarIntegracao')
    .and
    .callThrough();

    spyOn(component.editarService, 'setFarol')
    .and
    .callThrough();

    spyOn(component.component, 'ngOnInit')
    .and
    .callThrough();

    component.codFesta = 'teste';
    const integracao = {
      codFesta: 'teste',
      codInfo: 'teste',
      terceiroInt: 'teste',
      codEvent: 'teste',
      token: 'teste'
    };
    component.editarIntegracao(integracao);

    expect(component.editarService.editarIntegracao).toHaveBeenCalledWith(integracao);
    expect(component.editarService.setFarol).toHaveBeenCalledWith(false);
    expect(component.component.ngOnInit).toHaveBeenCalled();
    expect(dialogSpy.closeAll).toHaveBeenCalled();
  });

  it('should checarIntegracao Sympla', () => {
    spyOn(component.symplaService, 'testSymplaConnection')
    .and
    .callThrough();

    spyOn(component, 'editarIntegracao')
    .and
    .callThrough();

    const integracao = {
      codFesta: 'teste',
      codInfo: 'teste',
      terceiroInt: 'S',
      codEvent: 'testeEvento',
      token: 'testeToken'
    };
    component.checarIntegracao(integracao);

    expect(component.symplaService.testSymplaConnection).toHaveBeenCalledWith('testeEvento', 'testeToken');
    expect(component.editarIntegracao).toHaveBeenCalled();
  });

  it('should checarIntegracao Eventbrite', () => {
    spyOn(component.eventbriteService, 'testEventbriteConnection')
    .and
    .callThrough();

    spyOn(component, 'editarIntegracao')
    .and
    .callThrough();

    const integracao = {
      codFesta: 'teste',
      codInfo: 'teste',
      terceiroInt: 'E',
      codEvent: 'testeEvento',
      token: 'testeToken'
    };
    component.checarIntegracao(integracao);

    expect(component.eventbriteService.testEventbriteConnection).toHaveBeenCalledWith('testeEvento', 'testeToken');
    expect(component.editarIntegracao).toHaveBeenCalled();
  });

  it('should submitForm', () => {
    spyOn(component, 'checarIntegracao')
    .and
    .callThrough();

    const integracao = {
      codFesta: 'teste',
      codInfo: 'teste',
      terceiroInt: 'E',
      codEvent: 'testeEvento',
      token: 'testeToken'
    };
    component.integracao = integracao;
    component.submitForm('testeEvento', 'testeToken');

    expect(component.checarIntegracao).toHaveBeenCalledWith(integracao);
  });
});
