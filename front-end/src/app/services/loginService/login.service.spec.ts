import { TestBed } from '@angular/core/testing';

import { LoginService } from './login.service';

import { HttpClientModule } from '@angular/common/http';


describe('LoginService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [
      HttpClientModule,
    ]
  }));

  it('should be created', () => {
    const service: LoginService = TestBed.get(LoginService);
    expect(service).toBeTruthy();
  });
});
