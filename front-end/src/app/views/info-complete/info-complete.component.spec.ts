import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InfoCompleteComponent } from './info-complete.component';
import { CustomMaterialModule } from '../material/material.module';

import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { LoginService } from 'src/app/services/loginService/login.service';
import { MatDialog } from '@angular/material';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('InfoCompleteComponent', () => {
  let component: InfoCompleteComponent;
  let fixture: ComponentFixture<InfoCompleteComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);
    TestBed.configureTestingModule({
      declarations: [ InfoCompleteComponent ],
      imports: [
        CustomMaterialModule,
        HttpClientTestingModule,
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
    fixture = TestBed.createComponent(InfoCompleteComponent);
    const service: LoginService = TestBed.get(LoginService);
    service.usuarioInfo = {sexo: null, dtNasc: null};
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    component.loginService.usuarioInfo = {sexo: null, dtNasc: null};
    expect(component).toBeTruthy();
    expect(component.mensagem).toBeTruthy();
  });

  it('should close message', () => {
    component.loginService.usuarioInfo = {sexo: null, dtNasc: null};
    component.fecharMensagem();
    expect(component.mensagem).toBeFalsy();
  });

  it('should open a dialog perfil through a method', () => {
    component.openDialogPerfil();
    expect(dialogSpy.open).toHaveBeenCalled();
  });
});
