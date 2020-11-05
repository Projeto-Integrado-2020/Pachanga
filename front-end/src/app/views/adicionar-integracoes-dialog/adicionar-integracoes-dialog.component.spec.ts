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
import { of } from 'rxjs';
import { AdicionarIntegracoesDialogComponent } from './adicionar-integracoes-dialog.component';
import { AdicionarIntegracaoService } from 'src/app/services/adicionar-integracao/adicionar-integracao.service';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('AdicionarIntegracoesDialogComponent', () => {
  let component: AdicionarIntegracoesDialogComponent;
  let fixture: ComponentFixture<AdicionarIntegracoesDialogComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);
    TestBed.configureTestingModule({
      declarations: [ AdicionarIntegracoesDialogComponent ],
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
        { provide: MAT_DIALOG_DATA, useValue: {codFesta: 'teste'}},
        { provide: MatDialog, useValue: dialogSpy },
        {provide: AdicionarIntegracaoService, useValue: {
          adicionarIntegracao: () => of({}),
          setFarol: () => false,
        }}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdicionarIntegracoesDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get f to get form controls', () => {
    expect(component.f).toBe(component.form.controls);
  });

  it('should adicionarIntegracao', () => {
    spyOn(component.criarService, 'adicionarIntegracao')
    .and
    .callThrough();

    spyOn(component.criarService, 'setFarol')
    .and
    .callThrough();

    component.component = {
      ngOnInit: () => {
        return true;
      }
    };
    spyOn(component.component, 'ngOnInit')
    .and
    .callThrough();

    component.codFesta = 'teste';
    const integracao = {
      codFesta: 'teste',
      terIntegrado: 'teste',
      codEvento: 'teste',
      privateToken: 'teste'
    };
    component.adicionarIntegracao('teste', 'teste', 'teste');

    expect(component.criarService.adicionarIntegracao).toHaveBeenCalledWith(integracao);
    expect(component.criarService.setFarol).toHaveBeenCalledWith(false);
    expect(component.component.ngOnInit).toHaveBeenCalled();
    expect(dialogSpy.closeAll).toHaveBeenCalled();
  });
});
