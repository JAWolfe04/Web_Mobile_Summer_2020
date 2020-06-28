import { Component, OnInit } from '@angular/core';
import { SearchServiceService } from '../services/search-service.service';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-search-restaurant',
  templateUrl: './search-restaurant.component.html',
  styleUrls: ['./search-restaurant.component.css']
})
export class SearchRestaurantComponent implements OnInit {
  recipeValue: any;
  placeValue: any;
  venueList = [];

  currentLat: any;
  currentLong: any;
  geolocationPosition: any;

  constructor(private http: HttpClient, private search_service: SearchServiceService) { }

  ngOnInit() {
    window.navigator.geolocation.getCurrentPosition(
      position => {
        this.geolocationPosition = position;
        this.currentLat = position.coords.latitude;
        this.currentLong = position.coords.longitude;
      });

    this.search_service.currentRecipe.subscribe(recipe => this.recipeValue = recipe);
  }

  setRecipes() {
    this.search_service.setRecipe(this.recipeValue);
  }

  getVenues() {
    if (this.placeValue != null && this.placeValue !== '' && this.recipeValue != null && this.recipeValue !== '') {
      this.http.get('https://api.foursquare.com/v2/venues/search?query=' + this.recipeValue +
        '&near=' + this.placeValue +
        '&limit=5' +
        '&client_id=3JP2T4W4MWPI5EWKSBGOVPAMDFSYF5B5JQPRR1QZPV0V3AB0' +
        '&client_secret=NTQLQRBVG30UQA1QHZ33WEHBJMD53Z32RXQ1PLRMHHTTFTSJ' +
        '&v=20200626')
        .subscribe((data: any) => { this.venueList = data.response.venues; });
    }
  }
}
