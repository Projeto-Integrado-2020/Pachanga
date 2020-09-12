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
import { GetProdutosService } from 'src/app/services/get-produtos/get-produtos.service';
import { AtribuicaoProdutoEstoqueService } from 'src/app/services/atribuicao-produto-estoque/atribuicao-produto-estoque.service';
import { of } from 'rxjs';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('CriarProdutoEstoqueDialogComponent', () => {
  let component: CriarProdutoEstoqueDialogComponent;
  let fixture: ComponentFixture<CriarProdutoEstoqueDialogComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);
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
        {provide: GetProdutosService, useValue: {
          getProdutos: () => of([{marca: 'Teste1'}, {marca: 'Teste2'}]),
          setFarol: () => false,
        }},
        {provide: AtribuicaoProdutoEstoqueService, useValue: {
          addProdutoEstoque: () => of({}),
          setFarol: () => false,
        }}
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

  it('should resgatarProdutos', () => {
    spyOn(component.getProdutosService, 'getProdutos')
    .and
    .callThrough();

    spyOn(component.getProdutosService, 'setFarol')
    .and
    .callThrough();

    component.codFesta = 'teste';
    component.resgatarProdutos();
    expect(component.getProdutosService.getProdutos).toHaveBeenCalledWith('teste');
    expect(component.getProdutosService.setFarol).toHaveBeenCalledWith(false);
    expect(component.produtos).toEqual([{marca: 'Teste1'}, {marca: 'Teste2'}]);
  });

  it('should addProdutoEstoque', () => {
    spyOn(component.addProdEstoqueService, 'addProdutoEstoque')
    .and
    .callThrough();

    spyOn(component.addProdEstoqueService, 'setFarol')
    .and
    .callThrough();

    component.component = {
      ngOnInit: () =>  true
    };
    spyOn(component.component, 'ngOnInit')
    .and
    .callThrough();

    const itemEstoque = {
      codProduto: 'teste',
      codFesta: 'teste',
      codEstoque: 'teste',
      quantidadeMax: 100,
      quantidadeAtual: 100,
      porcentagemMin: 10
    };
    component.codFesta = 'teste';
    component.estoque = {codEstoque: 'teste'};
    component.produtos = [{codProduto: 'teste', dose: true, quantDoses: 10}];
    component.addProdutoEstoque('teste', 10, 10, 10);

    expect(component.addProdEstoqueService.addProdutoEstoque).toHaveBeenCalledWith(itemEstoque, 'teste');
    expect(component.addProdEstoqueService.setFarol).toHaveBeenCalledWith(false);
    expect(dialogSpy.closeAll).toHaveBeenCalled();
    expect(component.component.ngOnInit).toHaveBeenCalled();
  });
});
