import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { MatDialog, MatDialogRef } from '@angular/material';
import { RouterModule } from '@angular/router';
import { CustomMaterialModule } from 'src/app/views/material/material.module';

import { GetSheetsService } from './get-sheets.service';

describe('GetSheetsService', () => {
  let dialogSpy: MatDialog;
  let service: GetSheetsService;

  beforeEach(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);
    TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        CustomMaterialModule,
        RouterModule.forRoot([])
      ],
      providers: [
        { provide: MatDialog, useValue: dialogSpy },
        { provide: MatDialogRef, useValue: {} }
      ]
    });
    const token = {
      timeToken: '2020-09-21T01:14:04.028+0000',
      token: 'teste'
    };
    localStorage.setItem('token', JSON.stringify(token));
    service = TestBed.get(GetSheetsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get Info at getSheets', () => {
    expect(service.getSheets('1gSc_7WCmt-HuSLX01-Ev58VsiFuhbpYVo8krbPCvvqA')).toBeTruthy();
  });
});
