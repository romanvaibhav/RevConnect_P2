import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Notificationservice } from '../../cors/notification/notificationservice';
import { faBell } from '@fortawesome/free-solid-svg-icons';
import { FaIconComponent } from "@fortawesome/angular-fontawesome";
type NotificationItem = {
  id: number; // notificationId
  message: string;
  type: string; // INVOICE / LOAN / TRANSACTION etc
  createdAt: string; // ISO date
  read: boolean;

  // optional: if backend sends these
  relatedEntityId?: number;
};

type TabKey = 'all' | 'unread' | 'preferences';

@Component({
  selector: 'app-notifications',
  standalone: true,
  imports: [CommonModule, FormsModule, FaIconComponent],
  templateUrl: './notification.html',
  styleUrl: './notification.css',
})
export class Notifications implements OnInit {
  // UI state
  faBell = faBell;

  activeTab: TabKey = 'all';
  loading = false;
  errorMsg = '';

  // data
  notifications: NotificationItem[] = [];
  unreadCount = 0;

  // preferences UI (dummy for now)
  prefEmail = true;
  prefPush = true;
  prefInApp = true;

  constructor(private notificationService: Notificationservice) {}

  ngOnInit(): void {
    this.refreshAll();
  }

  refreshAll(): void {
    this.fetchNotifications();
    this.fetchUnreadCount();
  }

  // ---------------- API calls ----------------
  fetchNotifications(): void {
    this.loading = true;
    this.errorMsg = '';

    this.notificationService.getAllNotification().subscribe({
      next: (data: any) => {
        const list = Array.isArray(data) ? data : [];

        // map safely (handle different backend field names)
        this.notifications = list
          .map((n: any) => this.mapNotification(n))
          .sort(
            (a: any, b: any) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime(),
          );

        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        this.errorMsg = 'Failed to load notifications.';
        this.loading = false;
      },
    });
  }

  fetchUnreadCount(): void {
    this.notificationService.unreadCountNotification().subscribe({
      next: (cnt: any) => {
        this.unreadCount = Number(cnt ?? 0);
      },
      error: (err) => {
        console.error('Unread count failed', err);
      },
    });
  }

  markAllAsRead(): void {
    if (this.notifications.length === 0) return;

    this.notificationService.markAllAsRead().subscribe({
      next: () => {
        // update UI instantly
        this.notifications = this.notifications.map((n) => ({ ...n, read: true }));
        this.unreadCount = 0;
        this.activeTab = 'all';
      },
      error: (err) => {
        console.error(err);
        this.errorMsg = 'Failed to mark all as read.';
      },
    });
  }

  markAsRead(n: NotificationItem): void {
    if (n.read) return;

    this.notificationService.markNotificationAsRead(n.id).subscribe({
      next: () => {
        n.read = true;
        this.unreadCount = Math.max(0, this.unreadCount - 1);
      },
      error: (err) => {
        console.error(err);
        this.errorMsg = 'Failed to mark as read.';
      },
    });
  }

  // ---------------- UI helpers ----------------
  setTab(tab: TabKey): void {
    this.activeTab = tab;
  }

  get filteredNotifications(): NotificationItem[] {
    if (this.activeTab === 'unread') return this.notifications.filter((n) => !n.read);
    return this.notifications;
  }

  getTypeLabel(type: string): string {
    return (type || 'GENERAL').toUpperCase();
  }

  // icon based on type
  getIcon(type: string): string {
    const t = (type || '').toLowerCase();

    if (t.includes('invoice')) return '📄';
    if (t.includes('loan')) return '💰';
    if (t.includes('wallet') || t.includes('transaction')) return '💳';
    if (t.includes('like')) return '👍';
    if (t.includes('comment')) return '💬';
    if (t.includes('follow')) return '👤';
    return '🔔';
  }

  // format notification (support different backend field names)
  private mapNotification(n: any): NotificationItem {
    return {
      id: Number(n?.notificationId ?? n?.id ?? 0),
      message: String(n?.message ?? n?.content ?? ''),
      type: String(n?.type ?? 'GENERAL'),
      createdAt: String(n?.createdAt ?? n?.time ?? new Date().toISOString()),
      read: Boolean(n?.read ?? n?.isRead ?? false),
      relatedEntityId: n?.relatedEntityId ?? undefined,
    };
  }

  // Preferences save (frontend-only for now)
  savePreferences(): void {
    // If you have backend endpoint, call it here.
    alert('Preferences saved (frontend only).');
  }
}
