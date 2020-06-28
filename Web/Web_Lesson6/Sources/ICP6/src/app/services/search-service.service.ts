import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SearchServiceService {
  private recipe = new BehaviorSubject<string>('');
  currentRecipe = this.recipe.asObservable();

  constructor() { }

  setRecipe(entry) {
    this.recipe.next(entry);
  }
}
