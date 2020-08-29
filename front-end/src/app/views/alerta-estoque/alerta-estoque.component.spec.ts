import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CustomMaterialModule } from '../../views/material/material.module';
import { MAT_DIALOG_DATA } from '@angular/material';
import { AlertaEstoqueComponent } from './alerta-estoque.component';
import { RouterModule } from '@angular/router';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('AlertaEstoqueComponent', () => {
  let component: AlertaEstoqueComponent;
  let fixture: ComponentFixture<AlertaEstoqueComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AlertaEstoqueComponent ],
      imports: [
        CustomMaterialModule,
        TranslateModule.forRoot({
          loader: {
            provide: TranslateLoader,
            useFactory: HttpLoaderFactory,
            deps: [HttpClient]
          }
        }),
        RouterModule.forRoot([]),
        HttpClientTestingModule
      ],
      providers: [
        { provide: MAT_DIALOG_DATA,
          useValue: {
            alerta: { notificacaoEstoque: {
              nomeFesta: 'teste', marca: 'teste', nomeEstoque: 'teste', qtdAtual: 'teste'},
              mensagem: 'TESTE?testeCod7&testeCod8'
            }
          }
        },
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AlertaEstoqueComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should getUrlFesta', () => {
    expect(component.getUrlFesta()).toEqual('../festas/teste&testeCod7/estoque');
  });
});
