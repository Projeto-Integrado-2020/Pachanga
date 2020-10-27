import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CustomMaterialModule } from '../../views/material/material.module';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { AlertaEstoqueComponent } from './alerta-estoque.component';
import { RouterModule } from '@angular/router';
import { GetEstoqueService } from 'src/app/services/get-estoque/get-estoque.service';
import { of } from 'rxjs';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginService } from 'src/app/services/loginService/login.service';
import { GetProdutosService } from 'src/app/services/get-produtos/get-produtos.service';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('AlertaEstoqueComponent', () => {
  let component: AlertaEstoqueComponent;
  let fixture: ComponentFixture<AlertaEstoqueComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AlertaEstoqueComponent ],
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
        {
          provide: MatDialogRef,
          useValue: {}
        },
        { provide: MAT_DIALOG_DATA,
          useValue: {
            alerta: { notificacaoEstoque: {
              nomeFesta: 'teste', marca: 'teste', nomeEstoque: 'teste', qtdAtual: 'teste'},
              mensagem: 'TESTE?testeCod7&testeCod8&testeCod9'
            }
          }
        },
        {provide: GetEstoqueService, useValue: {
          getEstoque: () => of([{
            codEstoque: 'testeCod8',
            principal: true,
            itemEstoque: [{codProduto: 'testeCod9', dose: true, quantDoses: 10}]
          }])
        }},
        {provide: GetProdutosService, useValue: {
          getProdutos: () => of([{
            codProduto: 'testeCod9', dose: true, quantDoses: 10
          }]),
          setFarol: () => false
        }}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AlertaEstoqueComponent);
    component = fixture.componentInstance;
    const service: LoginService = TestBed.get(LoginService);
    service.usuarioInfo = {codUsuario: '1'};
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should getUrlFesta', () => {
    expect(component.getUrlFesta()).toEqual('../festas/teste&testeCod7/estoque');
  });

  it('should get f to get form controls', () => {
    expect(component.f).toBe(component.form.controls);
  });

  it('should gerarForm', () => {
    component.gerarForm();
    expect(component.form).toBeTruthy();
    expect(component.form.get('quantidade')).toBeTruthy();
    expect(component.form.get('estoqueOrigem')).toBeTruthy();
  });

  it('should recargaProduto', () => {
    component.produto = {codProduto: 'testeCod9', dose: true, quantDoses: 10};
    spyOn(component.recargaProdutoEstoqueService, 'recargaProdutoEstoque')
    .and
    .callThrough();

    component.recargaProduto(10);
    expect(component.recargaProdutoEstoqueService.recargaProdutoEstoque).toHaveBeenCalled();
  });

  it('should resgatarProduto', () => {
    spyOn(component.getProduto, 'getProdutos')
    .and
    .callThrough();

    component.resgatarProduto();
    expect(component.getProduto.getProdutos).toHaveBeenCalledWith('testeCod7');
  });

  it('should resgatarEstoques', () => {
    component.produto = {codProduto: 'testeCod9', dose: true, quantDoses: 10};
    spyOn(component.getEstoque, 'getEstoque')
    .and
    .callThrough();

    spyOn(component, 'gerarForm')
    .and
    .callThrough();

    component.resgatarEstoques();
    expect(component.getEstoque.getEstoque).toHaveBeenCalledWith('testeCod7');
    expect(component.gerarForm).toHaveBeenCalled();
  });
});
