import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RemoverUnidadeDoseDialogComponent } from './remover-unidade-dose-dialog.component';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material';
import { HttpClient } from '@angular/common/http';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { CustomMaterialModule } from '../material/material.module';
import { NgxMaterialTimepickerModule } from 'ngx-material-timepicker';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { of } from 'rxjs/internal/observable/of';
import { BaixaProdutoEstoqueService } from 'src/app/services/baixa-produto-estoque/baixa-produto-estoque.service';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}


describe('RemoverUnidadeDoseDialogComponent', () => {
  let component: RemoverUnidadeDoseDialogComponent;
  let fixture: ComponentFixture<RemoverUnidadeDoseDialogComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);
    TestBed.configureTestingModule({
      declarations: [ RemoverUnidadeDoseDialogComponent ],
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
        })],
        providers: [
          { provide: MAT_DIALOG_DATA, useValue: { codFesta: '1', estoque: { nomeEstoque: 'Estoque' } } },
          { provide: MatDialog, useValue: dialogSpy },
          {provide: BaixaProdutoEstoqueService, useValue: {
            baixaProdutoEstoque: () => of({}),
            setFarol: () => false,
          }},
      ]
      })
      .compileComponents();
    }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RemoverUnidadeDoseDialogComponent);
    component = fixture.componentInstance;
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
    expect(component.form.get('quantidade')).toBeTruthy();
  });

  it('should removerProduto', () => {
    spyOn(component.baixaProdutoEstoqueDoseUnidade, 'baixaProdutoEstoque')
    .and
    .callThrough();

    spyOn(component.baixaProdutoEstoqueDoseUnidade, 'setFarol')
    .and
    .callThrough();

    component.element = {
      dose: true,
      quantDoses: 10,
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
    component.removerProduto(10);
    expect(component.baixaProdutoEstoqueDoseUnidade.baixaProdutoEstoque).toHaveBeenCalled();
    expect(component.baixaProdutoEstoqueDoseUnidade.setFarol).toHaveBeenCalledWith(false);
    expect(dialogSpy.closeAll).toHaveBeenCalled();
  });
});
