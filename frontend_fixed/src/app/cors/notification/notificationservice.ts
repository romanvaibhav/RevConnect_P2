import { Injectable } from '@angular/core';
import { environment } from '../../envi/environment';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class Notificationservice {
  static baseUrl = environment.API_HOST_URL;

  constructor(private httpClient: HttpClient) {}

  //Get all the notifications for a user
  getAllNotification(): Observable<any> {
    const token = localStorage.getItem('token');
    const userId = Number(localStorage.getItem('userId'));
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.httpClient.get(
      `${Notificationservice.baseUrl}/api/notifications/history/${userId}`,
      {
        headers,
      },
    );
  }

  //unread count notifcations
  unreadCountNotification(): Observable<any> {
    const token = localStorage.getItem('token');
    const userId = Number(localStorage.getItem('userId'));
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.httpClient.get(
      `${Notificationservice.baseUrl}/api/notifications/unread/count/${userId}`,
      {
        headers,
      },
    );
  }

  //mark all notifications as read
  markAllAsRead(): Observable<any> {
    const token = localStorage.getItem('token');
    const userId = Number(localStorage.getItem('userId'));
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.httpClient.post(
      `${Notificationservice.baseUrl}/api/notifications/markAllAsRead/${userId}`,
      {},
      {
        headers,
      },
    );
  }

  //Mark a single notification as

  markNotificationAsRead(notificationId: number): Observable<any> {
    const token = localStorage.getItem('token');
    const userId = Number(localStorage.getItem('userId'));
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.httpClient.post(
      `${Notificationservice.baseUrl}/api/notifications/markAsRead/${notificationId}`,
      {},
      {
        headers,
      },
    );
  }

  //Send Notification to a user
  sendNotification(
    targetUserId: number,
    message: string,
    type: string,
    relatedEntityId?: number,
  ): Observable<any> {
    const token = localStorage.getItem('token');

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    let params = new HttpParams()
      .set('userId', targetUserId.toString())
      .set('message', message)
      .set('type', type);

    if (relatedEntityId) {
      params = params.set('relatedEntityId', relatedEntityId.toString());
    }

    return this.httpClient.post(
      `${Notificationservice.baseUrl}/api/notifications/send`,
      null,
      { headers, params },
    );
  }
}
