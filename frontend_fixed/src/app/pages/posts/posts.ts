import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Postservice } from '../../cors/postService/postservice';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import {
  faThumbsUp, faComment, faThumbsDown,
  faRotateRight, faUser, faBriefcase
} from '@fortawesome/free-solid-svg-icons';
import { Profileservice } from '../../cors/profileService/profileservice';
import { FormsModule } from '@angular/forms';
import { BusinessService } from '../../cors/businessService/business.service';

export interface Post {
  postId: number; content: string; createdAt?: string;
  username?: string; likeCount?: number; commentCount?: number;
  profileName?: string; liked?: boolean;
}
export interface ProfileModel {
  name: string; bio: string; profilePicUrl: string;
  location: string; websiteUrl: string; privacy: string;
}

@Component({
  selector: 'app-posts',
  standalone: true,
  imports: [CommonModule, FontAwesomeModule, FormsModule],
  templateUrl: './posts.html',
  styleUrl: './posts.css',
})
export class Posts implements OnInit {
  faThumbsUp = faThumbsUp;
  faComment = faComment;
  faThumbsDown = faThumbsDown;
  faRotateRight = faRotateRight;
  faUser = faUser;
  faBriefcase = faBriefcase;

  posts: Post[] = [];
  loadingPosts = false;
  likingPostIds = new Set<number>();

  userId = Number(localStorage.getItem('userId'));
  isBusinessUser = (localStorage.getItem('accountType') || '').toUpperCase() === 'BUSINESS';

  // Profile modal
  savingProfile = false; profileError = ''; profileModalOpen = false;
  profileForm: ProfileModel = { name:'', bio:'', profilePicUrl:'', location:'', websiteUrl:'', privacy:'PUBLIC' };

  // Business profile modal
  businessModalOpen = false; savingBusiness = false; businessError = '';
  businessForm = { category:'', detailedBio:'', contactEmail:'', address:'' };

  userProfile: any;

  constructor(
    private postsService: Postservice,
    private profileService: Profileservice,
    private businessService: BusinessService,
  ) {}

  ngOnInit(): void {
    this.fetchPosts();
    this.checkProfile();
  }

  checkProfile(): void {
    this.profileService.getUserProfile().subscribe({
      next: (data) => {
        this.userProfile = data;
        if (this.isBusinessUser) this.checkBusinessProfile();
      },
      error: () => this.openProfileModal(),
    });
  }

  checkBusinessProfile(): void {
    this.businessService.getBusinessProfile(this.userId).subscribe({
      next: () => {},
      error: () => this.openBusinessModal(),
    });
  }

  fetchPosts(): void {
    this.loadingPosts = true;
    this.postsService.getAllPosts().subscribe({
      next: (data) => { this.posts = Array.isArray(data) ? data : []; this.loadingPosts = false; },
      error: (err) => { console.error(err); this.loadingPosts = false; },
    });
  }

  openProfileModal(): void { this.profileModalOpen = true; }
  closeProfileModal(): void { this.profileModalOpen = false; }

  saveProfile(): void {
    this.profileError = '';
    if (!this.profileForm.name.trim()) { this.profileError = 'Name is required'; return; }
    this.savingProfile = true;
    this.profileService.createProfile({ ...this.profileForm }).subscribe({
      next: () => {
        this.savingProfile = false;
        this.closeProfileModal();
        if (this.isBusinessUser) this.openBusinessModal();
      },
      error: () => { this.savingProfile = false; this.profileError = 'Failed to save profile.'; },
    });
  }

  openBusinessModal(): void {
    this.businessError = '';
    this.businessForm = { category:'', detailedBio:'', contactEmail:'', address:'' };
    this.businessModalOpen = true;
  }
  closeBusinessModal(): void { this.businessModalOpen = false; }
  skipBusinessProfile(): void { this.closeBusinessModal(); }

  saveBusinessProfile(): void {
    this.businessError = '';
    if (!this.businessForm.category.trim()) { this.businessError = 'Category is required.'; return; }
    this.savingBusiness = true;
    this.businessService.createBusinessProfile({ userId: this.userId, ...this.businessForm }).subscribe({
      next: () => { this.savingBusiness = false; this.closeBusinessModal(); },
      error: (err: any) => { this.savingBusiness = false; this.businessError = err?.error?.message || 'Failed to save.'; },
    });
  }

  onLike(post: Post): void {
    if (this.likingPostIds.has(post.postId)) return;
    post.liked = true; post.likeCount = (post.likeCount ?? 0) + 1;
    this.postsService.postLike(post.postId).subscribe({
      next: () => this.likingPostIds.add(post.postId),
      error: () => { post.liked = false; post.likeCount = Math.max((post.likeCount ?? 1) - 1, 0); },
    });
  }

  onUnlike(post: Post): void {
    if (this.likingPostIds.has(post.postId)) return;
    post.liked = false; post.likeCount = Math.max((post.likeCount ?? 1) - 1, 0);
    this.postsService.postUnlike(post.postId).subscribe({
      next: () => { this.likingPostIds.delete(post.postId); this.fetchPosts(); },
      error: (err) => console.error('Unlike failed:', err),
    });
  }
}
