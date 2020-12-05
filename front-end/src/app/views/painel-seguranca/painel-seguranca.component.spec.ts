import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PainelSegurancaComponent } from './painel-seguranca.component';
import { CustomMaterialModule } from '../material/material.module';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { HttpLoaderFactory } from '../edit-dialog/edit-dialog.component.spec';
import { HttpClient } from '@angular/common/http';
import { MatDialog } from '@angular/material';
import { LoginService } from 'src/app/services/loginService/login.service';
import { ControleSidenavComponent } from '../controle-sidenav/controle-sidenav.component';

describe('PainelSegurancaComponent', () => {
  let component: PainelSegurancaComponent;
  let fixture: ComponentFixture<PainelSegurancaComponent>;

  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);
    TestBed.configureTestingModule({
      declarations: [
        PainelSegurancaComponent,
        ControleSidenavComponent
      ],
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
        { provide: MatDialog, useValue: dialogSpy },
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PainelSegurancaComponent);
    component = fixture.componentInstance;
    const service: LoginService = TestBed.get(LoginService);
    service.usuarioInfo = {codUsuario: '1'};
    component.festa = {codFesta: '1'};
    component.areas = [{codArea: '1', codFesta: '1', nomeArea: 'Nome', statusSeguranca: 'S'}];
    const token = {
      timeToken: '2020-09-21T01:14:04.028+0000',
      token: 'teste'
    };
    localStorage.setItem('token', JSON.stringify(token));
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should open a delete security area dialog through a method', () => {
    component.openDialogDelete(component.festa.codFesta);
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should open a edit security area dialog through a method', () => {
    component.openDialogEdit(component.areas[0]);
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should open a add security area dialog through a method', () => {
    component.openDialogCriarArea();
    expect(dialogSpy.open).toHaveBeenCalled();
  });

});
