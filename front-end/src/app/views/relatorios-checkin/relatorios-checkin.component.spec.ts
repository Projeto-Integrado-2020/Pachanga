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
import { of } from 'rxjs';
import { NgxChartsModule } from '@swimlane/ngx-charts';

import { RelatoriosCheckinComponent } from './relatorios-checkin.component';
import { RelatorioCheckinService } from 'src/app/services/relatorios/relatorio-checkin.service';

fdescribe('RelatoriosCheckinComponent', () => {
  let component: RelatoriosCheckinComponent;
  let fixture: ComponentFixture<RelatoriosCheckinComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RelatoriosCheckinComponent ],
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
        NgxChartsModule
      ],
      providers: [
        {provide: RelatorioCheckinService, useValue: {
          faixaEtaria: () => of({quantitadeFaixaEtaria: {}}),
          genero: () => of({quantidadeGenero: {}}),
          checkedUnchecked: () => of({ingressoFestaCheckedUnchecked: {}}),
          qtdEntradasHora: () => of({quantidadePessoasHora: {}})
        }},
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RelatoriosCheckinComponent);
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

  it('should get faixaEtaria', () => {
    spyOn(component.relatorioCheckin, 'faixaEtaria')
    .and
    .callThrough();

    component.faixaEtaria();
    expect(component.relatorioCheckin.faixaEtaria).toHaveBeenCalled();
  });

  it('should get generos', () => {
    spyOn(component.relatorioCheckin, 'genero')
    .and
    .callThrough();

    component.generos();
    expect(component.relatorioCheckin.genero).toHaveBeenCalled();
  });

  it('should get ingressosFestaCheckedUnchecked', () => {
    spyOn(component.relatorioCheckin, 'checkedUnchecked')
    .and
    .callThrough();

    component.ingressosFestaCheckedUnchecked();
    expect(component.relatorioCheckin.checkedUnchecked).toHaveBeenCalled();
  });

  it('should get quantidadeEntradasHora', () => {
    spyOn(component.relatorioCheckin, 'qtdEntradasHora')
    .and
    .callThrough();

    component.quantidadeEntradasHora();
    expect(component.relatorioCheckin.qtdEntradasHora).toHaveBeenCalled();
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
