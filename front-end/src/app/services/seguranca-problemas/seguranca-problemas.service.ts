import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { LoginService } from '../loginService/login.service';

@Injectable({
  providedIn: 'root'
})
export class SegurancaProblemasService {

  baseUrl = `${environment.URL_BACK}areaSeguranca`;
  constructor(
    private httpClient: HttpClient,
    public loginService: LoginService
    ) { }

  // /lista get
  getListaAreaSeg(codFesta) {

    const httpParams = new HttpParams()
      .append('codFesta', codFesta)
      .append('codUsuario', this.loginService.getusuarioInfo().codUsuario);

    return this.httpClient.get('/lista', {params: httpParams});

  }

  // /areaUnica get param: codUsuario
  getAreaSeg(codFesta, codArea) {

    const httpParams = new HttpParams()
      .append('codFesta', codFesta)
      .append('codArea', codArea)
      .append('codUsuario', this.loginService.getusuarioInfo().codUsuario);

    return this.httpClient.get('/areaUnica', {params: httpParams});
  }

  // model é o objeto criado pelo form
  criarAreaSeg(model) {
    const httpParams = new HttpParams()
      .append('codUsuario', this.loginService.getusuarioInfo().codUsuario);

    return this.httpClient.post('/adicionar', model, {params: httpParams});
  }

  // model é o objeto criado pelo form
  atualizarAreaSeg(model) {
    const httpParams = new HttpParams()
      .append('codUsuario', this.loginService.getusuarioInfo().codUsuario);

    return this.httpClient.put('/atualizar', model, {params: httpParams});
  }

  // /delete delete param: codUsuario
  deletarAreaSeg(codArea) {
    const httpParams = new HttpParams()
      .append('codArea', codArea)
      .append('codUsuario', this.loginService.getusuarioInfo().codUsuario);

    return this.httpClient.delete('/delete', {params: httpParams});
  }

}
