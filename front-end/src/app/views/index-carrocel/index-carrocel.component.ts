import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-index-carrocel',
  templateUrl: './index-carrocel.component.html',
  styleUrls: ['./index-carrocel.component.scss']
})


export class IndexCarrocelComponent implements OnInit {

  @Input() eventos: any;

  @Input() titulo: string;

  constructor() { }

  ngOnInit() {
    //console.log(this.titulo)
    console.log(this.eventos)
  }

}
