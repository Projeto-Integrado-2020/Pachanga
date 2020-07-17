import { Component, OnInit } from '@angular/core';
import {TranslateService} from '@ngx-translate/core';

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

  title: string = this.translate.instant('INDEX.OLA');

  constructor(private translate: TranslateService) {
  }

  tiles: Tile[] = [
    {text: this.title, cols: 3, rows: 1, },
    {text: null, cols: 1, rows: 1}
  ];

  ngOnInit() {
  }

}
