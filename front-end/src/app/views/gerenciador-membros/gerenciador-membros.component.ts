import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-gerenciador-membros',
  templateUrl: './gerenciador-membros.component.html',
  styleUrls: ['./gerenciador-membros.component.css']
})
export class GerenciadorMembrosComponent implements OnInit {

  constructor(public formBuilder: FormBuilder, public getFestaService: GetFestaService) { }

  ngOnInit(): void {
  }

}
