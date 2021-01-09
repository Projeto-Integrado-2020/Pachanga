import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomMaterialModule } from '../material/material.module';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { HttpLoaderFactory } from '../edit-dialog/edit-dialog.component.spec';
import { HttpClient } from '@angular/common/http';
import { LoginService } from 'src/app/services/loginService/login.service';
import { RelatoriosEstoqueComponent } from './relatorios-estoque.component';
import { RelatorioEstoqueService } from 'src/app/services/relatorios/relatorio-estoque.service';
import { of } from 'rxjs';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { OwlModule } from 'ngx-owl-carousel';

describe('RelatoriosEstoqueComponent', () => {
  let component: RelatoriosEstoqueComponent;
  let fixture: ComponentFixture<RelatoriosEstoqueComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RelatoriosEstoqueComponent ],
      imports: [
        CustomMaterialModule,
        HttpClientTestingModule,
        BrowserAnimationsModule,
        ReactiveFormsModule,
        RouterModule.forRoot([]),
        TranslateModule.forRoot({
          loader: {
            provide: TranslateLoader,
            useFactory: HttpLoaderFactory,
            deps: [HttpClient]
          }
        }),
        NgxChartsModule,
        OwlModule
      ],
      providers: [
        {provide: RelatorioEstoqueService, useValue: {
          consumoItemEstoque: () => of([]),
          perdaItemEstoque: () => of([]),
          quantidadeItemEstoque: () => of([]),
          consumoProduto: () => of([])
        }},
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RelatoriosEstoqueComponent);
    component = fixture.componentInstance;
    const service: LoginService = TestBed.get(LoginService);
    service.usuarioInfo = {codUsuario: '1'};
    const token = {
      timeToken: '2020-09-21T01:14:04.028+0000',
      token: 'teste'
    };
    localStorage.setItem('token', JSON.stringify(token));
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get consumoItemEstoque', () => {
    spyOn(component.relEstoqueService, 'consumoItemEstoque')
    .and
    .callThrough();

    component.consumoItemEstoque();
    expect(component.relEstoqueService.consumoItemEstoque).toHaveBeenCalled();
  });

  it('should get perdaItemEstoque', () => {
    spyOn(component.relEstoqueService, 'perdaItemEstoque')
    .and
    .callThrough();

    component.perdaItemEstoque();
    expect(component.relEstoqueService.perdaItemEstoque).toHaveBeenCalled();
  });

  it('should get quantidadeItemEstoque', () => {
    spyOn(component.relEstoqueService, 'quantidadeItemEstoque')
    .and
    .callThrough();

    component.quantidadeItemEstoque();
    expect(component.relEstoqueService.quantidadeItemEstoque).toHaveBeenCalled();
  });

  it('should get consumoProduto', () => {
    spyOn(component.relEstoqueService, 'consumoProduto')
    .and
    .callThrough();

    component.consumoProduto();
    expect(component.relEstoqueService.consumoProduto).toHaveBeenCalled();
  });

  it('should formatDate', () => {
    const result = component.formatDate(new Date('2021-01-09T14:19:49.656018'));
    expect(result).toEqual('49');
  });

  it('should toolTipDate', () => {
    const result = component.toolTipDate(new Date('2021-01-09T14:19:49.656018'));
    expect(result).toEqual('09/01 14:19:49');
  });
});
