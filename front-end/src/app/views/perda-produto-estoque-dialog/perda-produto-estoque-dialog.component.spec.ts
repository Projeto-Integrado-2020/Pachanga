import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { PerdaProdutoEstoqueDialogComponent } from './perda-produto-estoque-dialog.component';

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
import { PerdaProdutoEstoqueService } from 'src/app/services/perda-produto-estoque/perda-produto-estoque.service';
import { of } from 'rxjs';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('PerdaProdutoEstoqueDialogComponent', () => {
  let component: PerdaProdutoEstoqueDialogComponent;
  let fixture: ComponentFixture<PerdaProdutoEstoqueDialogComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);

    TestBed.configureTestingModule({
      declarations: [ PerdaProdutoEstoqueDialogComponent ],
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
        { provide: MAT_DIALOG_DATA, useValue: { element: {dose: true } } },
        { provide: MatDialog, useValue: dialogSpy },
        {provide: PerdaProdutoEstoqueService, useValue: {
          perdaProdutoEstoque: () => of({}),
          setFarol: () => false,
        }},
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PerdaProdutoEstoqueDialogComponent);
    component = fixture.componentInstance;
    const loginService = TestBed.get(LoginService);
    loginService.usuarioInfo = {codUsuario: '1', nomeUser: 'Teste', nomesexo: null, dtNasc: null};
    component.component = {
      quantidadesProdutos: [[{quantidadeAtual: 10}]]
    };
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
    expect(component.form.get('quantidade100')).toBeTruthy();
    expect(component.form.get('quantidade75')).toBeTruthy();
    expect(component.form.get('quantidade50')).toBeTruthy();
    expect(component.form.get('quantidade25')).toBeTruthy();
    expect(component.form.get('quantidade')).toBeFalsy();

    component.element = {dose: false};
    component.gerarForm();
    expect(component.form.get('quantidade100')).toBeFalsy();
    expect(component.form.get('quantidade75')).toBeFalsy();
    expect(component.form.get('quantidade50')).toBeFalsy();
    expect(component.form.get('quantidade25')).toBeFalsy();
    expect(component.form.get('quantidade')).toBeTruthy();
  });

  it('should dosagemValidator', () => {
    component.gerarForm();
    expect(component.form.valid).toBeFalsy();

    component.form.get('quantidade100').setValue(100);
    component.form.get('quantidade75').setValue(75);
    component.form.get('quantidade50').setValue(50);
    component.form.get('quantidade25').setValue(25);
    expect(component.form.valid).toBeTruthy();
  });

  it('should perdaProduto', () => {
    spyOn(component.perdaProdutoEstoque, 'perdaProdutoEstoque')
    .and
    .callThrough();

    spyOn(component.perdaProdutoEstoque, 'setFarol')
    .and
    .callThrough();

    const element = {
      dose: 'false',
      codProduto: 'teste'
    };
    component.estoque = {
      codEstoque: 'teste'
    };
    component.perdaProduto(10, element);
    expect(component.perdaProdutoEstoque.perdaProdutoEstoque).toHaveBeenCalled();
    expect(component.perdaProdutoEstoque.setFarol).toHaveBeenCalledWith(false);
  });

  it('should perdaProdutoDose', () => {
    spyOn(component.perdaProdutoEstoque, 'perdaProdutoEstoque')
    .and
    .callThrough();

    spyOn(component.perdaProdutoEstoque, 'setFarol')
    .and
    .callThrough();

    const element = {
      dose: 'false',
      codProduto: 'teste'
    };
    component.estoque = {
      codEstoque: 'teste'
    };
    component.component = {
      quantidadesProdutos: [[{quantidadeAtual: 10}]]
    };
    component.indexEstoque = 0;
    component.indexProduto = 0;
    component.perdaProdutoDose(25, 50, 75, 100, element);
    expect(component.perdaProdutoEstoque.perdaProdutoEstoque).toHaveBeenCalled();
    expect(component.perdaProdutoEstoque.setFarol).toHaveBeenCalledWith(false);
  });
});
