import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomMaterialModule } from '../material/material.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgxMaterialTimepickerModule } from 'ngx-material-timepicker';

import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { HttpClient } from '@angular/common/http';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { RouterModule } from '@angular/router';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { of } from 'rxjs';
import { MaterialFileInputModule } from 'ngx-material-file-input';
import { ImagemAreaProblemaDialogComponent } from './imagem-area-problema-dialog.component';
import { SegurancaProblemasService } from 'src/app/services/seguranca-problemas/seguranca-problemas.service';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('ImagemAreaProblemaDialogComponent', () => {
  let component: ImagemAreaProblemaDialogComponent;
  let fixture: ComponentFixture<ImagemAreaProblemaDialogComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);
    TestBed.configureTestingModule({
      declarations: [ ImagemAreaProblemaDialogComponent ],
      imports: [
        CustomMaterialModule,
        NgxMaterialTimepickerModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientTestingModule,
        BrowserAnimationsModule,
        TranslateModule.forRoot({
          loader: {
            provide: TranslateLoader,
            useFactory: HttpLoaderFactory,
            deps: [HttpClient]
          }
        }),
        RouterModule.forRoot([]),
        MaterialFileInputModule
      ],
      providers: [
        { provide: MAT_DIALOG_DATA, useValue: {codFesta: 'teste'}
        },
        { provide: MatDialog, useValue: dialogSpy },
        { provide: SegurancaProblemasService, useValue: {
          getProblemaSeguranca: () => of({
            codAreaProblema: 7,
            codAreaSeguranca: 1,
            codFesta: 2,
            codProblema: 4,
            codUsuarioResolv: 0,
            statusProblema: 'A',
            horarioInicio: '2020-12-19T13:39:53.474387',
            horarioFim: null,
            codUsuarioEmissor: 1,
            descProblemaEmissor: 'tryrteytryeyetryter',
            observacaoSolucao: null,
            descProblema: 'FURTOOBJ',
            imagemProblema: '1234'
          }),
          setFarol: () => false,
        }},
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ImagemAreaProblemaDialogComponent);
    component = fixture.componentInstance;
    component.detalhes = { problema: {
        codAreaProblema: 12,
        codAreaSeguranca: 1,
        codFesta: 2,
        codProblema: 13,
        codUsuarioEmissor: 1,
        codUsuarioResolv: 0,
        descProblema: 'ENTRPROB',
        descProblemaEmissor: 'dsadsadsa',
        hasImagem: true,
        horarioFim: null,
        horarioInicio: '2020-12-19T17:27:44.73289',
        imagemProblema: '1234',
        observacaoSolucao: '',
        statusProblema: 'A'
      }
    };
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

  it('should alterarPreview', () => {
    component.alterarPreview();
    const stringGif = 'https://portal.ufvjm.edu.br/a-universidade/cursos/grade_curricular_ckan/loading.gif/@@images/image.gif';
    expect(component.urlNoImage).toBe(stringGif);
  });
});
