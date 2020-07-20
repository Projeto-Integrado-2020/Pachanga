import { TestBed } from '@angular/core/testing';

import { EditAccountService } from './edit-account.service';

import { HttpClientModule } from '@angular/common/http';

import { CustomMaterialModule } from '../../views/material/material.module';
import { MatDialog } from '@angular/material';

describe('EditAccountService', () => {
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
    const service: EditAccountService = TestBed.get(EditAccountService);
    expect(service).toBeTruthy();
  });

  it('should set farol', () => {
    const service: EditAccountService = TestBed.get(EditAccountService);
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
    service.setFarol(false);
    expect(service.getFarol()).toBeFalsy();
  });

  it('should get farol', () => {
    const service: EditAccountService = TestBed.get(EditAccountService);
    expect(service.getFarol()).toBeFalsy();
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
  });

  it('should open a dialog through a method', () => {
    const service: EditAccountService = TestBed.get(EditAccountService);
    service.openErrorDialog('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });
});
