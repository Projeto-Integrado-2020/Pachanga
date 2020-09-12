import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CriarProdutoDialogComponent } from './criar-produto-dialog.component';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CustomMaterialModule } from '../../views/material/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { CriarProdutoService } from 'src/app/services/criar-produtos/criar-produto.service';
import { of } from 'rxjs/internal/observable/of';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('CriarProdutoDialogComponent', () => {
  let component: CriarProdutoDialogComponent;
  let fixture: ComponentFixture<CriarProdutoDialogComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);
    TestBed.configureTestingModule({
      declarations: [ CriarProdutoDialogComponent ],
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
        {provide: CriarProdutoService, useValue: {
          adicionarProduto: () => of({}),
          setFarol: () => false,
        }}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CriarProdutoDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get f to get form controls', () => {
    expect(component.f).toBe(component.form.controls);
  });

  it('should criarProduto', () => {
    spyOn(component.criarService, 'adicionarProduto')
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
    const produto = {
      marca: 'teste',
      precoMedio: 'teste',
      dose: 'teste',
      quantDoses: 'teste'
    };
    component.form.get('checkbox').setValue('teste');
    component.form.get('dosagem').setValue('teste');
    component.criarProduto('teste', 'teste');

    expect(component.criarService.adicionarProduto).toHaveBeenCalledWith(produto, 'teste');
    expect(component.criarService.setFarol).toHaveBeenCalledWith(false);
    expect(component.component.ngOnInit).toHaveBeenCalled();
    expect(dialogSpy.closeAll).toHaveBeenCalled();
  });

  it('should have dosagemValidator', () => {
    component.form.get('marca').setValue('teste');
    component.form.get('preco').setValue(5);
    component.form.get('checkbox').setValue(false);
    component.form.get('dosagem').setValue(null);
    expect(component.form.valid).toBeTruthy();

    component.form.get('checkbox').setValue(true);
    expect(component.form.valid).toBeFalsy();

    component.form.get('dosagem').setValue(1);
    expect(component.form.valid).toBeTruthy();
  });

  it('should showHideDosagem', () => {
    component.showHideDosagem();
    expect(component.showDosagem).toBeTruthy();
    component.showHideDosagem();
    expect(component.showDosagem).toBeFalsy();
  });
});
