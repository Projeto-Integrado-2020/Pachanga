import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditEstoqueDialogComponent } from './edit-estoque-dialog.component';
import { CustomMaterialModule } from '../material/material.module';
import { NgxMaterialTimepickerModule } from 'ngx-material-timepicker';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { HttpClient } from '@angular/common/http';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { LoginService } from 'src/app/services/loginService/login.service';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('EditEstoqueDialogComponent', () => {
  let component: EditEstoqueDialogComponent;
  let fixture: ComponentFixture<EditEstoqueDialogComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);
    TestBed.configureTestingModule({
      declarations: [ EditEstoqueDialogComponent ],
      imports: [
        CustomMaterialModule,
        NgxMaterialTimepickerModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientTestingModule,
        BrowserAnimationsModule,
        TranslateModule.forRoot({
          loader: {
            provide: TranslateLoader,
            useFactory: HttpLoaderFactory,
            deps: [HttpClient]
          }
        })],
        providers: [
          { provide: MAT_DIALOG_DATA, useValue: { codFesta: '1', estoque: { nomeEstoque: 'Estoque' } } },
          { provide: MatDialog, useValue: dialogSpy }
      ]
      })
      .compileComponents();
    }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditEstoqueDialogComponent);
    component = fixture.componentInstance;
    const service: LoginService = TestBed.get(LoginService);
    service.usuarioInfo = {codUsuario: '1'};
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should gerarForm', () => {
    component.gerarForm();
    expect(component.form).toBeTruthy();
    expect(component.form.get('nomeEstoque')).toBeTruthy();
  });
});
