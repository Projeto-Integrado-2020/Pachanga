import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { LogService } from '../logging/log.service';
import { LoginService } from '../loginService/login.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CheckInService {

  farol = false;
  private readonly urlCheckIn = `${environment.URL_BACK}ingresso/updateCheckin`;

  constructor(private http: HttpClient, public logService: LogService,
              public loginService: LoginService) { }

  checkInIngresso(codIngresso, codFesta) {
    if (!this.farol) {
      this.setFarol(true);
      const httpParams = new HttpParams()
        .append('codUsuario', this.loginService.usuarioInfo.codUsuario)
        .append('codIngresso', codIngresso)
        .append('codFesta', codFesta);

      let headers = new HttpHeaders();
      headers = headers.append('Content-Type', 'application/json');
      headers = headers.append('Authorization', 'Bearer ' + JSON.parse(localStorage.getItem('token')).token);

      return this.http.put(this.urlCheckIn, null, {params: httpParams, headers});
    }
    return new Observable<never>();
  }

  setFarol(flag: boolean) {
    this.farol = flag;
  }

  getFarol() {
    return this.farol;
  }
}
