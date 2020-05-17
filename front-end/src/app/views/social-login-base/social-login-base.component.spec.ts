import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SocialLoginBaseComponent } from './social-login-base.component';

import { SocialLoginModule, AuthServiceConfig } from 'angularx-social-login';
import { GoogleLoginProvider, FacebookLoginProvider } from 'angularx-social-login';
import { CustomMaterialModule } from '../material/material.module';

import { HttpClientModule } from '@angular/common/http';

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
    TestBed.configureTestingModule({
      declarations: [ SocialLoginBaseComponent ],
      imports: [
        SocialLoginModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule,
        CustomMaterialModule
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
    fixture = TestBed.createComponent(SocialLoginBaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
