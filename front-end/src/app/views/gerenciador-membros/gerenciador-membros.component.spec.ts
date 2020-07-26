import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GerenciadorMembrosComponent } from './gerenciador-membros.component';

describe('GerenciadorMembrosComponent', () => {
  let component: GerenciadorMembrosComponent;
  let fixture: ComponentFixture<GerenciadorMembrosComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GerenciadorMembrosComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GerenciadorMembrosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
