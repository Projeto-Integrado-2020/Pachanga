import { TestBed, inject } from '@angular/core/testing';
import { MenuFestasService } from './menu-festas.service';
import { HttpClientModule } from '@angular/common/http';
import { CustomMaterialModule } from '../../views/material/material.module';

describe('MenuFestasService', () => {
  beforeEach(() => TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        CustomMaterialModule
      ]
  }));

  it('should be created', () => {
    const service: MenuFestasService = TestBed.get(MenuFestasService);
    expect(service).toBeTruthy();
  });
});
