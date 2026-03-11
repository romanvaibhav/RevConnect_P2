import { Injectable } from '@angular/core';
import { environment } from '../../envi/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Postservice } from '../postService/postservice';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class Profileservice {
  static baseUrl = environment.API_HOST_URL;

  constructor(private httpClient: HttpClient) {}

  getUserProfile(): Observable<any> {
    const token = localStorage.getItem('token');
    const userId = localStorage.getItem('userId');
    console.log(token);
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.httpClient.get(`${Postservice.baseUrl}/user/userProfile/${userId}`, { headers });
  }

  getUserProfileById(userId: number): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.httpClient.get(`${Postservice.baseUrl}/user/userProfile/${userId}`, { headers });
  }

  getUserPosts(): Observable<any> {
    const token = localStorage.getItem('token');
    const userId = localStorage.getItem('userId');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.httpClient.get(`${Postservice.baseUrl}/post/userposts/${userId}`, { headers });
  }

  //create user profile
  createProfile(profile: any): Observable<any> {
    const token = localStorage.getItem('token');
    const userId = localStorage.getItem('userId');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.httpClient.post(`${Postservice.baseUrl}/user/profile/${userId}`, profile, {
      headers,
    });
  }

  updateProfile(profile: any): Observable<any> {
    const token = localStorage.getItem('token');
    const userId = localStorage.getItem('userId');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.httpClient.patch(`${Postservice.baseUrl}/user/updprofile/${userId}`, profile, {
      headers,
    });
  }

  //Search User

  searchUserProfiles(name: String): Observable<any> {
    const token = localStorage.getItem('token');
    const userId = localStorage.getItem('userId');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.httpClient.get(`${Postservice.baseUrl}/user/searchUser?name=${name}`, { headers });
  }
}
