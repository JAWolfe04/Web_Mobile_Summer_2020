import {Component, OnInit, ViewEncapsulation} from '@angular/core';

@Component({
  selector: 'app-todo',
  templateUrl: './todo.component.html',
  styleUrls: ['./todo.component.scss'],
  encapsulation : ViewEncapsulation.Emulated
})
export class TodoComponent implements OnInit {

  // define list of items
  items = [];
  submission: string;

  constructor() { }

  ngOnInit(): void {
    document.body.style.backgroundImage = '';
    document.body.style.backgroundColor = 'white';
  }

  // Write code to push new item
  submitNewItem() {
    this.items.push([this.submission, false]);
  }

  // Write code to complete item
  completeItem(index) {
    this.items[index][1] = true;
  }

  // Write code to delete item
  deleteItem(index) {
    this.items.splice(index, 1);
  }
}
