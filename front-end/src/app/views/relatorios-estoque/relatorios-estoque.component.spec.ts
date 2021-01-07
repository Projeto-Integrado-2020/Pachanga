import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RelatoriosEstoqueComponent } from './relatorios-estoque.component';

describe('RelatoriosEstoqueComponent', () => {
  let component: RelatoriosEstoqueComponent;
  let fixture: ComponentFixture<RelatoriosEstoqueComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RelatoriosEstoqueComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RelatoriosEstoqueComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
