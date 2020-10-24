import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditarAreaSegurancaDialogComponent } from './editar-area-seguranca-dialog.component';

describe('EditarAreaSegurancaDialogComponent', () => {
  let component: EditarAreaSegurancaDialogComponent;
  let fixture: ComponentFixture<EditarAreaSegurancaDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditarAreaSegurancaDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditarAreaSegurancaDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
