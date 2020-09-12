import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SocialLoginBaseComponent } from './social-login-base.component';

import { SocialLoginModule, AuthServiceConfig } from 'angular4-social-login';
import { GoogleLoginProvider, FacebookLoginProvider } from 'angular4-social-login';
import { CustomMaterialModule } from '../material/material.module';

import { HttpClientTestingModule } from '@angular/common/http/testing';

import { RouterModule, Router } from '@angular/router';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoginService } from 'src/app/services/loginService/login.service';
import { of } from 'rxjs';

const config = new AuthServiceConfig([
  {
    id: GoogleLoginProvider.PROVIDER_ID,
    provider: new GoogleLoginProvider('215875672881-jp0n01npv48op3j0c67mm0jlauoov3hb.apps.googleusercontent.com')
  },
  {
    id: FacebookLoginProvider.PROVIDER_ID,
    provider: new FacebookLoginProvider('620215655237701')
  }
]);

export function provideConfig() {
  return config;
}

describe('SocialLoginBaseComponent', () => {
  let component: SocialLoginBaseComponent;
  let fixture: ComponentFixture<SocialLoginBaseComponent>;
  const router = {
    navigate: jasmine.createSpy('navigate')
  };

  beforeEach(async(() => {
    jasmine.DEFAULT_TIMEOUT_INTERVAL = 100000;
    TestBed.configureTestingModule({
      declarations: [ SocialLoginBaseComponent ],
      imports: [
        SocialLoginModule,
        HttpClientTestingModule,
        FormsModule,
        ReactiveFormsModule,
        CustomMaterialModule,
        RouterModule.forRoot([])
      ],
      providers: [
        {
          provide: AuthServiceConfig,
          useFactory: provideConfig
        },
        { provide: Router, useValue: router },
        {provide: LoginService, useValue: {
          logar: () => of({nomeUser: 'teste'}),
          cadastrar: () => of({}),
          finalizarSessao: () => of({}),
          setUsuarioAutenticado: () => of({}),
          setusuarioInfo: () => of({}),
          setFarol: () => false,
        }}
      ],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    jasmine.DEFAULT_TIMEOUT_INTERVAL = 100000;
    fixture = TestBed.createComponent(SocialLoginBaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should navigate ["/"] when signOut', () => {
    spyOn(component.loginService, 'setUsuarioAutenticado')
    .and
    .callThrough();
    spyOn(component.loginService, 'finalizarSessao')
    .and
    .callThrough();

    component.signOut();
    expect(component.loginService.finalizarSessao).toHaveBeenCalled();
    expect(component.loginService.setUsuarioAutenticado).toHaveBeenCalled();
    expect(router.navigate).toHaveBeenCalledWith(['/']);
  });

  it('should autenticar', () => {
    spyOn(component.loginService, 'setusuarioInfo')
    .and
    .callThrough();
    spyOn(component.loginService, 'setUsuarioAutenticado')
    .and
    .callThrough();
    spyOn(component.loginService, 'setFarol')
    .and
    .callThrough();
    spyOn(component.modal, 'closeAll')
    .and
    .callThrough();

    component.autenticar({nomeUser: 'teste'});
    expect(component.loginService.setUsuarioAutenticado).toHaveBeenCalledWith(true);
    expect(component.loginService.setusuarioInfo).toHaveBeenCalledWith({nomeUser: 'teste'});
    expect(component.loginService.setFarol).toHaveBeenCalledWith(false);
  });

  it('should cadastrar_se', () => {
    spyOn(component.loginService, 'setFarol');
    spyOn(component, 'autenticar');

    component.cadastrar_se({nomeUser: 'teste'});
    expect(component.loginService.setFarol).toHaveBeenCalledWith(false);
    expect(component.autenticar).toHaveBeenCalledWith({nomeUser: 'teste'});
  });
});
