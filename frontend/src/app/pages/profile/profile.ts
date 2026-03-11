import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FaIconComponent } from '@fortawesome/angular-fontawesome';
import { faThumbsUp, faThumbsDown, faComment } from '@fortawesome/free-solid-svg-icons';

import { Profileservice } from '../../cors/profileService/profileservice';
import { Postservice } from '../../cors/postService/postservice';
import { Post } from '../posts/posts';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, FormsModule, FaIconComponent],
  templateUrl: './profile.html',
  styleUrl: './profile.css',
})
export class Profile implements OnInit {
  faThumbsUp = faThumbsUp;
  faThumbsDown = faThumbsDown;
  faComment = faComment;

  profile: any = null;
  posts: any[] = [];

  activeTab: 'posts' | 'saved' | 'shared' = 'posts';

  isEditOpen = false;
  isSaving = false;

  errorMsg = '';
  successMsg = '';

  editForm: any = {
    name: '',
    bio: '',
    location: '',
    websiteUrl: '',
    profilePicUrl: '',
    privacy: 'public',
  };

  likingPostIds = new Set<number>();

  // Comment modal state
  commentModalOpen = false;
  activePost: any = null;
  comments: any[] = [];
  newCommentText = '';
  postingComment = false;
  loadingComments = false;
  commentError = '';

  constructor(
    private profileService: Profileservice,
    private postService: Postservice,
  ) {}

  ngOnInit(): void {
    this.getUserProfile();
    this.getUserPosts();
  }

  getUserProfile(): void {
    this.profileService.getUserProfile().subscribe({
      next: (data) => {
        this.profile = data;
      },
      error: (err) => {
        console.error('Failed to load profile', err);
      },
    });
  }

  getUserPosts(): void {
    this.profileService.getUserPosts().subscribe({
      next: (data) => {
        this.posts = (data || []).map((post: any) => ({
          ...post,
          liked: post.liked ?? false,
          likeCount: post.likeCount ?? 0,
          commentCount: post.commentCount ?? 0,
        }));
        console.log('User posts:', this.posts);
      },
      error: (err) => {
        console.error('Failed to load posts', err);
      },
    });
  }

  onLike(post: any): void {
    if (!post?.postId || this.likingPostIds.has(post.postId) || post.liked) return;

    this.likingPostIds.add(post.postId);

    post.liked = true;
    post.likeCount = (post.likeCount ?? 0) + 1;

    this.postService.postLike(post.postId).subscribe({
      next: (res: any) => {
        console.log('Like response:', res);
        this.likingPostIds.delete(post.postId);
      },
      error: (err: any) => {
        console.error('Like failed:', err);
        post.liked = false;
        post.likeCount = Math.max((post.likeCount ?? 1) - 1, 0);
        this.likingPostIds.delete(post.postId);
      },
    });
  }

  onUnlike(post: any): void {
    if (!post?.postId || this.likingPostIds.has(post.postId) || !post.liked) return;

    this.likingPostIds.add(post.postId);

    post.liked = false;
    post.likeCount = Math.max((post.likeCount ?? 1) - 1, 0);

    this.postService.postUnlike(post.postId).subscribe({
      next: (res: any) => {
        console.log('Unlike response:', res);
        this.likingPostIds.delete(post.postId);
      },
      error: (err: any) => {
        console.error('Unlike failed:', err);
        post.liked = true;
        post.likeCount = (post.likeCount ?? 0) + 1;
        this.likingPostIds.delete(post.postId);
      },
    });
  }

  openEditModal(): void {
    if (!this.profile) return;

    this.editForm = {
      name: this.profile.name || '',
      bio: this.profile.bio || '',
      location: this.profile.location || '',
      websiteUrl: this.profile.websiteUrl || '',
      profilePicUrl: this.profile.profilePicUrl || '',
      privacy: this.profile.privacy || 'public',
    };

    this.errorMsg = '';
    this.successMsg = '';
    this.isEditOpen = true;
  }

  closeEditModal(): void {
    this.isEditOpen = false;
  }

  saveProfile(): void {
    this.isSaving = true;
    this.errorMsg = '';
    this.successMsg = '';

    const payload = {
      ...this.editForm,
    };

    this.profileService.updateProfile(payload).subscribe({
      next: (updated) => {
        this.profile = updated ?? { ...this.profile, ...payload };
        this.successMsg = 'Profile updated successfully!';
        this.isSaving = false;
        setTimeout(() => this.closeEditModal(), 400);
      },
      error: (err) => {
        console.error('Update failed', err);
        this.errorMsg = 'Failed to update profile.';
        this.isSaving = false;
      },
    });
  }

  formatWebsite(url: string): string {
    if (!url) return '';
    if (url.startsWith('http://') || url.startsWith('https://')) return url;
    return `https://${url}`;
  }

  openComments(post: any): void {
    this.activePost = post;
    this.commentModalOpen = true;
    this.newCommentText = '';
    this.commentError = '';
    this.loadComments(post.postId);
  }

  closeComments(): void {
    this.commentModalOpen = false;
    this.activePost = null;
    this.comments = [];
    this.newCommentText = '';
    this.commentError = '';
  }

  loadComments(postId: number): void {
    this.loadingComments = true;
    this.comments = [];

    this.postService.getCommentsByPost(postId).subscribe({
      next: (res: any) => {
        this.comments = res || [];
        this.loadingComments = false;
      },
      error: (err: any) => {
        console.error('Failed to load comments', err);
        this.commentError = 'Failed to load comments.';
        this.loadingComments = false;
      },
    });
  }

  addComment(): void {
    if (!this.activePost || !this.newCommentText.trim()) return;

    this.postingComment = true;
    this.commentError = '';

    const payload = {
      content: this.newCommentText.trim(),
    };

    this.postService.addComment(this.activePost.postId).subscribe({
      next: (res: any) => {
        this.newCommentText = '';
        this.postingComment = false;

        this.activePost.commentCount = (this.activePost.commentCount ?? 0) + 1;
        this.loadComments(this.activePost.postId);
      },
      error: (err: any) => {
        console.error('Failed to add comment', err);
        this.commentError = 'Failed to post comment.';
        this.postingComment = false;
      },
    });
  }
}
