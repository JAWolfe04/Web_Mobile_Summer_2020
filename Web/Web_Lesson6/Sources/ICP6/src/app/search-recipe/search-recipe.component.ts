import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SearchServiceService } from '../services/search-service.service';

@Component({
  selector: 'app-search-recipe',
  templateUrl: './search-recipe.component.html',
  styleUrls: ['./search-recipe.component.css']
})
export class SearchRecipeComponent implements OnInit {
  recipeValue: any;
  recipeList = [];

  constructor(private http: HttpClient, private search_service: SearchServiceService) {
  }

  ngOnInit() {
    this.search_service.currentRecipe.subscribe(recipe => this.recipeValue = recipe);
  }

  setRecipes() {
    this.search_service.setRecipe(this.recipeValue);
  }

  getRecipes() {
    if (this.recipeValue !== null) {
      this.http.get( 'https://api.edamam.com/search?q=' + this.recipeValue +
        '&app_id=0239d0f5' +
        '&app_key=715aa1fab9ecb1adc0b57f836b4d155d')
        .subscribe((data: any) => { this.recipeList = data.hits; });
    }
  }
}
