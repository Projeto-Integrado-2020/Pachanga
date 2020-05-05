import { Component, OnInit } from '@angular/core';



export interface Tile {
  cols: number;
  rows: number;
  text: string;
 }


@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.scss']
})
export class IndexComponent implements OnInit {


  tiles: Tile[] = [
    {text: 'Bem Vindo à Pachanga', cols: 2, rows: 1, },
    {text: '', cols: 1, rows: 1}
  ];

  constructor() { }

  ngOnInit() {
  }

}
