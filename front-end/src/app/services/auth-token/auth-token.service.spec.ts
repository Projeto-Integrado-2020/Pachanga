import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';

import { AuthTokenService } from './auth-token.service';

describe('AuthTokenService', () => {
  let service: AuthTokenService;

  beforeEach(() => {

    TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
      ],
      providers: [
      ]
    });

    service = TestBed.get(AuthTokenService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
