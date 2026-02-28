import { Injectable } from '@angular/core';
import { environment } from '../../envi/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class Postservice {
  static baseUrl = environment.API_HOST_URL;

  constructor(private httpClient: HttpClient) {}

  //Get all the posts
  getAllPosts(): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.httpClient.get(`${Postservice.baseUrl}/post/getposts`, { headers });
  }

  //get all the promotional posts
  getAllPromotionalPosts(): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.httpClient.get(`${Postservice.baseUrl}/api/posts`, { headers });
  }

  postLike(postId: number): Observable<any> {
    const token = localStorage.getItem('token');
    const userId = Number(localStorage.getItem('userId'));
    console.log('Printing userId hehehe', userId);
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
    return this.httpClient.post(
      `${Postservice.baseUrl}/post/${postId}/like/${userId}`,
      {},
      { headers },
    );
  }

  postUnlike(postId: number): Observable<any> {
    const token = localStorage.getItem('token');
    const userId = Number(localStorage.getItem('userId'));
    console.log('Printing userId hehehe', userId);
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
    return this.httpClient.delete(`${Postservice.baseUrl}/post/${postId}/unlike/${userId}`, {
      headers,
    });
  }

  //Create post
  createPosts(postData: any): Observable<any> {
    const token = localStorage.getItem('token');
    const userId = Number(localStorage.getItem('userId'));
    console.log('Printing userId hehehe', userId);
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
    return this.httpClient.post(`${Postservice.baseUrl}/post/create/${userId}`, postData, {
      headers,
    });
  }

  // Add comment
  addComment(commentData: any): Observable<any> {
    const token = localStorage.getItem('token');
    const userId = Number(localStorage.getItem('userId'));
    console.log('Printing userId hehehe', userId);
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
    return this.httpClient.post(`${Postservice.baseUrl}/comment`, commentData, {
      headers,
    });
  }

  getCommentsByPost(postId: any): Observable<any> {
    const token = localStorage.getItem('token');
    const userId = Number(localStorage.getItem('userId'));
    console.log('Printing userId hehehe', userId);
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
    return this.httpClient.get(`${Postservice.baseUrl}/comment/post/${postId}`, {
      headers,
    });
  }

  deleteComment(commentId: any): Observable<any> {
    const token = localStorage.getItem('token');
    const userId = Number(localStorage.getItem('userId'));
    console.log('Printing userId hehehe', userId);
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
    return this.httpClient.delete(`${Postservice.baseUrl}/comment/${commentId}/${userId}`, {
      headers,
    });
  }

  //Create shared post 
  createShare(postId: any, commentaryText?: string): Observable<any> {
  const token = localStorage.getItem('token');
  const userId = Number(localStorage.getItem('userId'));

  console.log('Printing userId hehehe', userId);

  const headers = new HttpHeaders({
    Authorization: `Bearer ${token}`,
  });

  const body = {
    originalPostId: postId,
    sharedByUserId: userId,
    commentaryText: commentaryText || null
  };

  return this.httpClient.post(
    `${Postservice.baseUrl}/shares`,
    body,
    { headers }
  );
}



//Share Post
getSharesByUser(userId: any): Observable<any> {
  const token = localStorage.getItem('token');

  const headers = new HttpHeaders({
    Authorization: `Bearer ${token}`,
  });

  return this.httpClient.get(
    `${Postservice.baseUrl}/shares/user/${userId}`,
    { headers }
  );
}


//Unshare post
deleteShare(shareId: any): Observable<any> {
  const token = localStorage.getItem('token');

  const headers = new HttpHeaders({
    Authorization: `Bearer ${token}`,
  });

  return this.httpClient.delete(
    `${Postservice.baseUrl}/shares/${shareId}`,
    { headers }
  );
}


  
}
