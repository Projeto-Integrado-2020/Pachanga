import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { FormControl, Validators} from '@angular/forms';
import { LoginService } from 'src/app/services/loginService/login.service';

@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.scss']
})
export class PerfilComponent implements OnInit {

  constructor(public loginService: LoginService) {
  }

  ngOnInit() {
  }

}
