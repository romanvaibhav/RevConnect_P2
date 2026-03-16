import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import {
  faBell, faThumbsUp, faComment, faShareNodes,
  faUserPlus, faUserCheck, faFile, faWallet, faCheck
} from '@fortawesome/free-solid-svg-icons';
import { Notificationservice } from '../../cors/notification/notificationservice';

type NotificationItem = { id:number; message:string; type:string; createdAt:string; read:boolean; relatedEntityId?:number; };
type TabKey = 'all'|'unread';

@Component({
  selector: 'app-notifications',
  standalone: true,
  imports: [CommonModule, FormsModule, FontAwesomeModule],
  templateUrl: './notification.html',
  styleUrl: './notification.css',
})
export class Notifications implements OnInit {
  faBell=faBell; faThumbsUp=faThumbsUp; faComment=faComment;
  faShareNodes=faShareNodes; faUserPlus=faUserPlus; faUserCheck=faUserCheck;
  faFile=faFile; faWallet=faWallet; faCheck=faCheck;

  activeTab: TabKey = 'all';
  loading=false; errorMsg='';
  notifications: NotificationItem[] = [];
  unreadCount=0;

  constructor(private notificationService: Notificationservice) {}

  ngOnInit(): void { this.refreshAll(); }

  refreshAll(): void { this.fetchNotifications(); this.fetchUnreadCount(); }

  fetchNotifications(): void {
    this.loading=true; this.errorMsg='';
    this.notificationService.getAllNotification().subscribe({
      next:(data:any)=>{ this.notifications=(Array.isArray(data)?data:[]).map((n:any)=>this.mapNotification(n)).sort((a:any,b:any)=>new Date(b.createdAt).getTime()-new Date(a.createdAt).getTime()); this.loading=false; },
      error:()=>{ this.errorMsg='Failed to load notifications.'; this.loading=false; },
    });
  }
  fetchUnreadCount(): void {
    this.notificationService.unreadCountNotification().subscribe({
      next:(cnt:any)=>this.unreadCount=Number(cnt??0), error:()=>{},
    });
  }
  markAllAsRead(): void {
    this.notificationService.markAllAsRead().subscribe({
      next:()=>{ this.notifications=this.notifications.map(n=>({...n,read:true})); this.unreadCount=0; },
      error:()=>this.errorMsg='Failed to mark all as read.',
    });
  }
  markAsRead(n: NotificationItem): void {
    if (n.read) return;
    this.notificationService.markNotificationAsRead(n.id).subscribe({
      next:()=>{ n.read=true; this.unreadCount=Math.max(0,this.unreadCount-1); }, error:()=>{},
    });
  }

  setTab(tab: TabKey): void { this.activeTab=tab; }
  get filteredNotifications(): NotificationItem[] { return this.activeTab==='unread'?this.notifications.filter(n=>!n.read):this.notifications; }
  getTypeLabel(type: string): string { return (type||'GENERAL').toUpperCase(); }

  getTypeIcon(type: string): any {
    const t=(type||'').toLowerCase();
    if (t.includes('like')) return this.faThumbsUp;
    if (t.includes('comment')) return this.faComment;
    if (t.includes('share')) return this.faShareNodes;
    if (t.includes('follow')) return this.faUserPlus;
    if (t.includes('connection_accepted')) return this.faUserCheck;
    if (t.includes('connection')) return this.faUserPlus;
    return this.faBell;
  }

  private mapNotification(n: any): NotificationItem {
    return { id:Number(n?.notificationId??n?.id??0), message:String(n?.message??''), type:String(n?.type??'GENERAL'), createdAt:String(n?.createdAt??n?.timestamp??new Date().toISOString()), read:Boolean(n?.read??n?.isRead??false), relatedEntityId:n?.relatedEntityId };
  }
}
