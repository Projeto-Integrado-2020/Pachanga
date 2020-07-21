import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IndexComponent } from './index.component';

import { CustomMaterialModule } from '../material/material.module';
import { AppRoutingModule } from '../../app-routing.module';
import { LoginComponent } from '../login/login.component';
import { CadastroComponent } from '../cadastro/cadastro.component';

import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PerfilComponent } from '../perfil/perfil.component';
import { InfoCompleteComponent } from '../info-complete/info-complete.component';
import { MenuFestasComponent } from '../menu-festas/menu-festas.component';
import { FestaPainelControleComponent } from '../festa-painel-controle/festa-painel-controle.component';
import { CriarFestaComponent } from '../criar-festa/criar-festa.component';
import { EditarFestaComponent } from '../editar-festa/editar-festa.component';
import { NotFoundComponent } from '../not-found/not-found.component';

import { NgxMaterialTimepickerModule } from 'ngx-material-timepicker';
import { LoginService } from 'src/app/services/loginService/login.service';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('IndexComponent', () => {
  let component: IndexComponent;
  let fixture: ComponentFixture<IndexComponent>;

  beforeEach(async(() => {
    jasmine.DEFAULT_TIMEOUT_INTERVAL = 100000;
    TestBed.configureTestingModule({
      declarations: [
        IndexComponent,
        LoginComponent,
        CadastroComponent,
        PerfilComponent,
        InfoCompleteComponent,
        MenuFestasComponent,
        FestaPainelControleComponent,
        CriarFestaComponent,
        EditarFestaComponent,
        NotFoundComponent
      ],
      imports: [
        CustomMaterialModule,
        NgxMaterialTimepickerModule,
        AppRoutingModule,
        HttpClientTestingModule,
        ReactiveFormsModule,
        FormsModule,
        TranslateModule.forRoot({
          loader: {
            provide: TranslateLoader,
            useFactory: HttpLoaderFactory,
            deps: [HttpClient]
          }
        }),
      ],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    jasmine.DEFAULT_TIMEOUT_INTERVAL = 100000;
    fixture = TestBed.createComponent(IndexComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    const service: LoginService = TestBed.get(LoginService);
    service.usuarioInfo = {nomeUser: 'Teste', nomesexo: null, dtNasc: null};
    expect(component).toBeTruthy();
  });
});
