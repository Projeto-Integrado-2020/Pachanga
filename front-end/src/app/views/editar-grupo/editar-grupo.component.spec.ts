import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditarGrupoComponent } from './editar-grupo.component';
import { CustomMaterialModule } from '../material/material.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgxMaterialTimepickerModule } from 'ngx-material-timepicker';

import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { HttpClient } from '@angular/common/http';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { RouterModule } from '@angular/router';
import { MatDialog } from '@angular/material';
import { LoginService } from 'src/app/services/loginService/login.service';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('EditarGrupoComponent', () => {
  let component: EditarGrupoComponent;
  let fixture: ComponentFixture<EditarGrupoComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);

    TestBed.configureTestingModule({
      declarations: [ EditarGrupoComponent ],
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
        }),
        RouterModule.forRoot([])
      ],
      providers: [
        { provide: MatDialog, useValue: dialogSpy },
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditarGrupoComponent);
    component = fixture.componentInstance;
    const service: LoginService = TestBed.get(LoginService);
    service.usuarioInfo = {codUsuario: '1'};
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get f to get form controls', () => {
    expect(component.f).toBe(component.form.controls);
  });

  it('should open a dialog through a method', () => {
    component.openDialogSuccess('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should setFormValues', () => {
    component.grupo = {nomeGrupo: 'Teste'};
    component.permissoesGrupo = [2];
    component.permissoes = [
      {descPermissao: 'teste1', codPermissao: 1}, {descPermissao: 'teste2', codPermissao: 2}
    ];
    component.buildForm();
    component.setFormValues();
    expect(component.f.teste1.value).toBeFalsy();
    expect(component.f.teste2.value).toBeTruthy();
  });
});
