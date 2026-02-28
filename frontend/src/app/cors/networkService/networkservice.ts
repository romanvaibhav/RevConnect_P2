import { Injectable } from '@angular/core';
import { environment } from '../../envi/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class Networkservice {
  static baseUrl = environment.API_HOST_URL;

  constructor(private httpClient: HttpClient) {}

  //Done
  getAllConnection(): Observable<any> {
    const token = localStorage.getItem('token');
    const userId = Number(localStorage.getItem('userId'));
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.httpClient.get(`${Networkservice.baseUrl}/connections/list/${userId}`, { headers });
  }

  //Send connection request to other user
  sendConnectionRequest(receiverId: number): Observable<any> {
    const token = localStorage.getItem('token');
    const userId = Number(localStorage.getItem('userId'));

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.httpClient.post(
      `${Networkservice.baseUrl}/connections/request/${userId}/${receiverId}`,
      {}, // ✅ body (empty)
      { headers }, // ✅ headers go here
    );
  }

  //get all following by user

  getFollowingByUser(): Observable<any> {
    const token = localStorage.getItem('token');
    const userId = Number(localStorage.getItem('userId'));
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.httpClient.get(`${Networkservice.baseUrl}/api/follow/following/${userId}`, {
      headers,
    });
  }

  //Get the followers also
  getFollowersByUser(): Observable<any> {
    const token = localStorage.getItem('token');
    const userId = Number(localStorage.getItem('userId'));
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.httpClient.get(`${Networkservice.baseUrl}/api/follow/followers/${userId}`, {
      headers,
    });
  }

  //Done
  getAllPendingRequests(): Observable<any> {
    const token = localStorage.getItem('token');
    const userId = Number(localStorage.getItem('userId'));
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.httpClient.get(`${Networkservice.baseUrl}/connections/requests/pending/${userId}`, {
      headers,
    });
  }

  //Reject the Request
  rejectConnectionRequest(requestId: number): Observable<any> {
    const token = localStorage.getItem('token');
    const userId = Number(localStorage.getItem('userId'));

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.httpClient.post(
      `${Networkservice.baseUrl}/connections/request/${requestId}/reject/${userId}`,
      {},
      { headers },
    );
  }

  //Accept the Request
  acceptConnectionRequest(requestId: number): Observable<any> {
    const token = localStorage.getItem('token');
    const userId = Number(localStorage.getItem('userId'));

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.httpClient.post(
      `${Networkservice.baseUrl}/connections/request/${requestId}/accept/${userId}`,
      {},
      { headers },
    );
  }

  //Delete the connection Request
  deleteConnectionRequest(connectionId: number): Observable<any> {
    const token = localStorage.getItem('token');
    const userId = Number(localStorage.getItem('userId'));

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.httpClient.delete(
      `${Networkservice.baseUrl}/connections/${userId}/${connectionId}`,
      { headers },
    );
  }

  // /api/follow/unfollow/{followerId}/{followingId}
  unfolllowUser(followingId: number): Observable<any> {
    const token = localStorage.getItem('token');
    const followerId = Number(localStorage.getItem('userId'));

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.httpClient.post(
      `${Networkservice.baseUrl}/api/follow/unfollow/${followingId}/${followerId}`,
      {},
      { headers },
    );
  }
}
