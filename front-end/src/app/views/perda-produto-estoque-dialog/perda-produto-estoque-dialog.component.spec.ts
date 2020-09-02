import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { PerdaProdutoEstoqueDialogComponent } from './perda-produto-estoque-dialog.component';

describe('PerdaProdutoEstoqueDialogComponent', () => {
  let component: PerdaProdutoEstoqueDialogComponent;
  let fixture: ComponentFixture<PerdaProdutoEstoqueDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PerdaProdutoEstoqueDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PerdaProdutoEstoqueDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
