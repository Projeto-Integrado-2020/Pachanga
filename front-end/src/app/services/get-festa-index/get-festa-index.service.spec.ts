import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { GetFestaIndexService } from './get-festa-index.service';
import { RouterModule } from '@angular/router';
import { CustomMaterialModule } from '../../views/material/material.module';

describe('GetFestaIndexService', () => {
  let service: GetFestaIndexService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        RouterModule.forRoot([]),
        CustomMaterialModule
      ]
    });
    const token = {
      timeToken: '2020-09-21T01:14:04.028+0000',
      token: 'teste'
    };
    localStorage.setItem('token', JSON.stringify(token));
    service = TestBed.get(GetFestaIndexService);
  });

  it('should be created', () => {
    const service: GetFestaIndexService = TestBed.get(GetFestaIndexService);
    expect(service).toBeTruthy();
  });

  it('should set farol', () => {
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
    service.setFarol(false);
    expect(service.getFarol()).toBeFalsy();
  });

  it('should get farol', () => {
    expect(service.getFarol()).toBeFalsy();
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
  });

  it('should get Info at acessarFesta', () => {
    expect(service.acessarFesta()).toBeTruthy();

    expect(service.getFarol()).toBeTruthy();

    expect(service.acessarFesta()).toBeFalsy();
  });
});
