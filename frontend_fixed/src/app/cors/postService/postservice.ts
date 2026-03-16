import { Injectable } from '@angular/core';
import { environment } from '../../envi/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class Postservice {
  static baseUrl = environment.API_HOST_URL;

  constructor(private httpClient: HttpClient) {}

  private get headers(): HttpHeaders {
    return new HttpHeaders({ Authorization: `Bearer ${localStorage.getItem('token')}` });
  }

  private get userId(): number {
    return Number(localStorage.getItem('userId'));
  }

  getAllPosts(): Observable<any> {
    return this.httpClient.get(`${Postservice.baseUrl}/post/getposts`, { headers: this.headers });
  }

  getAllPromotionalPosts(): Observable<any> {
    return this.httpClient.get(`${Postservice.baseUrl}/api/posts`, { headers: this.headers });
  }

  getUserPosts(userId?: number): Observable<any> {
    const uid = userId ?? this.userId;
    return this.httpClient.get(`${Postservice.baseUrl}/post/userposts/${uid}`, { headers: this.headers });
  }

  createPosts(postData: any): Observable<any> {
    return this.httpClient.post(`${Postservice.baseUrl}/post/create/${this.userId}`, postData, { headers: this.headers });
  }

  updatePost(postId: number, data: any): Observable<any> {
    return this.httpClient.patch(`${Postservice.baseUrl}/post/update/${postId}`, data, { headers: this.headers });
  }

  deletePost(postId: number): Observable<any> {
    return this.httpClient.delete(`${Postservice.baseUrl}/post/delete/${postId}`, { headers: this.headers, responseType: 'text' });
  }

  postLike(postId: number): Observable<any> {
    return this.httpClient.post(`${Postservice.baseUrl}/post/${postId}/like/${this.userId}`, {}, { headers: this.headers, responseType: 'text' });
  }

  postUnlike(postId: number): Observable<any> {
    return this.httpClient.delete(`${Postservice.baseUrl}/post/${postId}/unlike/${this.userId}`, { headers: this.headers, responseType: 'text' });
  }

  addComment(commentData: any): Observable<any> {
    return this.httpClient.post(`${Postservice.baseUrl}/comment`, commentData, { headers: this.headers });
  }

  getCommentsByPost(postId: number): Observable<any> {
    return this.httpClient.get(`${Postservice.baseUrl}/comment/post/${postId}`, { headers: this.headers });
  }

  deleteComment(commentId: number): Observable<any> {
    return this.httpClient.delete(`${Postservice.baseUrl}/comment/${commentId}/${this.userId}`, { headers: this.headers, responseType: 'text' });
  }

  createShare(postId: number, commentaryText?: string): Observable<any> {
    const body = { originalPostId: postId, sharedByUserId: this.userId, commentaryText: commentaryText || null };
    return this.httpClient.post(`${Postservice.baseUrl}/shares`, body, { headers: this.headers });
  }

  getSharesByUser(userId?: number): Observable<any> {
    const uid = userId ?? this.userId;
    return this.httpClient.get(`${Postservice.baseUrl}/shares/user/${uid}`, { headers: this.headers });
  }

  getPinnedPosts(): Observable<any> {
    return this.httpClient.get(`${Postservice.baseUrl}/post/pinned/${this.userId}`, { headers: this.headers });
  }

  pinPost(postId: number): Observable<any> {
    return this.httpClient.post(`${Postservice.baseUrl}/post/pinned/pin/${this.userId}/${postId}`, {}, { headers: this.headers });
  }

  unpinPost(postId: number): Observable<any> {
    return this.httpClient.delete(`${Postservice.baseUrl}/post/pinned/unpin/${this.userId}/${postId}`, { headers: this.headers, responseType: 'text' });
  }

  getPostAnalytics(postId: number): Observable<any> {
    return this.httpClient.get(`${Postservice.baseUrl}/post/${postId}/analytics`, { headers: this.headers });
  }

  addHashtag(tag: string, postId: number): Observable<any> {
    return this.httpClient.post(`${Postservice.baseUrl}/hashtags/add`, { tag, postId }, { headers: this.headers });
  }

  getHashtagsByPost(postId: number): Observable<any> {
    return this.httpClient.get(`${Postservice.baseUrl}/hashtags/post/${postId}`, { headers: this.headers });
  }
}
