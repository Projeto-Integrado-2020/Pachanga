import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CustomMaterialModule } from '../material/material.module';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { of } from 'rxjs';
import { GerenciadorCuponsComponent } from './gerenciador-cupons.component';
import { GetCuponsService } from 'src/app/services/get-cupons/get-cupons.service';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('GerenciadorCuponsComponent', () => {
  let component: GerenciadorCuponsComponent;
  let fixture: ComponentFixture<GerenciadorCuponsComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);
    TestBed.configureTestingModule({
      declarations: [ GerenciadorCuponsComponent ],
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
        { provide: MAT_DIALOG_DATA, useValue: {cupom: {nomeCupom: 'teste', codCupom: 'teste', precoDesconto: 'teste', codFesta: 'teste'}}},
        { provide: MatDialog, useValue: dialogSpy },
        { provide: GetCuponsService, useValue: {
          getCupons: () => of([{
            nomeCupom: 'teste',
            codCupom: 'teste',
            precoDesconto: 'teste',
            codFesta: 'teste'
          }]),
          setFarol: () => false,
        }}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GerenciadorCuponsComponent);
    component = fixture.componentInstance;
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
    component.openDialogEdit({codCupom: 'teste', precoDesconto: '1'});
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should resgatarCupom', () => {
    spyOn(component.getCupom, 'getCupons')
    .and
    .callThrough();

    spyOn(component.getCupom, 'setFarol')
    .and
    .callThrough();

    component.resgatarCupons();
    expect(component.getCupom.getCupons).toHaveBeenCalled();
    expect(component.getCupom.setFarol).toHaveBeenCalledWith(false);
  });

  it('nomeCupomSort', () => {
    expect(component.nomeCupomSort({nomeCupom: 'A'}, {nomeCupom: 'B'})).toBe(-1);
    expect(component.nomeCupomSort({nomeCupom: 'B'}, {nomeCupom: 'A'})).toBe(1);
  });
});
