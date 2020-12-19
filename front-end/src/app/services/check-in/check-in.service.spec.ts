import { TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { CheckInService } from './check-in.service';
import { MatDialog } from '@angular/material';

describe('CheckInService', () => {
  let dialogSpy: MatDialog;
  let service: CheckInService;

  beforeEach(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);
    TestBed.configureTestingModule({
      imports: [
        HttpClientModule
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
    service = TestBed.get(CheckInService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should do checkIn at checkInIngresso', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    expect(service.checkInIngresso('teste', 'teste')).toBeTruthy();
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
});
