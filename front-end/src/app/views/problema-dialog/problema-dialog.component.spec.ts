import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProblemaDialogComponent } from './problema-dialog.component';

describe('ProblemaDialogComponent', () => {
  let component: ProblemaDialogComponent;
  let fixture: ComponentFixture<ProblemaDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProblemaDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProblemaDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
