import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RelatoriosVendaComponent } from './relatorios-venda.component';

describe('RelatoriosVendaComponent', () => {
  let component: RelatoriosVendaComponent;
  let fixture: ComponentFixture<RelatoriosVendaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RelatoriosVendaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RelatoriosVendaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
