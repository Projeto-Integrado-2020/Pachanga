import { TestBed } from '@angular/core/testing';

import { EditAccountService } from './edit-account.service';

import { LoginService } from '../loginService/login.service';

import { HttpClientModule } from '@angular/common/http';

import { CustomMaterialModule } from '../../views/material/material.module';

describe('EditAccountService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [
      HttpClientModule,
      CustomMaterialModule
    ]
  }));

  it('should be created', () => {
    const service: EditAccountService = TestBed.get(EditAccountService);
    expect(service).toBeTruthy();
  });
});
