import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GerenciadorProdutosComponent } from './gerenciador-produtos.component';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material';
import { CustomMaterialModule } from '../material/material.module';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { HttpClient } from '@angular/common/http';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { LoginService } from 'src/app/services/loginService/login.service';
import { GetProdutosService } from 'src/app/services/get-produtos/get-produtos.service';
import { of } from 'rxjs';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('GerenciadorProdutosComponent', () => {
  let component: GerenciadorProdutosComponent;
  let fixture: ComponentFixture<GerenciadorProdutosComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);
    TestBed.configureTestingModule({
      declarations: [ GerenciadorProdutosComponent ],
      imports: [
        CustomMaterialModule,
        HttpClientTestingModule,
        BrowserAnimationsModule,
        ReactiveFormsModule,
        RouterModule.forRoot([]),
        TranslateModule.forRoot({
          loader: {
            provide: TranslateLoader,
            useFactory: HttpLoaderFactory,
            deps: [HttpClient]
          }
        }),
      ],
      providers: [
        { provide: MAT_DIALOG_DATA, useValue: {codProduto: 'teste', produto: {
          codProduto: 'teste',
          marca: 'teste',
          preco: 'teste'
        }}},
        { provide: MatDialog, useValue: dialogSpy },
        {provide: GetProdutosService, useValue: {
          getProdutos: () => of([{
            codProduto: 'Teste',
            marca: 'Teste',
            dose: true,
            quantDoses: 100,
            precoMedio: 87
          }]),
          setFarol: () => false,
        }}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GerenciadorProdutosComponent);
    component = fixture.componentInstance;
    component.codFesta = 'teste';
    const service: LoginService = TestBed.get(LoginService);
    service.usuarioInfo = {codUsuario: '1'};
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should open a add-dialog through a method', () => {
    component.openDialogAdd();
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should open a delete-dialog through a method', () => {
    component.openDialogDelete('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should open a edit-dialog through a method', () => {
    component.openDialogEdit({marca: 'teste', preco: 'teste'});
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should resgatarProdutos', () => {
    spyOn(component.getProdutos, 'getProdutos')
    .and
    .callThrough();

    spyOn(component.getProdutos, 'setFarol')
    .and
    .callThrough();

    component.resgatarProdutos();
    expect(component.getProdutos.getProdutos).toHaveBeenCalled();
    expect(component.getProdutos.setFarol).toHaveBeenCalledWith(false);
  });
});
