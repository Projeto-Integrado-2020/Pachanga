import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CriarCupomDialogComponent } from './criar-cupom-dialog.component';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CustomMaterialModule } from '../../views/material/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { of } from 'rxjs/internal/observable/of';
import { CriarCupomService } from 'src/app/services/criar-cupom/criar-cupom.service';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('CriarCupomDialogComponent', () => {
  let component: CriarCupomDialogComponent;
  let fixture: ComponentFixture<CriarCupomDialogComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);
    TestBed.configureTestingModule({
      declarations: [ CriarCupomDialogComponent ],
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
        { provide: CriarCupomService, useValue: {
          criarCupom: () => of({}),
          setFarol: () => false,
        }}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CriarCupomDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get f to get form controls', () => {
    expect(component.f).toBe(component.form.controls);
  });

  it('should criarCupom', () => {
    spyOn(component.addCupom, 'criarCupom')
    .and
    .callThrough();

    spyOn(component.addCupom, 'setFarol')
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
    component.form.get('tipoDesconto').setValue('V');
    component.form.get('precoDesconto').setValue(1);
    component.form.get('nomeCupom').setValue('teste');
    const cupom = {
      nomeCupom: 'teste',
      tipoDesconto: 'V',
      precoDesconto: 1,
      porcentagemDesc: null,
      codFesta: 'teste'
    };
    component.criarCupom();

    expect(component.addCupom.criarCupom).toHaveBeenCalledWith(cupom);
    expect(component.addCupom.setFarol).toHaveBeenCalledWith(false);
    expect(component.component.ngOnInit).toHaveBeenCalled();
    expect(dialogSpy.closeAll).toHaveBeenCalled();
  });

  it('should tipoDescontoValidation', () => {
    component.form.get('nomeCupom').setValue('Teste');

    component.form.get('tipoDesconto').setValue('V');
    expect(component.form.valid).toBeFalsy();
    component.form.get('precoDesconto').setValue(1);
    expect(component.form.valid).toBeTruthy();

    component.form.get('tipoDesconto').setValue('P');
    expect(component.form.valid).toBeFalsy();
    component.form.get('porcentagemDesc').setValue(1);
    expect(component.form.valid).toBeTruthy();
  });
});
