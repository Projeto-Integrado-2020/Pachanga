import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeletarProdutoDialogComponent } from './deletar-produto-dialog.component';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { CustomMaterialModule } from '../material/material.module';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { HttpLoaderFactory } from 'src/app/app.module';
import { HttpClient } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { of } from 'rxjs';
import { DeletarProdutoService } from 'src/app/services/deletar-produto/deletar-produto.service';

describe('DeletarProdutoDialogComponent', () => {
  let component: DeletarProdutoDialogComponent;
  let fixture: ComponentFixture<DeletarProdutoDialogComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);
    TestBed.configureTestingModule({
      declarations: [ DeletarProdutoDialogComponent ],
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
        {provide: DeletarProdutoService, useValue: {
          deleteProduto: () => of({}),
          setFarol: () => false,
        }}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeletarProdutoDialogComponent);
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
    spyOn(component.deleteService, 'deleteProduto')
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
    component.codFesta = 'teste';
    component.deletarProduto();
    expect(component.deleteService.deleteProduto).toHaveBeenCalledWith('teste', 'teste');
    expect(dialogSpy.closeAll).toHaveBeenCalled();
    expect(component.component.ngOnInit).toHaveBeenCalled();
    expect(component.openDialogSuccess).toHaveBeenCalledWith('PRODDELE');
  });
});
