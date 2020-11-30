import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterModule } from '@angular/router';
import { CustomMaterialModule } from '../../views/material/material.module';
import { GetFestaVendaIngressosService } from './get-festa-venda-ingressos.service';

describe('GetFestaVendaIngressosService', () => {
  let service: GetFestaVendaIngressosService;

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
    service = TestBed.get(GetFestaVendaIngressosService);
  });

  it('should be created', () => {
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

  it('should get Info at getFestaVenda', () => {
    expect(service.getFestaVenda('teste')).toBeTruthy();

    expect(service.getFarol()).toBeTruthy();

    expect(service.getFestaVenda('teste')).toBeFalsy();
  });
});
