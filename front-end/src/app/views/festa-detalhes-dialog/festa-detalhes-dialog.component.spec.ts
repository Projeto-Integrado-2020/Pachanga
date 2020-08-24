/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { FestaDetalhesDialogComponent } from './festa-detalhes-dialog.component';
import { LoginService } from 'src/app/services/loginService/login.service';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { CustomMaterialModule } from '../material/material.module';
import { HttpClientModule } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('FestaDetalhesDialogComponent', () => {
  let component: FestaDetalhesDialogComponent;
  let fixture: ComponentFixture<FestaDetalhesDialogComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);
    TestBed.configureTestingModule({
      declarations: [ FestaDetalhesDialogComponent ],
      imports: [
        CustomMaterialModule,
        HttpClientTestingModule
      ],
      providers: [
        { provide: MAT_DIALOG_DATA, useValue: {mensagem: "CONVFEST?1&1"}},
        { provide: MatDialog, useValue: dialogSpy },
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FestaDetalhesDialogComponent);
    component = fixture.componentInstance;
    const service: LoginService = TestBed.get(LoginService);
    service.usuarioInfo = {codUsuario: '1'};
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
