/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { MenuFestasService } from './menu-festas.service';

describe('Service: MenuFestas', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [MenuFestasService]
    });
  });

  it('should ...', inject([MenuFestasService], (service: MenuFestasService) => {
    expect(service).toBeTruthy();
  }));
});
