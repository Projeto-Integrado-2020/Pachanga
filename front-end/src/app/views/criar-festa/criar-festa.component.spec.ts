import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CriarFestaComponent } from './criar-festa.component';

describe('CriarFestaComponent', () => {
  let component: CriarFestaComponent;
  let fixture: ComponentFixture<CriarFestaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CriarFestaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CriarFestaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
