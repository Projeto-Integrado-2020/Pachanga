import { TestBed } from '@angular/core/testing';
import { MenuFestasService } from './menu-festas.service';
import { HttpClientModule } from '@angular/common/http';
import { CustomMaterialModule } from '../../views/material/material.module';

describe('MenuFestasService', () => {
  let service: MenuFestasService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        CustomMaterialModule
      ]
    });

    service = TestBed.get(MenuFestasService);
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

  it('should get Info at getFestas', () => {
    expect(service.getFestas('teste')).toBeTruthy();

    expect(service.getFarol()).toBeTruthy();

    expect(service.getFestas('teste')).toBeFalsy();
  });
});
