import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DistribuicaoPermissoesComponent } from './distribuicao-permissoes.component';
<<<<<<< HEAD
=======
import { RouterModule } from '@angular/router';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CustomMaterialModule } from '../material/material.module';
>>>>>>> f29963daeb03faed6c016ec4aac951bfa0641820

describe('DistribuicaoPermissoesComponent', () => {
  let component: DistribuicaoPermissoesComponent;
  let fixture: ComponentFixture<DistribuicaoPermissoesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
<<<<<<< HEAD
      declarations: [ DistribuicaoPermissoesComponent ]
=======
      declarations: [ DistribuicaoPermissoesComponent ],
      imports: [
        RouterModule.forRoot([]),
        HttpClientTestingModule,
        CustomMaterialModule
      ]
>>>>>>> f29963daeb03faed6c016ec4aac951bfa0641820
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
