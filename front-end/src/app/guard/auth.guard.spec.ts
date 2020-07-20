import { TestBed } from '@angular/core/testing';

import { AuthGuard } from './auth.guard';
import { RouterModule } from '@angular/router';

import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CustomMaterialModule } from '../views/material/material.module';

describe('AuthGuard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        RouterModule.forRoot([]),
        CustomMaterialModule
      ]
    });
  });

  it('should be created', () => {
    const service: AuthGuard = TestBed.get(AuthGuard);
    expect(service).toBeTruthy();
  });

  it('should redirect to index in pages that need authentication', () => {
    const service: AuthGuard = TestBed.get(AuthGuard);
    service.loginService.setUsuarioAutenticado(false);
    const result = service.canActivate(null, null);
    expect(result).toBeFalsy();
  });

  it('should stay in pages that need authentication when authenticated', () => {
    const service: AuthGuard = TestBed.get(AuthGuard);
    service.loginService.setUsuarioAutenticado(true);
    const result = service.canActivate(null, null);
    expect(result).toBeTruthy();
  });
});
