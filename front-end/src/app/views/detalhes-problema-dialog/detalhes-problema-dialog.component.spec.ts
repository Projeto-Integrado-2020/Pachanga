/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { DetalhesProblemaDialogComponent } from './detalhes-problema-dialog.component';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { HttpLoaderFactory } from 'src/app/app.module';
import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('DetalhesProblemaDialogComponent', () => {
  let component: DetalhesProblemaDialogComponent;
  let fixture: ComponentFixture<DetalhesProblemaDialogComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetalhesProblemaDialogComponent ],
      imports: [
        HttpClientTestingModule,
        TranslateModule.forRoot({
          loader: {
            provide: TranslateLoader,
            useFactory: HttpLoaderFactory,
            deps: [HttpClient]
          }
        })
      ],
      providers: [
        {
          provide: MAT_DIALOG_DATA, useValue: {
            problema: {
              codAreaProblema: 2,
              codAreaSeguranca: 4,
              codFesta: 6,
              codProblema: 2,
              codUsuarioEmissor: 3,
              codUsuarioResolv: 0,
              descProblema: 'Briga',
              horarioFim: null,
              horarioInicio: '2020-11-05T13:36:29.126907',
              statusProblema: 'A'
            }
          }
        },
        { provide: MatDialog, useValue: dialogSpy }
        // {provide: RelatarProblemaDialogComponent, useValue: {
        //   atualizarAreaSeguranca: () => of({}),
        //   setFarol: () => false,
        // }}
    ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetalhesProblemaDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
