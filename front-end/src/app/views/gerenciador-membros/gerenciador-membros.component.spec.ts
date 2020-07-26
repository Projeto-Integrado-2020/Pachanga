import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CustomMaterialModule } from '../material/material.module';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { MatDialog } from '@angular/material';
import { LoginService } from 'src/app/services/loginService/login.service';
import { GerenciadorMembrosComponent } from './gerenciador-membros.component';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

describe('GerenciadorMembrosComponent', () => {
  let component: GerenciadorMembrosComponent;
  let fixture: ComponentFixture<GerenciadorMembrosComponent>;
  let dialogSpy: MatDialog;

  beforeEach(async(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);
    TestBed.configureTestingModule({
      declarations: [ GerenciadorMembrosComponent ],
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
      ],
      providers: [
        { provide: MatDialog, useValue: dialogSpy },
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GerenciadorMembrosComponent);
    component = fixture.componentInstance;
    const service: LoginService = TestBed.get(LoginService);
    service.usuarioInfo = {codUsuario: '1'};
    component.festa = {codFesta: '1'};
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should open a invite dialog through a method', () => {
    component.festa = {codFesta: '1'};
    component.openDialogInvite('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });
});
