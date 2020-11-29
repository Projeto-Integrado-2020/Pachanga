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
        { provide: MAT_DIALOG_DATA, useValue: {cupom: {nomeCupom: 'teste', codCupom: 'teste', precoDesconto: '1', codFesta: '1'},
          codFesta: 'teste'}
        },
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
    const cupom = {
      nomeCupom: 'teste',
      codCupom: 'teste',
      codFesta: 'teste',
      precoDesconto: 'teste'
    };
    component.editarCupom('teste', 'teste');

    expect(component.editCupom.editarCupom).toHaveBeenCalledWith(cupom);
    expect(component.editCupom.setFarol).toHaveBeenCalledWith(false);
    expect(component.component.ngOnInit).toHaveBeenCalled();
    expect(dialogSpy.closeAll).toHaveBeenCalled();
  });
});
