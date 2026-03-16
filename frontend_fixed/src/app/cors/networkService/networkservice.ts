import { Injectable } from '@angular/core';
import { environment } from '../../envi/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class Networkservice {
  static baseUrl = environment.API_HOST_URL;

  constructor(private httpClient: HttpClient) {}

  private get headers(): HttpHeaders {
    return new HttpHeaders({ Authorization: `Bearer ${localStorage.getItem('token')}` });
  }

  private get userId(): number {
    return Number(localStorage.getItem('userId'));
  }

  getAllConnection(): Observable<any> {
    return this.httpClient.get(`${Networkservice.baseUrl}/connections/list/${this.userId}`, { headers: this.headers });
  }

  sendConnectionRequest(receiverId: number): Observable<any> {
    return this.httpClient.post(`${Networkservice.baseUrl}/connections/request/${this.userId}/${receiverId}`, {}, { headers: this.headers });
  }

  getAllPendingRequests(): Observable<any> {
    return this.httpClient.get(`${Networkservice.baseUrl}/connections/requests/pending/${this.userId}`, { headers: this.headers });
  }

  acceptConnectionRequest(requestId: number): Observable<any> {
    return this.httpClient.post(`${Networkservice.baseUrl}/connections/request/${requestId}/accept/${this.userId}`, {}, { headers: this.headers });
  }

  rejectConnectionRequest(requestId: number): Observable<any> {
    return this.httpClient.post(`${Networkservice.baseUrl}/connections/request/${requestId}/reject/${this.userId}`, {}, { headers: this.headers });
  }

  deleteConnectionRequest(connectionId: number): Observable<any> {
    return this.httpClient.delete(`${Networkservice.baseUrl}/connections/${this.userId}/${connectionId}`, { headers: this.headers });
  }

  getFollowingByUser(): Observable<any> {
    return this.httpClient.get(`${Networkservice.baseUrl}/api/follow/following/${this.userId}`, { headers: this.headers });
  }

  getFollowersByUser(): Observable<any> {
    return this.httpClient.get(`${Networkservice.baseUrl}/api/follow/followers/${this.userId}`, { headers: this.headers });
  }

  followUser(followingId: number): Observable<any> {
    return this.httpClient.post(`${Networkservice.baseUrl}/api/follow/follow/${this.userId}/${followingId}`, {}, { headers: this.headers });
  }

  // FIX: correct order - followerId first, then followingId
  unfollowUser(followingId: number): Observable<any> {
    return this.httpClient.delete(`${Networkservice.baseUrl}/api/follow/unfollow/${this.userId}/${followingId}`, { headers: this.headers });
  }
}
