import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Networkservice } from '../../cors/networkService/networkservice';
import { Profileservice } from '../../cors/profileService/profileservice';

type ModalType = 'connections' | 'following' | 'followers' | 'pending';

@Component({
  selector: 'app-networks',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './networks.html',
  styleUrl: './networks.css',
})
export class Networks implements OnInit {
  constructor(
    private networkService: Networkservice,
    private profileService: Profileservice,
  ) {}

  // data
  connections: any[] = [];
  followings: any[] = [];
  followers: any[] = [];
  pendingRequests: any[] = [];
  searchResults: any[] = [];

  // ui state
  isModalOpen = false;
  modalType: ModalType = 'connections';
  searchTerm = '';
  loading = false;

  // profile modal
  isProfileModalOpen = false;
  selectedProfile: any = null;
  profileLoading = false;

  // action loading (disable buttons)
  actionLoading = false;

  currentUserId = Number(localStorage.getItem('userId')) || 0;
  currentAccountType = (localStorage.getItem('accountType') || '').toUpperCase(); // BUSINESS / CREATOR / etc.

  ngOnInit(): void {
    this.getUserConnections();
    this.getAllFollowing();
    this.getAllPendingReq();

    // only business user sees followers
    if (this.isBusinessUser()) {
      this.getAllFollowers();
    }
  }

  // ---------- API Calls ----------
  getUserConnections(): void {
    this.networkService.getAllConnection().subscribe({
      next: (data: any) => {
        console.log('Connections data:', data);
        this.connections = Array.isArray(data) ? data : [];
      },
      error: (err: any) => console.error('Failed to load connections', err),
    });
  }

  getAllFollowing(): void {
    this.networkService.getFollowingByUser().subscribe({
      next: (data: any) => {
        console.log('following data:', data);

        this.followings = Array.isArray(data) ? data : [];
      },
      error: (err: any) => console.error('Failed to load following', err),
    });
  }

  getAllFollowers(): void {
    this.networkService.getFollowersByUser().subscribe({
      next: (data: any) => {
        console.log('followers data:', data);

        this.followers = Array.isArray(data) ? data : [];
      },
      error: (err: any) => console.error('Failed to load followers', err),
    });
  }

  getAllPendingReq(): void {
    this.networkService.getAllPendingRequests().subscribe({
      next: (data: any) => {
        this.pendingRequests = Array.isArray(data) ? data : [];
      },
      error: (err: any) => console.error('Failed to load pending requests', err),
    });
  }

  // ---------- Modal ----------
  openModal(type: ModalType) {
    this.modalType = type;
    this.isModalOpen = true;
  }

  closeModal() {
    this.isModalOpen = false;
  }

  // ---------- Search ----------
  onSearch() {
    const q = this.searchTerm.trim();
    if (!q) return;

    this.loading = true;
    this.profileService.searchUserProfiles(q).subscribe({
      next: (data: any) => {
        this.searchResults = Array.isArray(data) ? data : [];
        this.loading = false;
      },
      error: (err: any) => {
        console.error('Failed to search users', err);
        this.loading = false;
      },
    });
  }

  clearSearch() {
    this.searchTerm = '';
    this.searchResults = [];
  }

  // ---------- View Profile Modal ----------
  viewProfile(userId: number) {
    if (!userId) return;

    this.isProfileModalOpen = true;
    this.profileLoading = true;
    this.selectedProfile = null;

    // ✅ IMPORTANT:
    // Your "getUserProfile()" service fetches profile using localStorage userId (current user).
    // To view other user, you must have getUserProfileById(userId).
    // If you already have it, this will work.
    // If not, use the method given below at the end.

    const getter: any = (this.profileService as any).getUserProfileById
      ? (this.profileService as any).getUserProfileById(userId)
      : null;

    if (!getter) {
      console.error(
        'Profileservice.getUserProfileById(userId) not found. Add it in Profileservice.',
      );
      this.profileLoading = false;
      this.isProfileModalOpen = false;
      return;
    }

    getter.subscribe({
      next: (profile: any) => {
        this.selectedProfile = profile;
        this.profileLoading = false;
      },
      error: (err: any) => {
        console.error('Failed to load profile', err);
        this.profileLoading = false;
      },
    });
  }

  closeProfileModal() {
    this.isProfileModalOpen = false;
    this.selectedProfile = null;
    this.profileLoading = false;
  }

  // ---------- Actions ----------
  connectTo(userId: number) {
    if (!userId || this.actionLoading) return;

    this.actionLoading = true;
    this.networkService.sendConnectionRequest(userId).subscribe({
      next: () => {
        this.actionLoading = false;
        this.getAllPendingReq();
      },
      error: (err: any) => {
        console.error('sendConnectionRequest failed', err);
        this.actionLoading = false;
      },
    });
  }

  removeConnection(otherUserId: number) {
    if (!otherUserId || this.actionLoading) return;

    this.actionLoading = true;
    this.networkService.deleteConnectionRequest(otherUserId).subscribe({
      next: () => {
        this.actionLoading = false;
        this.getUserConnections();
      },
      error: (err: any) => {
        console.error('deleteConnectionRequest failed', err);
        this.actionLoading = false;
      },
    });
  }

  unfollow(otherUserId: number) {
    if (!otherUserId || this.actionLoading) return;

    this.actionLoading = true;
    this.networkService.unfolllowUser(otherUserId).subscribe({
      next: () => {
        this.actionLoading = false;
        this.getAllFollowing();
      },
      error: (err: any) => {
        console.error('unfollow failed', err);
        this.actionLoading = false;
      },
    });
  }

  acceptRequest(requestId: number) {
    if (!requestId || this.actionLoading) return;

    this.actionLoading = true;
    this.networkService.acceptConnectionRequest(requestId).subscribe({
      next: () => {
        this.actionLoading = false;
        this.getAllPendingReq();
        this.getUserConnections();
      },
      error: (err: any) => {
        console.error('acceptConnectionRequest failed', err);
        this.actionLoading = false;
      },
    });
  }

  rejectRequest(requestId: number) {
    if (!requestId || this.actionLoading) return;

    this.actionLoading = true;
    this.networkService.rejectConnectionRequest(requestId).subscribe({
      next: () => {
        this.actionLoading = false;
        this.getAllPendingReq();
      },
      error: (err: any) => {
        console.error('rejectConnectionRequest failed', err);
        this.actionLoading = false;
      },
    });
  }

  // Followers remove (endpoint not shared)
  removeFollower(followerUserId: number) {
    // Plug your API here when you have endpoint.
    // Temporary UI update:
    this.followers = this.followers.filter((f) => this.pickUserId(f) !== followerUserId);
  }

  // Follow option only for BUSINESS profiles (endpoint not provided)
  followBusiness(userId: number) {
    console.warn('Follow endpoint not provided. userId:', userId);
  }

  // ---------- helpers ----------
  isBusinessUser(): boolean {
    return this.currentAccountType === 'BUSINESS';
  }

  selectedIsBusiness(): boolean {
    const t = (this.selectedProfile?.accountType || '').toUpperCase();
    return t === 'BUSINESS';
  }

  pickUserId(obj: any): number {
    return (
      obj?.userId ?? obj?.id ?? obj?.targetUserId ?? obj?.otherUserId ?? obj?.profileUserId ?? 0
    );
  }

  pickRequestId(obj: any): number {
    return obj?.requestId ?? obj?.id ?? obj?.connectionRequestId ?? 0;
  }

  initials(name: string) {
    if (!name) return 'U';
    const parts = name.trim().split(' ').filter(Boolean);
    if (parts.length === 1) return parts[0].charAt(0).toUpperCase();
    return parts[0].charAt(0).toUpperCase() + parts[parts.length - 1].charAt(0).toUpperCase();
  }
}
