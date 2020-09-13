import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditDialogComponent } from './edit-dialog.component';

import { CustomMaterialModule } from '../material/material.module';

import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';

import {MAT_DIALOG_DATA, MatDialog} from '@angular/material';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoginService } from 'src/app/services/loginService/login.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { EditAccountService } from 'src/app/services/editAccountService/edit-account.service';
import { of } from 'rxjs';
import { LoginComponent } from '../login/login.component';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('EditDialogComponent', () => {
  let component: EditDialogComponent;
  let fixture: ComponentFixture<EditDialogComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);
    TestBed.configureTestingModule({
      declarations: [ EditDialogComponent ],
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
        { provide: MAT_DIALOG_DATA, useValue: {campo: 'nome'} },
        { provide: MatDialog, useValue: dialogSpy },
        {provide: EditAccountService, useValue: {
          atualizar: () => of({nomeUser: 'teste'}),
          setFarol: () => false,
        }}
    ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditDialogComponent);
    component = fixture.componentInstance;
    component.loginService = TestBed.get(LoginService);
    component.loginService.usuarioInfo = {nomeUser: 'teste'};
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

  it('should save name at salvarNome and callService', () => {
    spyOn(component, 'callService');
    component.salvarNome('teste');
    expect(component.modJson.nomeUser).toBe('teste');
    expect(component.callService).toHaveBeenCalled();
  });

  it('should save email at salvarEmail and callService', () => {
    spyOn(component, 'callService');
    component.salvarEmail('senha', 'teste');
    expect(component.modJson.emailNovo).toBe('teste');
    expect(component.modJson.senha).toBe('senha');
    expect(component.callService).toHaveBeenCalled();
  });

  it('should save dtNasc at salvarDtNasc and callService', () => {
    spyOn(component, 'callService');
    component.salvarDtNasc('01/02/2000');
    expect(component.modJson.dtNasc).toBe('2000-02-01');
    expect(component.callService).toHaveBeenCalled();
  });

  it('should save sexo at salvarSexo and callService', () => {
    spyOn(component, 'callService');
    component.salvarSexo('M');
    expect(component.modJson.sexo).toBe('M');
    expect(component.callService).toHaveBeenCalled();
  });

  it('should save senha at salvarSenha and callService', () => {
    spyOn(component, 'callService');
    component.salvarSenha('antiga', 'nova');
    expect(component.modJson.senhaNova).toBe('nova');
    expect(component.modJson.senha).toBe('antiga');
    expect(component.callService).toHaveBeenCalled();
  });

  it('should callService', () => {
    spyOn(component.editService, 'atualizar')
    .and
    .callThrough();

    component.modJson = {nomeUser: 'mockMod'};
    component.callService();
    expect(component.editService.atualizar).toHaveBeenCalledWith({nomeUser: 'mockMod'}, {nomeUser: 'teste'});
    expect(component.loginService.usuarioInfo).toEqual({nomeUser: 'teste'});
  });
});
