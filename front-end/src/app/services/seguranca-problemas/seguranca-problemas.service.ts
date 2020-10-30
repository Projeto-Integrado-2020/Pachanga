import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { LoginService } from '../loginService/login.service';

@Injectable({
  providedIn: 'root'
})
export class SegurancaProblemasService {

  baseUrl = `${environment.URL_BACK}areaSegurancaProblema`;
  listaProblemas = `${environment.URL_BACK}problema/lista`;

  constructor(
    private httpClient: HttpClient,
    public loginService: LoginService
    ) { }

  listarProblemas() {

    let headers = new HttpHeaders();
    headers = headers.append('Content-Type', 'application/json');
    headers = headers.append('Authorization', 'Bearer ' + JSON.parse(localStorage.getItem('token')).token);

    return this.httpClient.get(this.listaProblemas, {headers});
  }

  adicionarProblema(problemaTO) {

    let headers = new HttpHeaders();
    headers = headers.append('Content-Type', 'application/json');
    headers = headers.append('Authorization', 'Bearer ' + JSON.parse(localStorage.getItem('token')).token);

    const httpParams = new HttpParams()
      .append('idUsuario', this.loginService.getusuarioInfo().codUsuario);

    return this.httpClient.post(this.baseUrl + '/adicionar', problemaTO, {headers, params: httpParams});
  }

  atualizarProblema(problemaTO) {
    const httpParams = new HttpParams()
      .append('idUsuario', this.loginService.getusuarioInfo().codUsuario);

    return this.httpClient.put(this.baseUrl + '/atualizar', problemaTO, {params: httpParams});
  }

  deletarProblema(codArea, codFesta, codProblema) {
    const httpParams = new HttpParams()
      .append('codAreaSeguranca', codArea)
      .append('codProblema', codProblema)
      .append('codFesta', codFesta)
      .append('idUsuario', this.loginService.getusuarioInfo().codUsuario);

    return this.httpClient.delete(this.baseUrl + '/delete', {params: httpParams});
  }

  findAllProblema(codArea, codFesta) {

    const httpParams = new HttpParams()
      .append('codAreaSeguranca', codArea)
      .append('codFesta', codFesta)
      .append('idUsuario', this.loginService.getusuarioInfo().codUsuario);

    return this.httpClient.get(this.baseUrl + '/lista', {params: httpParams});

  }

  // /areaUnica get param: idUsuario
  getProblema(codProblema, codArea, codFesta) {

    const httpParams = new HttpParams()
      .append('codFesta', codFesta)
      .append('codProblema', codProblema)
      .append('codAreaSeguranca', codArea)
      .append('idUsuario', this.loginService.getusuarioInfo().codUsuario);

    return this.httpClient.get(this.baseUrl + '/areaUnica', {params: httpParams});
  }

  // /lista get
  getAllProblemasArea(codArea, codFesta) {

    let headers = new HttpHeaders();
    headers = headers.append('Content-Type', 'application/json');
    headers = headers.append('Authorization', 'Bearer ' + JSON.parse(localStorage.getItem('token')).token);

    const httpParams = new HttpParams()
      .append('codAreaSeguranca', codArea)
      .append('codFesta', codFesta)
      .append('idUsuario', this.loginService.getusuarioInfo().codUsuario);

    return this.httpClient.get(this.baseUrl + '/findAllProblemasSegurancaArea', {headers, params: httpParams});

  }

    // /lista get
    getAllProblemasFesta(codFesta) {

      const httpParams = new HttpParams()
        .append('codFesta', codFesta)
        .append('idUsuario', this.loginService.getusuarioInfo().codUsuario);

      return this.httpClient.get(this.baseUrl + '/lista', {params: httpParams});

    }

}
