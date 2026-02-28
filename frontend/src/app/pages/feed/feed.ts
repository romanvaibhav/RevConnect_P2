import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { forkJoin } from 'rxjs';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faThumbsUp, faThumbsDown, faComment } from '@fortawesome/free-solid-svg-icons';

import { Postservice } from '../../cors/postService/postservice';

type FeedPost = {
  postId: number; // normal: postId, promo: id
  userId?: number;

  content: string;
  createdAt: string; // ISO date
  updatedAt?: string;

  likeCount: number;
  commentCount: number;

  profileName: string;
  liked?: boolean;

  isPromoted?: boolean;
  imageUrl?: string;
  ctaType?: string;
  ctaUrl?: string;
  isPinned?: boolean;
};

@Component({
  selector: 'app-feed',
  standalone: true,
  imports: [CommonModule, FormsModule, FontAwesomeModule],
  templateUrl: './feed.html',
  styleUrl: './feed.css',
})
export class Feed implements OnInit {
  // icons
  faThumbsUp = faThumbsUp;
  faThumbsDown = faThumbsDown;
  faComment = faComment;

  // posts
  posts: FeedPost[] = [];
  filteredPosts: FeedPost[] = [];

  loading = false;
  errorMsg = '';

  // likes
  likingPostIds = new Set<number>();

  // hashtag search
  hashtagQuery = '';

  // current user
  currentUserId = Number(localStorage.getItem('userId'));

  // comment modal
  commentModalOpen = false;
  activePost: FeedPost | null = null;

  comments: any[] = [];
  loadingComments = false;
  newCommentText = '';
  postingComment = false;
  deletingCommentIds = new Set<number>();
  commentError = '';

  constructor(private postService: Postservice) {}

  ngOnInit(): void {
    this.loadFeed();
  }

  // ---------------- FEED LOAD ----------------
  loadFeed(): void {
    this.loading = true;
    this.errorMsg = '';

    forkJoin({
      normal: this.postService.getAllPosts(),
      promo: this.postService.getAllPromotionalPosts(),
    }).subscribe({
      next: ({ normal, promo }: any) => {
        const normalPosts: FeedPost[] = (Array.isArray(normal) ? normal : []).map((p) =>
          this.mapNormal(p),
        );

        const promoPosts: FeedPost[] = (Array.isArray(promo) ? promo : []).map((p) =>
          this.mapPromo(p),
        );

        this.posts = [...normalPosts, ...promoPosts].sort(
          (a, b) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime(),
        );

        this.applyHashtagFilter();
        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        this.errorMsg = 'Failed to load posts.';
        this.loading = false;
      },
    });
  }

  private mapNormal(p: any): FeedPost {
    return {
      postId: Number(p?.postId),
      userId: p?.userId,
      content: p?.content || '',
      createdAt: p?.createdAt,
      updatedAt: p?.updatedAt,
      likeCount: Number(p?.likeCount ?? 0),
      commentCount: Number(p?.commentCount ?? 0),
      profileName: p?.profileName || 'User',
      liked: !!p?.liked,
      isPromoted: false,
    };
  }

  private mapPromo(p: any): FeedPost {
    return {
      postId: Number(p?.id), // promo uses id
      content: p?.content || '',
      createdAt: p?.createdAt,
      likeCount: Number(p?.likeCount ?? 0),
      commentCount: Number(p?.commentCount ?? 0),
      profileName: p?.businessName || 'Promoted',
      liked: !!p?.liked,
      isPromoted: true,
      imageUrl: p?.imageUrl,
      ctaType: p?.ctaType,
      ctaUrl: p?.ctaUrl,
      isPinned: !!p?.isPinned || !!p?.pinned,
    };
  }

  // ---------------- HASHTAG SEARCH ----------------
  applyHashtagFilter(): void {
    const q = this.hashtagQuery.trim().toLowerCase();

    if (!q) {
      this.filteredPosts = [...this.posts];
      return;
    }

    const needle = q.startsWith('#') ? q : `#${q}`;

    this.filteredPosts = this.posts.filter((p) => {
      const text = (p?.content || '').toLowerCase();
      const regex = new RegExp(`(^|\\s)${needle.replace('#', '\\#')}(\\b|\\s|$)`, 'i');
      return regex.test(text);
    });
  }

  clearHashtagSearch(): void {
    this.hashtagQuery = '';
    this.applyHashtagFilter();
  }

  // ---------------- LIKE / UNLIKE ----------------
  onLike(post: FeedPost): void {
    if (this.likingPostIds.has(post.postId)) return;

    this.likingPostIds.add(post.postId);

    if (!post.liked) {
      post.liked = true;
      post.likeCount = (post.likeCount ?? 0) + 1;
    }

    // TODO: call like API here
    // this.postService.likePost(post.postId).subscribe({...})

    setTimeout(() => this.likingPostIds.delete(post.postId), 250);
  }

  onUnlike(post: FeedPost): void {
    if (this.likingPostIds.has(post.postId)) return;

    this.likingPostIds.add(post.postId);

    if (post.liked) {
      post.liked = false;
      post.likeCount = Math.max(0, (post.likeCount ?? 0) - 1);
    }

    // TODO: call unlike API here
    // this.postService.unlikePost(post.postId).subscribe({...})

    setTimeout(() => this.likingPostIds.delete(post.postId), 250);
  }

  // ---------------- COMMENTS MODAL ----------------
  openComments(post: FeedPost): void {
    this.activePost = post;
    this.commentModalOpen = true;
    this.comments = [];
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
    this.commentError = '';

    // ✅ Update this endpoint in service if needed
    this.postService.getCommentsByPost(postId).subscribe({
      next: (data: any) => {
        this.comments = Array.isArray(data) ? data : [];
        // latest first (optional)
        this.comments = this.comments.sort(
          (a, b) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime(),
        );
        this.loadingComments = false;
      },
      error: (err: any) => {
        console.error('Load comments failed', err);
        this.commentError = 'Failed to load comments.';
        this.loadingComments = false;
      },
    });
  }

  addComment(): void {
    const text = this.newCommentText.trim();
    if (!text || !this.activePost) return;

    this.postingComment = true;
    this.commentError = '';

    const body = { content: text };

    // ✅ Update this endpoint in service if needed
    this.postService.addComment(body).subscribe({
      next: (saved: any) => {
        this.postingComment = false;
        this.newCommentText = '';

        this.comments = [saved, ...this.comments];

        this.activePost!.commentCount = (this.activePost!.commentCount ?? 0) + 1;
      },
      error: (err: any) => {
        console.error('Add comment failed', err);
        this.commentError = err?.error?.message || 'Failed to add comment.';
        this.postingComment = false;
      },
    });
  }

  // show delete only if comment belongs to logged in user
  isMyComment(c: any): boolean {
    const uid = Number(c?.userId ?? c?.commentedByUserId ?? c?.ownerId);
    return !!uid && uid === this.currentUserId;
  }

  onDeleteComment(c: any): void {
    const commentId = Number(c?.commentId ?? c?.id);
    if (!commentId || this.deletingCommentIds.has(commentId)) return;

    this.deletingCommentIds.add(commentId);
    this.commentError = '';

    // ✅ Update this endpoint in service if needed
    this.postService.deleteComment(commentId).subscribe({
      next: () => {
        this.comments = this.comments.filter((x) => (x?.commentId ?? x?.id) !== commentId);

        if (this.activePost) {
          this.activePost.commentCount = Math.max(0, (this.activePost.commentCount ?? 0) - 1);
        }

        this.deletingCommentIds.delete(commentId);
      },
      error: (err: any) => {
        console.error('Delete comment failed', err);
        this.commentError = err?.error?.message || 'Failed to delete comment.';
        this.deletingCommentIds.delete(commentId);
      },
    });
  }

  // promo CTA
  openCta(url?: string): void {
    if (!url) return;

    const formattedUrl = url.startsWith('http') ? url : `https://${url}`;
    window.open(formattedUrl, '_blank');
  }
}
