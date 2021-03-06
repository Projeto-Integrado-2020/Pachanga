import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FestaPainelControleComponent } from './festa-painel-controle.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { CustomMaterialModule } from '../material/material.module';
import { ReactiveFormsModule } from '@angular/forms';

import { RouterModule } from '@angular/router';
import { MatDialog } from '@angular/material';
import { LoginService } from 'src/app/services/loginService/login.service';
import { ControleSidenavComponent } from '../controle-sidenav/controle-sidenav.component';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('FestaPainelControleComponent', () => {
  let component: FestaPainelControleComponent;
  let fixture: ComponentFixture<FestaPainelControleComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);
    TestBed.configureTestingModule({
      declarations: [
        FestaPainelControleComponent,
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
    fixture = TestBed.createComponent(FestaPainelControleComponent);
    component = fixture.componentInstance;
    const service: LoginService = TestBed.get(LoginService);
    service.usuarioInfo = {codUsuario: '1'};
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

  it('should open a delete dialog through a method', () => {
    component.openDialogDelete();
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should open a confirmation dialog through a method', () => {
    component.festa = {statusFesta: 'I'};
    component.openDialogStatus(component.festa.statusFesta);
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should setFesta ', () => {
    const json = {
      nomeFesta: 'Teste',
      statusFesta: 'P',
      codFesta: '1',
      usuarios: {
        codUsuario: '1'
      }
    };
    component.setFesta(json);
    expect(component.festa.statusFesta).toEqual('P');
    json.statusFesta = 'I';
    component.setFesta(json);
    expect(component.festa.statusFesta).toEqual('I');
  });

});
