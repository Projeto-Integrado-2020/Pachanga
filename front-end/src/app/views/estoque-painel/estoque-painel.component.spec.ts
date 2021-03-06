import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EstoquePainelComponent } from './estoque-painel.component';
import { CustomMaterialModule } from '../material/material.module';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { HttpLoaderFactory } from '../edit-dialog/edit-dialog.component.spec';
import { HttpClient } from '@angular/common/http';
import { MatDialog } from '@angular/material';
import { LoginService } from 'src/app/services/loginService/login.service';
import { ControleSidenavComponent } from '../controle-sidenav/controle-sidenav.component';

describe('EstoquePainelComponent', () => {
  let component: EstoquePainelComponent;
  let fixture: ComponentFixture<EstoquePainelComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);
    TestBed.configureTestingModule({
      declarations: [
        EstoquePainelComponent,
        ControleSidenavComponent
      ],
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
        { provide: MatDialog, useValue: dialogSpy },
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EstoquePainelComponent);
    component = fixture.componentInstance;
    const service: LoginService = TestBed.get(LoginService);
    service.usuarioInfo = {codUsuario: '1'};
    component.festa = {codFesta: '1'};
    const token = {
      timeToken: '2020-09-21T01:14:04.028+0000',
      token: 'teste'
    };
    localStorage.setItem('token', JSON.stringify(token));
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should open a delete stock dialog through a method', () => {
    component.openDialogDelete(component.festa.codFesta);
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should open a edit stock dialog through a method', () => {
    component.openDialogEdit(component.festa.codFesta);
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should open a add stock dialog through a method', () => {
    component.openDialogAdd();
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should open a delete stock product dialog through a method', () => {
    component.openDialogDeleteProdEstoque(component.festa.codFesta, component.festa.codFesta);
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should open a edit stock product dialog through a method', () => {
    component.openDialogEditProdEstoque(component.festa.codFesta, component.festa.codFesta);
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should open a add stock product dialog through a method', () => {
    component.openDialogAddProdEstoque(component.festa.codFesta);
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should open a stock refill dialog through a method', () => {
    component.recargaProduto(component.festa.codFesta, component.festa.codFesta, 0, 0);
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should gerarForm', () => {
    component.estoques = [
      {
        codEstoque: '7',
        itemEstoque: [
          {
          codProduto: '77'
          },
          {
            codProduto: '78'
          },
        ]
      }
    ];
    component.gerarForm();
    expect(component.forms).toBeTruthy();
    expect(component.forms['777'].get('quantidade')).toBeTruthy();
    expect(component.forms['778'].get('quantidade')).toBeTruthy();
  });

  it('codEstoqueSort', () => {
    expect(component.codEstoqueSort({codEstoque: 1}, {codEstoque: 2})).toBe(-1);
    expect(component.codEstoqueSort({codEstoque: 2}, {codEstoque: 1})).toBe(1);
  });

  it('marcaProdutoSort', () => {
    expect(component.marcaProdutoSort({marca: 'A'}, {marca: 'B'})).toBe(-1);
    expect(component.marcaProdutoSort({marca: 'B'}, {marca: 'A'})).toBe(1);
  });

});
