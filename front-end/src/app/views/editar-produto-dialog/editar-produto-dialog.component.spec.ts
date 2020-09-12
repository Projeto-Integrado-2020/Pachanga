import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditarProdutoDialogComponent } from './editar-produto-dialog.component';
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
import { EditarProdutoService } from 'src/app/services/editar-produto/editar-produto.service';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('EditarProdutoDialogComponent', () => {
  let component: EditarProdutoDialogComponent;
  let fixture: ComponentFixture<EditarProdutoDialogComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);
    TestBed.configureTestingModule({
      declarations: [ EditarProdutoDialogComponent ],
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
        { provide: MAT_DIALOG_DATA, useValue: {produto: {marca: '1', preco: '1', codProduto: '1'},
          codFesta: 'teste'}
        },
        { provide: MatDialog, useValue: dialogSpy },
        {provide: EditarProdutoService, useValue: {
          editarProduto: () => of({}),
          setFarol: () => false,
        }}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditarProdutoDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get f to get form controls', () => {
    expect(component.f).toBe(component.form.controls);
  });

  it('should editarProduto', () => {
    spyOn(component.editarService, 'editarProduto')
    .and
    .callThrough();

    spyOn(component.editarService, 'setFarol')
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
      codProduto: '1',
      codFesta: 'teste',
      marca: 'teste',
      precoMedio: 'teste',
      dose: 'teste',
      quantDoses: 'teste'
    };
    component.form.get('checkbox').setValue('teste');
    component.form.get('dosagem').setValue('teste');
    component.editarProduto('teste', 'teste');

    expect(component.editarService.editarProduto).toHaveBeenCalledWith(produto);
    expect(component.editarService.setFarol).toHaveBeenCalledWith(false);
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
