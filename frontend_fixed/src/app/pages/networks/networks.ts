import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import {
  faUserPlus, faUserMinus, faCheck, faXmark,
  faLocationDot, faMagnifyingGlass, faUserGroup, faLink
} from '@fortawesome/free-solid-svg-icons';
import { Networkservice } from '../../cors/networkService/networkservice';
import { Profileservice } from '../../cors/profileService/profileservice';

type ModalType = 'connections'|'following'|'followers'|'pending';

@Component({
  selector: 'app-networks',
  standalone: true,
  imports: [CommonModule, FormsModule, FontAwesomeModule],
  templateUrl: './networks.html',
  styleUrl: './networks.css',
})
export class Networks implements OnInit {
  faUserPlus=faUserPlus; faUserMinus=faUserMinus; faCheck=faCheck;
  faXmark=faXmark; faLocationDot=faLocationDot; faMagnifyingGlass=faMagnifyingGlass;
  faUserGroup=faUserGroup; faLink=faLink;

  connections: any[]=[]; followings: any[]=[]; followers: any[]=[]; pendingRequests: any[]=[]; searchResults: any[]=[];
  isModalOpen=false; modalType: ModalType='connections'; searchTerm=''; loading=false;
  isProfileModalOpen=false; selectedProfile: any=null; profileLoading=false; actionLoading=false;
  currentUserId = Number(localStorage.getItem('userId'))||0;
  currentAccountType = (localStorage.getItem('accountType')||'').toUpperCase();
  errorMsg=''; successMsg='';

  constructor(private networkService: Networkservice, private profileService: Profileservice) {}

  ngOnInit(): void {
    this.getUserConnections(); this.getAllFollowing(); this.getAllPendingReq();
    if (this.isBusinessUser()) this.getAllFollowers();
  }

  getUserConnections(): void { this.networkService.getAllConnection().subscribe({ next:(d:any)=>this.connections=Array.isArray(d)?d:[], error:()=>{} }); }
  getAllFollowing(): void { this.networkService.getFollowingByUser().subscribe({ next:(d:any)=>this.followings=Array.isArray(d)?d:[], error:()=>{} }); }
  getAllFollowers(): void { this.networkService.getFollowersByUser().subscribe({ next:(d:any)=>this.followers=Array.isArray(d)?d:[], error:()=>{} }); }
  getAllPendingReq(): void { this.networkService.getAllPendingRequests().subscribe({ next:(d:any)=>this.pendingRequests=Array.isArray(d)?d:[], error:()=>{} }); }

  openModal(type: ModalType): void { this.modalType=type; this.isModalOpen=true; }
  closeModal(): void { this.isModalOpen=false; }

  onSearch(): void {
    const q=this.searchTerm.trim(); if (!q) return;
    this.loading=true;
    this.profileService.searchUserProfiles(q).subscribe({
      next:(d:any)=>{ this.searchResults=Array.isArray(d)?d:[]; this.loading=false; },
      error:()=>this.loading=false,
    });
  }
  clearSearch(): void { this.searchTerm=''; this.searchResults=[]; }

  viewProfile(userId: number): void {
    if (!userId) return;
    this.isProfileModalOpen=true; this.profileLoading=true; this.selectedProfile=null;
    this.profileService.getUserProfileById(userId).subscribe({
      next:(p:any)=>{ this.selectedProfile=p; this.profileLoading=false; },
      error:()=>{ this.profileLoading=false; this.isProfileModalOpen=false; },
    });
  }
  closeProfileModal(): void { this.isProfileModalOpen=false; this.selectedProfile=null; }

  connectTo(userId: number): void {
    if (!userId||this.actionLoading) return; this.actionLoading=true;
    this.networkService.sendConnectionRequest(userId).subscribe({
      next:()=>{ this.actionLoading=false; this.successMsg='Request sent!'; this.getAllPendingReq(); setTimeout(()=>this.successMsg='',2500); },
      error:(err:any)=>{ this.errorMsg=err?.error?.message||'Failed to send.'; this.actionLoading=false; setTimeout(()=>this.errorMsg='',3000); },
    });
  }
  removeConnection(connectionId: number): void {
    if (!connectionId||this.actionLoading) return; this.actionLoading=true;
    this.networkService.deleteConnectionRequest(connectionId).subscribe({ next:()=>{ this.actionLoading=false; this.getUserConnections(); }, error:()=>this.actionLoading=false });
  }
  unfollow(followingId: number): void {
    if (!followingId||this.actionLoading) return; this.actionLoading=true;
    this.networkService.unfollowUser(followingId).subscribe({ next:()=>{ this.actionLoading=false; this.getAllFollowing(); }, error:()=>this.actionLoading=false });
  }
  followUser(userId: number): void {
    if (!userId||this.actionLoading) return; this.actionLoading=true;
    this.networkService.followUser(userId).subscribe({
      next:()=>{ this.actionLoading=false; this.successMsg='Now following!'; this.getAllFollowing(); setTimeout(()=>this.successMsg='',2500); },
      error:(err:any)=>{ this.errorMsg=err?.error?.message||'Failed to follow.'; this.actionLoading=false; setTimeout(()=>this.errorMsg='',3000); },
    });
  }
  acceptRequest(requestId: number): void {
    if (!requestId||this.actionLoading) return; this.actionLoading=true;
    this.networkService.acceptConnectionRequest(requestId).subscribe({ next:()=>{ this.actionLoading=false; this.getAllPendingReq(); this.getUserConnections(); }, error:()=>this.actionLoading=false });
  }
  rejectRequest(requestId: number): void {
    if (!requestId||this.actionLoading) return; this.actionLoading=true;
    this.networkService.rejectConnectionRequest(requestId).subscribe({ next:()=>{ this.actionLoading=false; this.getAllPendingReq(); }, error:()=>this.actionLoading=false });
  }

  isBusinessUser(): boolean { return this.currentAccountType==='BUSINESS'; }
  selectedIsBusiness(): boolean { return (this.selectedProfile?.accountType||'').toUpperCase()==='BUSINESS'; }
  pickUserId(obj: any): number { return obj?.userId??obj?.user2Id??obj?.id??obj?.followingId??0; }
  pickRequestId(obj: any): number { return obj?.requestId??obj?.id??0; }
  getConnectionName(c: any): string { return c?.user1Id===this.currentUserId ? c?.user2Name||'User' : c?.user1Name||'User'; }
  getConnectionId(c: any): number { return c?.id??0; }
  initials(name: string): string {
    if (!name) return 'U';
    const p=name.trim().split(' ').filter(Boolean);
    return p.length===1?p[0].charAt(0).toUpperCase():p[0].charAt(0).toUpperCase()+p[p.length-1].charAt(0).toUpperCase();
  }
}
