import { Injectable } from '@angular/core';
import { environment } from '../../envi/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class Service {
  static baseUrl = environment.API_HOST_URL;

  constructor(private httpClient: HttpClient) {}

  postRegistration(regiForm: any):Observable<any> {
    return this.httpClient.post(`${Service.baseUrl}/auth/register`, regiForm, {
      responseType: 'text', // IMPORTANT
    });
  }

  postLogin(loginForm: any): Observable<any> {
    return this.httpClient.post(`${Service.baseUrl}/auth/login`, loginForm);
  }
}
