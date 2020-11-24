/* tslint:disable:no-unused-variable */

import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed, async, inject } from '@angular/core/testing';
import { RouterModule } from '@angular/router';
import { CustomMaterialModule } from 'src/app/views/material/material.module';
import { IngressosService } from './ingressos.service';

describe('Service: Ingressos', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        RouterModule.forRoot([]),
        CustomMaterialModule
      ],
      providers: [IngressosService]
    });
  });

  it('should ...', inject([IngressosService], (service: IngressosService) => {
    expect(service).toBeTruthy();
  }));
});
