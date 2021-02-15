import { TestBed } from '@angular/core/testing';
import { RouterModule } from '@angular/router';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CustomMaterialModule } from '../../views/material/material.module';
import { CheckoutGuard } from './checkout.guard';
import { MatDialog, MatDialogRef } from '@angular/material';

describe('CheckoutGuard', () => {
  let dialogSpy: MatDialog;
  let service: CheckoutGuard;

  beforeEach(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);

    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        RouterModule.forRoot([]),
        CustomMaterialModule
      ],
      providers: [
        { provide: MatDialog, useValue: dialogSpy }
      ]
    });
    const token = {
      timeToken: '2020-09-21T01:14:04.028+0000',
      token: 'teste'
    };
    localStorage.setItem('token', JSON.stringify(token));
    service = TestBed.get(CheckoutGuard);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should stay in pages', () => {
    service.loginService.setUsuarioAutenticado(true);
    service.dadosCheckout.addIngresso([{codIngresso: '1'}]);
    const result = service.canActivate(null, null);
    expect(result).toBeTruthy();
  });

  it('should redirect to index', () => {
    service.loginService.setUsuarioAutenticado(true);
    service.dadosCheckout.cleanStorage();
    const result = service.canActivate(null, null);
    expect(result).toBeFalsy();
  });

  it('should open LoginComponent', () => {
    service.loginService.setUsuarioAutenticado(false);
    const result = service.canActivate(null, null);
    expect(result).toBeFalsy();
    expect(dialogSpy.open).toHaveBeenCalled();
  });
});
