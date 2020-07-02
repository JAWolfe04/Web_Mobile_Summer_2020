import { Component, OnInit } from '@angular/core';
import {ApiService} from '../services/api.service';

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.scss']
})
export class BookComponent implements OnInit {
  books: any;

  constructor(private api: ApiService) { }

  ngOnInit(): void {
    this.api.getBooks()
      .subscribe(res => {
        this.books = res;
      }, err => {
        console.log(err);
      });
  }

}
