import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SearchRestaurantComponent } from './search-restaurant/search-restaurant.component';
import { SearchRecipeComponent } from './search-recipe/search-recipe.component';

const appRoutes: Routes = [
  { path: '', redirectTo: 'search-recipe', pathMatch: 'full' },
  { path: 'search-restaurant', component: SearchRestaurantComponent},
  { path: 'search-recipe', component: SearchRecipeComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
