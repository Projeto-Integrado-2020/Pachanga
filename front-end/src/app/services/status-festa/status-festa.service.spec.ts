import { TestBed } from '@angular/core/testing';
import { CustomMaterialModule } from '../../views/material/material.module';
import { HttpClientModule } from '@angular/common/http';
import { StatusFestaService } from './status-festa.service';
import { MatDialog, MatDialogRef } from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';


describe('StatusFestaService', () => {
  let dialogSpy: MatDialog;

  beforeEach(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);
    TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        CustomMaterialModule,
        BrowserAnimationsModule
      ],
      providers: [
        { provide: MatDialog, useValue: dialogSpy },
        { provide: MatDialogRef, useValue: {} }
      ]
    });
  });

  it('should be created', () => {
    const service: StatusFestaService = TestBed.get(StatusFestaService);
    expect(service).toBeTruthy();
  });

  it('should set farol', () => {
    const service: StatusFestaService = TestBed.get(StatusFestaService);
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
    service.setFarol(false);
    expect(service.getFarol()).toBeFalsy();
  });

  it('should get farol', () => {
    const service: StatusFestaService = TestBed.get(StatusFestaService);
    expect(service.getFarol()).toBeFalsy();
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
  });

  it('should open a dialog through a method', () => {
    const service: StatusFestaService = TestBed.get(StatusFestaService);
    service.openErrorDialog('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });

});
