import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SocialLoginBaseComponent } from './social-login-base.component';

import { SocialLoginModule, AuthServiceConfig } from 'angular4-social-login';
import { GoogleLoginProvider, FacebookLoginProvider } from 'angular4-social-login';
import { CustomMaterialModule } from '../material/material.module';

import { HttpClientTestingModule } from '@angular/common/http/testing';

import { RouterModule } from '@angular/router';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';

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
        }
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
});
