import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CriarProdutoEstoqueDialogComponent } from './criar-produto-estoque-dialog.component';
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

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('CriarProdutoEstoqueDialogComponent', () => {
  let component: CriarProdutoEstoqueDialogComponent;
  let fixture: ComponentFixture<CriarProdutoEstoqueDialogComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);
    TestBed.configureTestingModule({
      declarations: [ CriarProdutoEstoqueDialogComponent ],
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
        { provide: MAT_DIALOG_DATA, useValue: {produto: {quantidadeMax: '1', quantidadeAtual: '1', codProduto: '1', porcentagemMin: '1'},
          codFesta: '1', codEstoque: '1'}
        },
        { provide: MatDialog, useValue: dialogSpy },
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CriarProdutoEstoqueDialogComponent);
    component = fixture.componentInstance;
    const service: LoginService = TestBed.get(LoginService);
    service.usuarioInfo = {codUsuario: '1'};
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get f to get form controls', () => {
    expect(component.f).toBe(component.form.controls);
  });

  it('should gerarForm', () => {
    component.gerarForm();
    expect(component.form).toBeTruthy();
    expect(component.form.get('codProduto')).toBeTruthy();
    expect(component.form.get('quantidadeMax')).toBeTruthy();
    expect(component.form.get('quantidadeAtual')).toBeTruthy();
    expect(component.form.get('porcentagemMin')).toBeTruthy();
  });
});
