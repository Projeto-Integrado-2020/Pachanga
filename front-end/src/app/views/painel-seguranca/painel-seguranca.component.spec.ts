import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PainelSegurancaComponent } from './painel-seguranca.component';

describe('PainelSegurancaComponent', () => {
  let component: PainelSegurancaComponent;
  let fixture: ComponentFixture<PainelSegurancaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PainelSegurancaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PainelSegurancaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
