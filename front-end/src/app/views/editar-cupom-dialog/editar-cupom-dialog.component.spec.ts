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
import { EditarCupomDialogComponent } from './editar-cupom-dialog.component';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { EditarCupomService } from 'src/app/services/editar-cupom/editar-cupom.service';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('EditarCupomDialogComponent', () => {
  let component: EditarCupomDialogComponent;
  let fixture: ComponentFixture<EditarCupomDialogComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);
    TestBed.configureTestingModule({
      declarations: [ EditarCupomDialogComponent ],
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
        { provide: MAT_DIALOG_DATA, useValue: {
          cupom: {
            nomeCupom: 'teste', codCupom: 'teste', precoDesconto: '', codFesta: '1', tipoDesconto: 'P',
            porcentagemDesc: 1
          },
          codFesta: 'teste'}},
        { provide: MatDialog, useValue: dialogSpy },
        { provide: EditarCupomService, useValue: {
          editarCupom: () => of({}),
          setFarol: () => false,
        }}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditarCupomDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get f to get form controls', () => {
    expect(component.f).toBe(component.form.controls);
  });

  it('should editarCupom', () => {
    spyOn(component.editCupom, 'editarCupom')
    .and
    .callThrough();

    spyOn(component.editCupom, 'setFarol')
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
      codCupom: 'teste',
      codFesta: 'teste',
      tipoDesconto: 'V',
      precoDesconto: 1,
      porcentagemDesc: null,
    };
    component.editarCupom();

    expect(component.editCupom.editarCupom).toHaveBeenCalledWith(cupom);
    expect(component.editCupom.setFarol).toHaveBeenCalledWith(false);
    expect(component.component.ngOnInit).toHaveBeenCalled();
    expect(dialogSpy.closeAll).toHaveBeenCalled();
  });

  it('should tipoDescontoValidation', () => {
    component.form.get('tipoDesconto').setValue('V');
    component.form.get('porcentagemDesc').setValue(null);
    expect(component.form.valid).toBeFalsy();
    component.form.get('precoDesconto').setValue(1);
    expect(component.form.valid).toBeTruthy();

    component.form.get('tipoDesconto').setValue('P');
    expect(component.form.valid).toBeFalsy();
    component.form.get('porcentagemDesc').setValue(1);
    expect(component.form.valid).toBeTruthy();
  });
});
