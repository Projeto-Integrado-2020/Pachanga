import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteAreaSegurancaDialogComponent } from './delete-area-seguranca-dialog.component';

describe('DeleteAreaSegurancaDialogComponent', () => {
  let component: DeleteAreaSegurancaDialogComponent;
  let fixture: ComponentFixture<DeleteAreaSegurancaDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DeleteAreaSegurancaDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeleteAreaSegurancaDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
