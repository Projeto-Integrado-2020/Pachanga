import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteEstoqueDialogComponent } from './delete-estoque-dialog.component';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { CustomMaterialModule } from '../material/material.module';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { HttpClient } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { DeletarEstoqueService } from 'src/app/services/deletar-estoque/deletar-estoque.service';
import { of } from 'rxjs';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('DeleteEstoqueDialogComponent', () => {
  let component: DeleteEstoqueDialogComponent;
  let fixture: ComponentFixture<DeleteEstoqueDialogComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);

    TestBed.configureTestingModule({
      declarations: [
        DeleteEstoqueDialogComponent,
      ],
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
        { provide: MAT_DIALOG_DATA, useValue: {grupo: {codFesta: '1', codUsuario: '1', codGrupo: '1'}} },
        { provide: MatDialog, useValue: dialogSpy },
        {provide: DeletarEstoqueService, useValue: {
          deleteEstoque: () => of({}),
          setFarol: () => false,
        }}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeleteEstoqueDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should deletarProduto', () => {
    spyOn(component.deleteEstoqueService, 'deleteEstoque')
    .and
    .callThrough();

    spyOn(component.deleteEstoqueService, 'setFarol')
    .and
    .callThrough();

    component.component = {
      ngOnInit: () => true
    };
    spyOn(component.component, 'ngOnInit')
    .and
    .callThrough();

    component.codEstoque = 'teste';
    component.codFesta = 'teste';
    component.deleteEstoque();
    expect(component.deleteEstoqueService.deleteEstoque).toHaveBeenCalledWith('teste', 'teste');
    expect(component.deleteEstoqueService.setFarol).toHaveBeenCalledWith(false);
    expect(dialogSpy.closeAll).toHaveBeenCalled();
    expect(component.component.ngOnInit).toHaveBeenCalled();
  });
});
