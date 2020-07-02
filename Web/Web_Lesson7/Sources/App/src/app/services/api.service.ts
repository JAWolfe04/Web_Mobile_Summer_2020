import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http: HttpClient, private router: Router) {
  }

  getBooks(): Observable<any> {
    return this.http.get(`${environment.serverURL}/getBooks`);
  }

  getBook(id: string): Observable<any> {
    return this.http.get(`${environment.serverURL}/getBook/${id}`);
  }

  createBook(data) {
    this.http.post(`${environment.serverURL}/createBook`, data).subscribe((res: any) => {
      this.router.navigate(['/book-details', res._id]);
    });
  }

  updateBook(id: string, data) {
    this.http.put(`${environment.serverURL}/updateBook/${id}`, data).subscribe((res: any) => {
      this.router.navigate(['/book-details', res._id]);
    });
  }

  deleteBook(id: string) {
    console.log(id);
    this.http.delete(`${environment.serverURL}/deleteBook/${id}`)
      .subscribe(() => { this.router.navigate(['/books']); });
  }
}
