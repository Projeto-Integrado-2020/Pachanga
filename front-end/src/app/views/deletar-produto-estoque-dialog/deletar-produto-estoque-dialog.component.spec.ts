import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeletarProdutoEstoqueDialogComponent } from './deletar-produto-estoque-dialog.component';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { CustomMaterialModule } from '../material/material.module';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { HttpClient } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { DeletarProdutoEstoqueService } from 'src/app/services/deletar-produto-estoque/deletar-produto-estoque.service';
import { of } from 'rxjs';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('DeletarProdutoEstoqueDialogComponent', () => {
  let component: DeletarProdutoEstoqueDialogComponent;
  let fixture: ComponentFixture<DeletarProdutoEstoqueDialogComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);
    TestBed.configureTestingModule({
      declarations: [ DeletarProdutoEstoqueDialogComponent ],
      imports: [
        CustomMaterialModule,
        HttpClientTestingModule,
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
        {provide: DeletarProdutoEstoqueService, useValue: {
          deletarProdutoEstoque: () => of({}),
          setFarol: () => false,
        }}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeletarProdutoEstoqueDialogComponent);
    component = fixture.componentInstance;
    component.produto = {marca: '1', preco: '1', codProduto: '1'};
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should open a dialog through a method', () => {
    component.openDialogSuccess('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should deletarProduto', () => {
    spyOn(component.deleteProdEstoqueService, 'deletarProdutoEstoque')
    .and
    .callThrough();

    spyOn(component.deleteProdEstoqueService, 'setFarol')
    .and
    .callThrough();

    spyOn(component, 'openDialogSuccess')
    .and
    .callThrough();

    component.component = {
      ngOnInit: () => true
    };
    spyOn(component.component, 'ngOnInit')
    .and
    .callThrough();

    component.produto = {codProduto: 'teste'};
    component.estoque = {codEstoque: 'teste'};
    component.deleteProdutoEstoque();
    expect(component.deleteProdEstoqueService.deletarProdutoEstoque).toHaveBeenCalledWith('teste', 'teste');
    expect(component.deleteProdEstoqueService.setFarol).toHaveBeenCalledWith(false);
    expect(dialogSpy.closeAll).toHaveBeenCalled();
    expect(component.component.ngOnInit).toHaveBeenCalled();
  });
});
