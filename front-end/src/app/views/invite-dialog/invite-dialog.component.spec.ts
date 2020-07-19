/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { InviteDialogComponent } from './invite-dialog.component';

import { CustomMaterialModule } from '../material/material.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatChipInputEvent } from '@angular/material/chips';

describe('InviteDialogComponent', () => {
  let component: InviteDialogComponent;
  let fixture: ComponentFixture<InviteDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InviteDialogComponent ],
      imports: [
        CustomMaterialModule,
        BrowserAnimationsModule
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InviteDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should add e-mail', () => {
    component.add({input: null, value: 'teste@teste.com'});
    expect(component.maillist).toEqual([{address: 'teste@teste.com'}]);
  });

  it('should remove e-mail', () => {
    component.add({input: null, value: 'teste@teste.com'});
    component.remove(component.maillist[0]);
    expect(component.maillist).toEqual([]);
  });
});
