/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { ConvidadoService } from './convidado.service';
import { HttpClientModule } from '@angular/common/http';

describe('Service: Convidado', () => {
  let service: ConvidadoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
      ],
      providers: [ConvidadoService]
    });
    const token = {
      timeToken: '2020-09-21T01:14:04.028+0000',
      token: 'teste'
    };
    localStorage.setItem('token', JSON.stringify(token));
    service = TestBed.get(ConvidadoService);
  });

  it('should ...', () => {
    expect(service).toBeTruthy();
  });

  it('should post Info at aceitarConvite', () => {
    expect(service.aceitarConvite('teste', 'teste')).toBeTruthy();
  });

  it('should post Info at recusarConvite', () => {
    expect(service.recusarConvite('teste', 'teste')).toBeTruthy();
  });

  it('should get Info at getDetalhesFesta', () => {
    expect(service.getDetalhesFesta('teste', 'teste')).toBeTruthy();
  });
});
