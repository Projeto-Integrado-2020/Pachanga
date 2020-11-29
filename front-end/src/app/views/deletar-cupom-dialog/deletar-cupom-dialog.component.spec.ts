import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { CustomMaterialModule } from '../material/material.module';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { HttpLoaderFactory } from 'src/app/app.module';
import { HttpClient } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { of } from 'rxjs';
import { DeletarCupomDialogComponent } from './deletar-cupom-dialog.component';
import { DeletarCupomService } from 'src/app/services/deletar-cupom/deletar-cupom.service';

describe('DeletarCupomDialogComponent', () => {
  let component: DeletarCupomDialogComponent;
  let fixture: ComponentFixture<DeletarCupomDialogComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);
    TestBed.configureTestingModule({
      declarations: [ DeletarCupomDialogComponent ],
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
        { provide: MAT_DIALOG_DATA, useValue: {cupom: {nomeCupom: 'teste', codCupom: 'teste', precoDesconto: '1', codFesta: '1'},
          codFesta: 'teste'}
        },
        { provide: MatDialog, useValue: dialogSpy },
        { provide: DeletarCupomService, useValue: {
          deleteCupom: () => of({}),
          setFarol: () => false,
        }}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeletarCupomDialogComponent);
    component = fixture.componentInstance;
    component.cupom = {nomeCupom: 'teste', codCupom: 'teste', precoDesconto: '1', codFesta: '1'};
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
    spyOn(component.deletarCupom, 'deleteCupom')
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

    component.cupom = {codCupom: 'teste'};
    component.codFesta = 'teste';
    component.deleteCupom();
    expect(component.deletarCupom.deleteCupom).toHaveBeenCalledWith('teste');
    expect(dialogSpy.closeAll).toHaveBeenCalled();
    expect(component.component.ngOnInit).toHaveBeenCalled();
    expect(component.openDialogSuccess).toHaveBeenCalledWith('CUPOMDEL');
  });
});
