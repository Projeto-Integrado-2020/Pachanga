import { Component, OnInit } from '@angular/core';
import { SocialLoginBaseComponent } from '../social-login-base/social-login-base.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: [
    './login.component.scss',
    '../../../../node_modules/font-awesome/css/font-awesome.css'
  ]
})

export class LoginComponent extends SocialLoginBaseComponent implements OnInit {
}
