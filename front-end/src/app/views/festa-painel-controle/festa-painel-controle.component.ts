import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';

@Component({
  selector: 'app-festa-painel-controle',
  templateUrl: './festa-painel-controle.component.html',
  styleUrls: ['./festa-painel-controle.component.scss']
})
export class FestaPainelControleComponent implements OnInit {

  options: FormGroup;

  constructor(fb: FormBuilder) {
    this.options = fb.group({
      bottom: 55,
      top: 0
    });
  }

  ngOnInit() {
  }

}
