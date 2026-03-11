import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Postservice } from '../../cors/postService/postservice';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faThumbsUp, faComment, faThumbsDown } from '@fortawesome/free-solid-svg-icons';
import { Profileservice } from '../../cors/profileService/profileservice';
import { FormsModule } from '@angular/forms';
export interface Post {
  postId: number;
  content: string;
  createdAt?: string;
  username?: string;
  likeCount?: number;
  commentCount?: number;
  profileName?: string;
  liked?: boolean;
}

export interface ProfileModel {
  name: string;
  bio: string;
  profilePicUrl: string;
  location: string;
  websiteUrl: string;
  privacy: string;
}
export interface Comment {
  id: number;
  postId: number;
  text: string;
  createdAt?: string;
  username?: string;
}

@Component({
  selector: 'app-posts',
  imports: [CommonModule, FontAwesomeModule, FormsModule],
  templateUrl: './posts.html',
  styleUrl: './posts.css',
})
export class Posts implements OnInit {
  faThumbsUp = faThumbsUp;
  faComment = faComment;
  faThumbsDown = faThumbsDown;

  posts: Post[] = [];
  loadingPosts = false;

  // Comments drawer state
  isCommentsOpen = false;
  activePost: Post | null = null;
  comments: Comment[] = [];
  loadingComments = false;

  newCommentText = '';
  likingPostIds = new Set<number>();

  constructor(
    private postsService: Postservice,
    private profileService: Profileservice,
  ) {}

  ngOnInit(): void {
    this.fetchPosts();
    this.getUserProfile();
  }

  userProfile: any;
  getUserProfile(): void {
    this.profileService.getUserProfile().subscribe({
      next: (data) => {
        this.userProfile = data;
      },
      error: (err) => {
        // console.log(err);
        // if (err.status == 404) {
        this.openProfileModal();
        // }
        console.error('Failed to load profile', err);
      },
    });
  }

  fetchPosts(): void {
    this.loadingPosts = true;
    this.postsService.getAllPosts().subscribe({
      next: (data) => {
        console.log('Posts fetched:', data);
        this.posts = Array.isArray(data) ? data : (data as any);
        this.loadingPosts = false;
      },
      error: (err) => {
        console.error('Failed to load posts', err);
        this.loadingPosts = false;
      },
    });
  }

  savingProfile = false;
  profileError = '';

  profileModalOpen = false;

  profileForm: ProfileModel = {
    name: '',
    bio: '',
    profilePicUrl: '',
    location: '',
    websiteUrl: '',
    privacy: 'PUBLIC',
  };

  openProfileModal(): void {
    this.profileModalOpen = true;
  }

  closeProfileModal(): void {
    this.profileModalOpen = false;
  }

  //Save Profile
  saveProfile(): void {
    this.profileError = '';

    if (!this.profileForm.name.trim()) {
      this.profileError = 'Name is required';
      return;
    }

    const payload = {
      name: this.profileForm.name,
      bio: this.profileForm.bio,
      profilePicUrl: this.profileForm.profilePicUrl,
      location: this.profileForm.location,
      websiteUrl: this.profileForm.websiteUrl,
      privacy: this.profileForm.privacy,
    };

    this.savingProfile = true;

    this.profileService.createProfile(payload).subscribe({
      next: (res: any) => {
        console.log('Profile saved:', res);
        this.savingProfile = false;
        this.closeProfileModal();
        // this.getUserProfile();
      },
      error: (err) => {
        this.savingProfile = false;
        console.error('Failed to save profile', err);
        this.profileError = 'Failed to save profile';
      },
    });
  }

  onLike(post: Post): void {
    if (this.likingPostIds.has(post.postId)) return;

    // optimistic UI
    post.liked = true;
    post.likeCount = Math.max((post.likeCount ?? 1) + 1, 0);

    this.postsService.postLike(post.postId).subscribe({
      next: (res) => {
        // If backend returns updated likeCount, set it here:
        // post.likeCount = res.likeCount;
        //
        this.likingPostIds.add(post.postId);
      },
      error: (err) => {
        post.liked = false;
        // post.likeCount = Math.max((post.likeCount ?? 1) - 1, 0);
        // this.likingPostIds.delete(post.postId);
      },
    });
  }

  onUnlike(post: Post): void {
    if (this.likingPostIds.has(post.postId)) return;

    // this.likingPostIds.add(post.postId);

    // optimistic UI
    post.liked = false;
    post.likeCount = Math.max((post.likeCount ?? 1) - 1, 0);
    this.postsService.postUnlike(post.postId).subscribe({
      next: (res) => {
        this.likingPostIds.delete(post.postId);

        console.log('Unlike response:', res);
        this.fetchPosts(); // Refresh posts to reflect changes
      },
      error: (err) => {
        console.error('Unlike failed:', err);
      },
    });
  }

  // openComments(post: Post): void {
  //   this.isCommentsOpen = true;
  //   this.activePost = post;
  //   this.newCommentText = '';
  //   this.fetchComments(post.id);
  // }

  // closeComments(): void {
  //   this.isCommentsOpen = false;
  //   this.activePost = null;
  //   this.comments = [];
  //   this.newCommentText = '';
  // }

  // fetchComments(postId: number): void {
  //   this.loadingComments = true;
  //   this.postsService.getComments(postId).subscribe({
  //     next: (data) => {
  //       this.comments = Array.isArray(data) ? data : (data as any);
  //       this.loadingComments = false;
  //     },
  //     error: (err) => {
  //       console.error('Failed to load comments', err);
  //       this.loadingComments = false;
  //     },
  //   });
  // }

  // addComment(): void {
  //   if (!this.activePost) return;

  //   const text = this.newCommentText.trim();
  //   if (!text) return;

  //   const postId = this.activePost.id;

  //   // Optimistic add (temporary comment)
  //   const temp: Comment = {
  //     id: Date.now(),
  //     postId,
  //     text,
  //     username: 'You',
  //     createdAt: new Date().toISOString(),
  //   };
  //   this.comments = [temp, ...this.comments];
  //   this.newCommentText = '';

  //   this.postsService.addComment(postId, text).subscribe({
  //     next: () => {
  //       // safest: refresh from backend so IDs/order match backend
  //       this.fetchComments(postId);
  //       // update comment count
  //       this.activePost!.commentCount = (this.activePost!.commentCount ?? 0) + 1;
  //     },
  //     error: (err) => {
  //       console.error('Add comment failed', err);
  //       // rollback optimistic insert
  //       this.comments = this.comments.filter((c) => c.id !== temp.id);
  //     },
  //   });
  // }

  // trackByPostId(_: number, post: Post) {
  //   return post.id;
  // }

  // trackByCommentId(_: number, c: Comment) {
  //   return c.id;
  // }
}
