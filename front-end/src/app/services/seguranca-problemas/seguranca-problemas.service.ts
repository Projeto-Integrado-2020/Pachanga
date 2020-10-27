import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { LoginService } from '../loginService/login.service';

@Injectable({
  providedIn: 'root'
})
export class SegurancaProblemasService {

  baseUrl = `${environment.URL_BACK}areaSegurancaProblema`;
  constructor(
    private httpClient: HttpClient,
    public loginService: LoginService
    ) { }

  adicionarProblema(problemaTO) {

    const httpParams = new HttpParams()
      .append('idUsuario', this.loginService.getusuarioInfo().idUsuario);

    return this.httpClient.post('/adicionar', problemaTO, {params: httpParams});
  }

  atualizarProblema(problemaTO) {
    const httpParams = new HttpParams()
      .append('idUsuario', this.loginService.getusuarioInfo().idUsuario);

    return this.httpClient.put('/atualizar', problemaTO, {params: httpParams});
  }

  deletarProblema(codArea, codFesta, codProblema) {
    const httpParams = new HttpParams()
      .append('codAreaSeguranca', codArea)
      .append('codProblema', codProblema)
      .append('codFesta', codFesta)
      .append('idUsuario', this.loginService.getusuarioInfo().codUsuario);

    return this.httpClient.delete('/delete', {params: httpParams});
  }

  findAllProblema(codArea, codFesta) {

    const httpParams = new HttpParams()
      .append('codAreaSeguranca', codArea)
      .append('codFesta', codFesta)
      .append('idUsuario', this.loginService.getusuarioInfo().codUsuario);

    return this.httpClient.get('/lista', {params: httpParams});

  }

  // /areaUnica get param: idUsuario
  getProblema(codProblema, codArea, codFesta) {

    const httpParams = new HttpParams()
      .append('codFesta', codFesta)
      .append('codProblema', codProblema)
      .append('codAreaSeguranca', codArea)
      .append('idUsuario', this.loginService.getusuarioInfo().codUsuario);

    return this.httpClient.get('/areaUnica', {params: httpParams});
  }

  // /lista get
  getAllProblemasArea(codArea, codFesta) {

    const httpParams = new HttpParams()
      .append('codAreaSeguranca', codArea)
      .append('codFesta', codFesta)
      .append('idUsuario', this.loginService.getusuarioInfo().codUsuario);

    return this.httpClient.get('/lista', {params: httpParams});

  }

    // /lista get
    getAllProblemasFesta(codFesta) {

      const httpParams = new HttpParams()
        .append('codFesta', codFesta)
        .append('idUsuario', this.loginService.getusuarioInfo().codUsuario);

      return this.httpClient.get('/lista', {params: httpParams});

    }

}
