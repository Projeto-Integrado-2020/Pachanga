import { TestBed } from '@angular/core/testing';
import { HttpErrorResponse, HttpClientModule } from '@angular/common/http';
import { DeletarFestaService } from './deletar-festa.service';
import { CustomMaterialModule } from '../../views/material/material.module';
import { MatDialog, MatDialogRef } from '@angular/material';
import { throwError } from 'rxjs';

describe('DeletarFestaService', () => {
  let dialogSpy: MatDialog;

  beforeEach(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);
    TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        CustomMaterialModule
      ],
      providers: [
        { provide: MatDialog, useValue: dialogSpy },
        { provide: MatDialogRef, useValue: {} }
      ]
    });
  });

  it('should be created', () => {
    const service: DeletarFestaService = TestBed.get(DeletarFestaService);
    expect(service).toBeTruthy();
  });

  it('should set farol', () => {
    const service: DeletarFestaService = TestBed.get(DeletarFestaService);
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
    service.setFarol(false);
    expect(service.getFarol()).toBeFalsy();
  });

  it('should get farol', () => {
    const service: DeletarFestaService = TestBed.get(DeletarFestaService);
    expect(service.getFarol()).toBeFalsy();
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
  });

  it('should open a dialog through a method', () => {
    const service: DeletarFestaService = TestBed.get(DeletarFestaService);
    service.openErrorDialog('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });
});
