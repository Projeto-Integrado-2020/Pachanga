import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';
import { CustomMaterialModule } from '../material/material.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MAT_DIALOG_DATA } from '@angular/material';
import { of } from 'rxjs';
import { LoginService } from 'src/app/services/loginService/login.service';

import { RelatorioDetalhesDialogComponent } from './relatorio-detalhes-dialog.component';
import { GetHistoricoSegurancaService } from 'src/app/services/get-historico-seguranca/get-historico-seguranca.service';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('RelatorioDetalhesDialogComponent', () => {
  let component: RelatorioDetalhesDialogComponent;
  let fixture: ComponentFixture<RelatorioDetalhesDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RelatorioDetalhesDialogComponent ],
      imports: [
        CustomMaterialModule,
        BrowserAnimationsModule,
        HttpClientTestingModule,
        TranslateModule.forRoot({
          loader: {
            provide: TranslateLoader,
            useFactory: HttpLoaderFactory,
            deps: [HttpClient]
          }
        })
      ],
      providers: [
        { provide: MAT_DIALOG_DATA, useValue: {data: {name: 'teste'}} },
        {provide: GetHistoricoSegurancaService, useValue: {
          getHistorico: () => of([])
        }}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RelatorioDetalhesDialogComponent);
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

  it('should generate relatorioProblemaArea', () => {
    spyOn(component.historicoSeguranca, 'getHistorico')
    .and
    .callThrough();
    component.relatorioProblemaArea();
    expect(component.historicoSeguranca.getHistorico).toHaveBeenCalled();
  });

  it('should generate relatorioProblemaUsuario', () => {
    spyOn(component.historicoSeguranca, 'getHistorico')
    .and
    .callThrough();
    component.chamadasUsuario = [];
    component.relatorioProblemaUsuario();
    expect(component.historicoSeguranca.getHistorico).toHaveBeenCalled();
  });
});
