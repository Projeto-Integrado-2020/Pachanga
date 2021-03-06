import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { GetFestaService } from './get-festa.service';
import { RouterModule } from '@angular/router';
import { CustomMaterialModule } from '../../views/material/material.module';

describe('GetFestaService', () => {
  let service: GetFestaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        RouterModule.forRoot([]),
        CustomMaterialModule
      ]
    });
    const token = {
      timeToken: '2020-09-21T01:14:04.028+0000',
      token: 'teste'
    };
    localStorage.setItem('token', JSON.stringify(token));
    service = TestBed.get(GetFestaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get Info at acessarFesta', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    expect(service.acessarFesta('teste')).toBeTruthy();
  });
});
