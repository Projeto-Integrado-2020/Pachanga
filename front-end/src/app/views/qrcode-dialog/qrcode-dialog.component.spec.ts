/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { CUSTOM_ELEMENTS_SCHEMA, DebugElement } from '@angular/core';

import { QrcodeDialogComponent } from './qrcode-dialog.component';
import { MAT_DIALOG_DATA } from '@angular/material';

describe('QrcodeDialogComponent', () => {
  let component: QrcodeDialogComponent;
  let fixture: ComponentFixture<QrcodeDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QrcodeDialogComponent ],
      providers: [
        { provide: MAT_DIALOG_DATA,
          useValue: {link: '1' }
        }
    ],
      schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QrcodeDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
