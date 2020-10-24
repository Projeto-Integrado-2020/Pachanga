import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CriarAreaSegurancaDialogComponent } from './criar-area-seguranca-dialog.component';

describe('CriarAreaSegurancaDialogComponent', () => {
  let component: CriarAreaSegurancaDialogComponent;
  let fixture: ComponentFixture<CriarAreaSegurancaDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CriarAreaSegurancaDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CriarAreaSegurancaDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
