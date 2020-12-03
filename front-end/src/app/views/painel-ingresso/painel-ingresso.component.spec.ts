import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PainelIngressoComponent } from './painel-ingresso.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { HttpClient } from '@angular/common/http';
import { CustomMaterialModule } from '../material/material.module';
import { RouterModule } from '@angular/router';
import { LoginService } from 'src/app/services/loginService/login.service';
import { FormsModule } from '@angular/forms';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';
import { of } from 'rxjs';
import { GetLoteService } from 'src/app/services/get-lote/get-lote.service';
import { ControleSidenavComponent } from '../controle-sidenav/controle-sidenav.component';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('PainelIngressoComponent', () => {
  let component: PainelIngressoComponent;
  let fixture: ComponentFixture<PainelIngressoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ 
        PainelIngressoComponent,
        ControleSidenavComponent
      ],
      imports: [
        CustomMaterialModule,
        HttpClientTestingModule,
        BrowserAnimationsModule,
        FormsModule,
        ReactiveFormsModule,
        TranslateModule.forRoot({
          loader: {
            provide: TranslateLoader,
            useFactory: HttpLoaderFactory,
            deps: [HttpClient]
          }
        }),
        RouterModule.forRoot([]),
      ],
      providers: [
        {provide: GetFestaService, useValue: {
          acessarFesta: () => of({
          }),
          setFarol: () => false,
        }},
        {provide: GetLoteService, useValue: {
          getLote: () => of([{
            codLote: '1',
            codFesta: '1',
            horarioInicio: '2020-09-23T19:10:25',
            horarioFim: '2020-09-23T19:10:25'
          }]),
          setFarol: () => false,
        }}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PainelIngressoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    component = fixture.componentInstance;
    const service: LoginService = TestBed.get(LoginService);
    service.usuarioInfo = {codUsuario: '1'};
    component.festa = {codFesta: '1'};
    component.lotes = [{codLote: '1', codFesta: '1', horarioInicio: '2020-09-23T19:10:25', horarioFim: '2020-09-23T19:10:25'}];
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

  it('should format date from datetime', () => {
    const result = component.getDateFromDTF('2020-09-23T19:10:25');
    expect(result).toBe('23/09/2020');
  });

  it('should formar time from datetime', () => {
    const result = component.getTimeFromDTF('2020-09-23T19:10:25');
    expect(result).toBe('19:10:25');
  });

  it('should create url with batch id', () => {
    const result = component.createUrlEditLote('1');
    expect(result).toBe('../ingressos/editar-lote/1');
  });

});
