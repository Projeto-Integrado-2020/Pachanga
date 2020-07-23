import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { GetFestaService } from './get-festa.service';
import { RouterModule } from '@angular/router';
import { CustomMaterialModule } from '../../views/material/material.module';

describe('GetFestaService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [
      HttpClientTestingModule,
      RouterModule.forRoot([]),
      CustomMaterialModule
    ]
  }));

  it('should be created', () => {
    const service: GetFestaService = TestBed.get(GetFestaService);
    expect(service).toBeTruthy();
  });

  it('should set farol', () => {
    const service: GetFestaService = TestBed.get(GetFestaService);
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
    service.setFarol(false);
    expect(service.getFarol()).toBeFalsy();
  });

  it('should get farol', () => {
    const service: GetFestaService = TestBed.get(GetFestaService);
    expect(service.getFarol()).toBeFalsy();
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
  });
});
