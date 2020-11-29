import { TestBed } from '@angular/core/testing';
import { RouterModule } from '@angular/router';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CustomMaterialModule } from '../../views/material/material.module';
import { CheckoutGuard } from './checkout.guard';

describe('CheckoutGuard', () => {
  let service: CheckoutGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        RouterModule.forRoot([]),
        CustomMaterialModule
      ]
    });

    service = TestBed.get(CheckoutGuard);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should stay in pages', () => {
    service.dadosCheckout.addIngresso([{codIngresso: '1'}]);
    const result = service.canActivate(null, null);
    expect(result).toBeTruthy();
  });

  it('should redirect to index', () => {
    service.dadosCheckout.cleanStorage();
    const result = service.canActivate(null, null);
    expect(result).toBeFalsy();
  });
});
