import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { CustomMaterialModule } from '../material/material.module';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { of } from 'rxjs';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginService } from 'src/app/services/loginService/login.service';
import { ControleSidenavComponent } from '../controle-sidenav/controle-sidenav.component';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';
import { GetFormsService } from 'src/app/services/get-forms/get-forms.service';
import { VisualizacaoFormsComponent } from './visualizacao-forms.component';
import { SafePipe } from './safe-pipe';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('VisualizacaoFormsComponent', () => {
  let component: VisualizacaoFormsComponent;
  let fixture: ComponentFixture<VisualizacaoFormsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        CustomMaterialModule,
        ReactiveFormsModule,
        BrowserAnimationsModule,
        HttpClientTestingModule,
        TranslateModule.forRoot({
          loader: {
            provide: TranslateLoader,
            useFactory: HttpLoaderFactory,
            deps: [HttpClient]
          }
        }),
        RouterModule.forRoot([])
      ],
      declarations: [
        VisualizacaoFormsComponent,
        SafePipe,
        ControleSidenavComponent
       ],
       schemas: [ CUSTOM_ELEMENTS_SCHEMA ],
       providers: [
        { provide: GetFestaService, useValue: {
          acessarFesta: () => of({
            nomeFesta: 'Teste',
            descricaoFesta: 'Teste',
            codEnderecoFesta: 'Teste',
            organizador: 'Teste',
            descOrganizador: 'Teste',
            horarioInicioFesta: '2020-02-01 12:00:00',
            horarioFimFesta: '2020-02-06 18:00:00',
            categoriaPrimaria: {
              codCategoria: 2,
              nomeCategoria: 'RAVEAFIM'
            },
            categoriaSecundaria: null
          }),
          setFarol: () => false,
        }},
        { provide: GetFormsService, useValue: {
          getQuestionarios: () => of([{
            codQuestionario: 1,
            codFesta: 2,
            nomeQuestionario: 'oi',
            urlQuestionario: 'https://docs.google.com/spreadsheets/d/1LFx8KlUseiQLTnjVDLslMVS8zLVZCESwH5LjDo86AiI/edit#gid=314107439'
          }]),
          setFarol: () => false,
        }}
       ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VisualizacaoFormsComponent);
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

  it('should getQuestionarios', () => {
    spyOn(component.forms, 'getQuestionarios')
    .and
    .callThrough();

    component.getQuestionarios();

    expect(component.forms.getQuestionarios).toHaveBeenCalled();
  });

  it('should getSheets', () => {
    spyOn(component.getSheetsService, 'getSheets')
    .and
    .callThrough();

    component.getSheets('1gSc_7WCmt-HuSLX01-Ev58VsiFuhbpYVo8krbPCvvqA');

    expect(component.getSheetsService.getSheets).toHaveBeenCalled();
  });
});
