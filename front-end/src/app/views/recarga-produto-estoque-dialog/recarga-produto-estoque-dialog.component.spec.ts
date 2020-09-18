import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RecargaProdutoEstoqueDialogComponent } from './recarga-produto-estoque-dialog.component';
import { LoginService } from 'src/app/services/loginService/login.service';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material';
import { HttpClient } from '@angular/common/http';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { CustomMaterialModule } from '../material/material.module';
import { NgxMaterialTimepickerModule } from 'ngx-material-timepicker';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { of } from 'rxjs';
import { RecargaProdutoEstoqueService } from 'src/app/services/recarga-produto-estoque/recarga-produto-estoque.service';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('RecargaProdutoEstoqueDialogComponent', () => {
  let component: RecargaProdutoEstoqueDialogComponent;
  let fixture: ComponentFixture<RecargaProdutoEstoqueDialogComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);
    TestBed.configureTestingModule({
      declarations: [ RecargaProdutoEstoqueDialogComponent ],
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
          { provide: MAT_DIALOG_DATA, useValue: { codFesta: '1', estoque: { nomeEstoque: 'Estoque' }, estoques: [{}] } },
          { provide: MatDialog, useValue: dialogSpy },
          {provide: RecargaProdutoEstoqueService, useValue: {
            recargaProdutoEstoque: () => of({}),
            setFarol: () => false,
          }},
      ]
      })
      .compileComponents();
    }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RecargaProdutoEstoqueDialogComponent);
    component = fixture.componentInstance;
    const service: LoginService = TestBed.get(LoginService);
    service.usuarioInfo = {codUsuario: '1'};
    component.principal = '';
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

  it('should recargaProduto', () => {
    spyOn(component.recargaProdutoEstoqueService, 'recargaProdutoEstoque')
    .and
    .callThrough();

    spyOn(component.recargaProdutoEstoqueService, 'setFarol')
    .and
    .callThrough();

    const element = {
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
    component.recargaProduto(10, 10, element);
    expect(component.recargaProdutoEstoqueService.recargaProdutoEstoque).toHaveBeenCalled();
    expect(component.recargaProdutoEstoqueService.setFarol).toHaveBeenCalledWith(false);
    expect(dialogSpy.closeAll).toHaveBeenCalled();
  });
});
