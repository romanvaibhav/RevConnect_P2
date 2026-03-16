import { Injectable } from '@angular/core';
import { environment } from '../../envi/environment';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class BusinessService {
  static baseUrl = environment.API_HOST_URL;

  private get headers(): HttpHeaders {
    return new HttpHeaders({ Authorization: `Bearer ${localStorage.getItem('token')}` });
  }

  constructor(private http: HttpClient) {}

  // ── Business Profile ──────────────────────────────────────────────────────
  getBusinessProfile(userId: number): Observable<any> {
    return this.http.get(`${BusinessService.baseUrl}/businessProfile/view/${userId}`, {
      headers: this.headers,
    });
  }

  createBusinessProfile(data: any): Observable<any> {
    return this.http.post(`${BusinessService.baseUrl}/businessProfile/create`, data, {
      headers: this.headers,
    });
  }

  updateBusinessProfile(userId: number, data: any): Observable<any> {
    return this.http.put(`${BusinessService.baseUrl}/businessProfile/update/${userId}`, data, {
      headers: this.headers,
    });
  }

  // ── Products ──────────────────────────────────────────────────────────────
  getAllProducts(): Observable<any> {
    return this.http.get(`${BusinessService.baseUrl}/api/products`, { headers: this.headers });
  }

  createProduct(data: any): Observable<any> {
    return this.http.post(`${BusinessService.baseUrl}/api/products`, data, {
      headers: this.headers,
    });
  }

  updateProduct(id: number, data: any): Observable<any> {
    return this.http.put(`${BusinessService.baseUrl}/api/products/${id}`, data, {
      headers: this.headers,
    });
  }

  deleteProduct(id: number): Observable<any> {
    return this.http.delete(`${BusinessService.baseUrl}/api/products/${id}`, {
      headers: this.headers,
    });
  }

  // ── Business Hours ────────────────────────────────────────────────────────
  getBusinessHours(userId: number): Observable<any> {
    return this.http.get(`${BusinessService.baseUrl}/businessHours/${userId}`, {
      headers: this.headers,
    });
  }

  setBusinessHours(userId: number, data: any): Observable<any> {
    return this.http.post(`${BusinessService.baseUrl}/businessHours/set/${userId}`, data, {
      headers: this.headers,
    });
  }

  deleteBusinessHours(hoursId: number): Observable<any> {
    return this.http.delete(`${BusinessService.baseUrl}/businessHours/${hoursId}`, {
      headers: this.headers,
    });
  }

  // ── Promotional Posts ─────────────────────────────────────────────────────
  getPromotionalPostsByBusiness(businessProfileId: number): Observable<any> {
    return this.http.get(`${BusinessService.baseUrl}/api/posts/business/${businessProfileId}`, {
      headers: this.headers,
    });
  }

  createPromotionalPost(
    businessProfileId: number,
    content: string,
    imageUrl: string,
    ctaType: string,
    ctaUrl: string
  ): Observable<any> {
    let params = new HttpParams()
      .set('businessProfileId', businessProfileId.toString())
      .set('content', content)
      .set('imageUrl', imageUrl)
      .set('ctaType', ctaType)
      .set('ctaUrl', ctaUrl);
    return this.http.post(`${BusinessService.baseUrl}/api/posts`, null, {
      headers: this.headers,
      params,
    });
  }

  updatePromotionalPost(id: number, data: any): Observable<any> {
    let params = new HttpParams();
    if (data.content) params = params.set('content', data.content);
    if (data.imageUrl !== undefined) params = params.set('imageUrl', data.imageUrl);
    if (data.ctaType) params = params.set('ctaType', data.ctaType);
    if (data.ctaUrl) params = params.set('ctaUrl', data.ctaUrl);
    return this.http.put(`${BusinessService.baseUrl}/api/posts/${id}`, null, {
      headers: this.headers,
      params,
    });
  }

  deletePromotionalPost(id: number): Observable<any> {
    return this.http.delete(`${BusinessService.baseUrl}/api/posts/${id}`, {
      headers: this.headers,
    });
  }

  pinPromotionalPost(id: number): Observable<any> {
    return this.http.put(`${BusinessService.baseUrl}/api/posts/${id}/pin`, null, {
      headers: this.headers,
    });
  }

  unpinPromotionalPost(id: number): Observable<any> {
    return this.http.put(`${BusinessService.baseUrl}/api/posts/${id}/unpin`, null, {
      headers: this.headers,
    });
  }

  // ── Post Analytics ────────────────────────────────────────────────────────
  getPostAnalytics(postId: number): Observable<any> {
    return this.http.get(`${BusinessService.baseUrl}/post/${postId}/analytics`, {
      headers: this.headers,
    });
  }

  // ── External Links ────────────────────────────────────────────────────────
  getExternalLinks(userId: number): Observable<any> {
    return this.http.get(`${BusinessService.baseUrl}/externalLinks/user/${userId}`, {
      headers: this.headers,
    });
  }

  addExternalLink(data: any): Observable<any> {
    return this.http.post(`${BusinessService.baseUrl}/externalLinks/add`, data, {
      headers: this.headers,
    });
  }

  deleteExternalLink(id: number): Observable<any> {
    return this.http.delete(`${BusinessService.baseUrl}/externalLinks/delete/${id}`, {
      headers: this.headers,
    });
  }
}
