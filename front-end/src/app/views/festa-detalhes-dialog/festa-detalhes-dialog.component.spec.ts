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
import { RouterModule } from '@angular/router';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { RouterTestingModule } from '@angular/router/testing';

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
        HttpClientTestingModule,
        RouterTestingModule.withRoutes([]),
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
