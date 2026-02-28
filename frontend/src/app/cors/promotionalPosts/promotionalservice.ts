import { Injectable } from '@angular/core';
import { environment } from '../../envi/environment';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class Promotionalservice {
  static baseUrl = environment.API_HOST_URL;

  constructor(private httpClient: HttpClient) {}

  // Get Promotional Posts
  getUserProfile(): Observable<any> {
    const token = localStorage.getItem('token');
    const userId = localStorage.getItem('userId');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.httpClient.get(`${Promotionalservice.baseUrl}/api/posts`, {
      headers,
    });
  }

  // Creating Promotional Post
  createPromotionalPost(
    businessProfileId: number,
    content: string,
    imageUrl: string,
    productIds: number[],
    ctaType: string,
    ctaUrl: string,
  ): Observable<any> {
    const token = localStorage.getItem('token');

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    let params = new HttpParams()
      .set('businessProfileId', businessProfileId.toString())
      .set('content', content)
      .set('imageUrl', imageUrl)
      .set('ctaType', ctaType)
      .set('ctaUrl', ctaUrl);

    // IMPORTANT: For List<Long> productIds
    productIds.forEach((id) => {
      params = params.append('productIds', id.toString());
    });

    return this.httpClient.post(
      `${Promotionalservice.baseUrl}/api/posts`,
      null, // 🔥 body is null because using @RequestParam
      { headers, params },
    );
  }
}
