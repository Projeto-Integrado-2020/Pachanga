/* tslint:disable:no-unused-variable */

import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed, inject } from '@angular/core/testing';
import { CustomMaterialModule } from 'src/app/views/material/material.module';
import { SegurancaProblemasService } from './seguranca-problemas.service';

describe('Service: SegurancaProblemas', () => {
  let service: SegurancaProblemasService;
  
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        CustomMaterialModule
      ],
      providers: [SegurancaProblemasService]
    });
    const token = {
      timeToken: '2020-09-21T01:14:04.028+0000',
      token: 'teste'
    };
    localStorage.setItem('token', JSON.stringify(token));
    service = TestBed.get(SegurancaProblemasService);
  });

  it('should ...', inject([SegurancaProblemasService], (service: SegurancaProblemasService) => {
    expect(service).toBeTruthy();
  }));
});
