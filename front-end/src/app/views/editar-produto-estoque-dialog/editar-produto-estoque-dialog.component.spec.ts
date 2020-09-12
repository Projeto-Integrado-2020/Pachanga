import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditarProdutoEstoqueDialogComponent } from './editar-produto-estoque-dialog.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CustomMaterialModule } from '../material/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { HttpLoaderFactory } from 'src/app/app.module';
import { HttpClient } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material';
import { EditarProdutoEstoqueService } from 'src/app/services/editar-produto-estoque/editar-produto-estoque.service';
import { of } from 'rxjs/internal/observable/of';

describe('EditarProdutoEstoqueDialogComponent', () => {
  let component: EditarProdutoEstoqueDialogComponent;
  let fixture: ComponentFixture<EditarProdutoEstoqueDialogComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);
    TestBed.configureTestingModule({
      declarations: [ EditarProdutoEstoqueDialogComponent ],
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
        {provide: EditarProdutoEstoqueService, useValue: {
          editarProdutoEstoque: () => of({}),
          setFarol: () => false,
        }}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditarProdutoEstoqueDialogComponent);
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
    expect(component.form.get('quantidadeMax')).toBeTruthy();
    expect(component.form.get('quantidadeAtual')).toBeTruthy();
    expect(component.form.get('porcentagemMin')).toBeTruthy();
  });

  it('should calcularUnidades', () => {
    expect(component.calcularUnidades(100, null)).toBe(100);
    expect(component.calcularUnidades(100, 10)).toBe(10);
    expect(component.calcularUnidades(7, 2)).toBe(4);
  });

  it('should editarProdutoEstoque', () => {
    spyOn(component.editProdutoEstoque, 'editarProdutoEstoque')
    .and
    .callThrough();

    spyOn(component.editProdutoEstoque, 'setFarol')
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

    component.produto = {
      codProduto: 'teste',
      dose: true,
      quantDoses: 10
    };
    component.estoque = {codEstoque: 'teste'};
    component.festa = {codFesta: 'teste'};
    const itemEstoqueTO = {
      codProduto: 'teste',
      codFesta: 'teste',
      codEstoque: 'teste',
      quantidadeMax: 1000,
      quantidadeAtual: 100,
      porcentagemMin: 10
    };
    component.editarProdutoEstoque(100, 10, 10);

    expect(component.editProdutoEstoque.editarProdutoEstoque).toHaveBeenCalledWith(itemEstoqueTO);
    expect(component.editProdutoEstoque.setFarol).toHaveBeenCalledWith(false);
    expect(component.component.ngOnInit).toHaveBeenCalled();
    expect(dialogSpy.closeAll).toHaveBeenCalled();
  });
});
