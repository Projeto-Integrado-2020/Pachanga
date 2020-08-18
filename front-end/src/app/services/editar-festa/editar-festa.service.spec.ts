import { TestBed } from '@angular/core/testing';

import { EditarFestaService } from './editar-festa.service';

import { HttpClientModule } from '@angular/common/http';

import { CustomMaterialModule } from '../../views/material/material.module';

import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';

import { MatDialog } from '@angular/material';

describe('EditarFestaService', () => {
  let dialogSpy: MatDialog;
  const router = {
    navigate: jasmine.createSpy('navigate')
  };

  beforeEach(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);

    TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        CustomMaterialModule,
        RouterTestingModule
      ],
      providers: [
        { provide: MatDialog, useValue: dialogSpy },
        { provide: Router, useValue: router }
      ]
    });
  });

  it('should be created', () => {
    const service: EditarFestaService = TestBed.get(EditarFestaService);
    expect(service).toBeTruthy();
  });

  it('should open a dialog through a method', () => {
    const service: EditarFestaService = TestBed.get(EditarFestaService);
    service.openErrorDialog('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });

  it('should set farol', () => {
    const service: EditarFestaService = TestBed.get(EditarFestaService);
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
    service.setFarol(false);
    expect(service.getFarol()).toBeFalsy();
  });

  it('should get farol', () => {
    const service: EditarFestaService = TestBed.get(EditarFestaService);
    expect(service.getFarol()).toBeFalsy();
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
  });
});
