import { TestBed } from '@angular/core/testing';

import { CadastrarFestaService } from './cadastrar-festa.service';
import { HttpErrorResponse, HttpClientModule } from '@angular/common/http';

import { CustomMaterialModule } from '../../views/material/material.module';
import { MatDialog } from '@angular/material';
import { throwError } from 'rxjs';

describe('CadastrarFestaService', () => {
  let dialogSpy: MatDialog;

  beforeEach(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);
    TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        CustomMaterialModule
      ],
      providers: [
        { provide: MatDialog, useValue: dialogSpy },
      ]
    });
  });

  it('should be created', () => {
    const service: CadastrarFestaService = TestBed.get(CadastrarFestaService);
    expect(service).toBeTruthy();
  });

  it('should set farol', () => {
    const service: CadastrarFestaService = TestBed.get(CadastrarFestaService);
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
    service.setFarol(false);
    expect(service.getFarol()).toBeFalsy();
  });

  it('should get farol', () => {
    const service: CadastrarFestaService = TestBed.get(CadastrarFestaService);
    expect(service.getFarol()).toBeFalsy();
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
  });

  it('should open a dialog through a method', () => {
    const service: CadastrarFestaService = TestBed.get(CadastrarFestaService);
    service.openErrorDialog('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });
});
