import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DistribuicaoPermissoesComponent } from './distribuicao-permissoes.component';

describe('DistribuicaoPermissoesComponent', () => {
  let component: DistribuicaoPermissoesComponent;
  let fixture: ComponentFixture<DistribuicaoPermissoesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DistribuicaoPermissoesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DistribuicaoPermissoesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
