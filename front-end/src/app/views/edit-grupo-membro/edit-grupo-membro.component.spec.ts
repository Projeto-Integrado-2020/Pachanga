import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditGrupoMembroComponent } from './edit-grupo-membro.component';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CustomMaterialModule } from '../material/material.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule, FormControl, Validators } from '@angular/forms';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';
import { LoginService } from 'src/app/services/loginService/login.service';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('EditGrupoMembroComponent', () => {
  let component: EditGrupoMembroComponent;
  let fixture: ComponentFixture<EditGrupoMembroComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);
    TestBed.configureTestingModule({
      declarations: [ EditGrupoMembroComponent ],
      imports: [
      HttpClientTestingModule,
      CustomMaterialModule,
      BrowserAnimationsModule,
      FormsModule,
      ReactiveFormsModule,
      TranslateModule.forRoot({
        loader: {
          provide: TranslateLoader,
          useFactory: HttpLoaderFactory,
          deps: [HttpClient]
        }
      })],
      providers: [
        { provide: MAT_DIALOG_DATA, useValue: { codUsuario: '1', codFesta: '1', grupo: {codGrupo: '1', nomeGrupo: 'Salve'}} },
        { provide: MatDialog, useValue: dialogSpy }
    ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditGrupoMembroComponent);
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
    expect(component.form.get('grupoSelect')).toBeTruthy();
  });

});
