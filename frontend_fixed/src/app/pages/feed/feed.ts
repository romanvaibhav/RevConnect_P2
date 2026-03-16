import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { forkJoin } from 'rxjs';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import {
  faThumbsUp, faThumbsDown, faComment, faShareNodes,
  faBookmark, faXmark, faTrash, faRotateRight,
  faHashtag, faThumbTack
} from '@fortawesome/free-solid-svg-icons';
import { Postservice } from '../../cors/postService/postservice';

type FeedPost = {
  postId: number; userId?: number; content: string; createdAt: string;
  updatedAt?: string; likeCount: number; commentCount: number;
  profileName: string; liked?: boolean; saved?: boolean;
  isPromoted?: boolean; imageUrl?: string; ctaType?: string;
  ctaUrl?: string; isPinned?: boolean;
};

@Component({
  selector: 'app-feed',
  standalone: true,
  imports: [CommonModule, FormsModule, FontAwesomeModule],
  templateUrl: './feed.html',
  styleUrl: './feed.css',
})
export class Feed implements OnInit {
  faThumbsUp = faThumbsUp; faThumbsDown = faThumbsDown;
  faComment = faComment; faShareNodes = faShareNodes;
  faBookmark = faBookmark; faXmark = faXmark;
  faTrash = faTrash; faRotateRight = faRotateRight;
  faHashtag = faHashtag; faThumbTack = faThumbTack;

  posts: FeedPost[] = []; filteredPosts: FeedPost[] = [];
  loading = false; errorMsg = '';
  likingPostIds = new Set<number>();
  hashtagQuery = '';
  currentUserId = Number(localStorage.getItem('userId'));
  commentModalOpen = false; activePost: FeedPost | null = null;
  comments: any[] = []; loadingComments = false;
  newCommentText = ''; postingComment = false;
  deletingCommentIds = new Set<number>(); commentError = '';
  shareModalOpen = false; sharePost: FeedPost | null = null;
  shareText = ''; sharing = false; shareError = ''; shareSuccess = '';
  savedPostIds = new Set<number>();

  constructor(private postService: Postservice) {}

  ngOnInit(): void {
    try { const a: number[] = JSON.parse(localStorage.getItem('savedPostIds') || '[]');
      this.savedPostIds = new Set(a); } catch {}
    this.loadFeed();
  }

  loadFeed(): void {
    this.loading = true; this.errorMsg = '';
    forkJoin({ normal: this.postService.getAllPosts(), promo: this.postService.getAllPromotionalPosts() }).subscribe({
      next: ({ normal, promo }: any) => {
        const n: FeedPost[] = (Array.isArray(normal) ? normal : []).map((p: any) => this.mapNormal(p));
        const pr: FeedPost[] = (Array.isArray(promo) ? promo : []).map((p: any) => this.mapPromo(p));
        this.posts = [...n, ...pr].sort((a,b) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime());
        this.applyHashtagFilter(); this.loading = false;
      },
      error: (err) => { console.error(err); this.errorMsg = 'Failed to load posts.'; this.loading = false; },
    });
  }

  private mapNormal(p: any): FeedPost {
    return { postId: Number(p?.postId), userId: p?.userId, content: p?.content||'', createdAt: p?.createdAt,
      updatedAt: p?.updatedAt, likeCount: Number(p?.likeCount??0), commentCount: Number(p?.commentCount??0),
      profileName: p?.profileName||'User', liked: false, saved: this.savedPostIds.has(Number(p?.postId)), isPromoted: false };
  }
  private mapPromo(p: any): FeedPost {
    return { postId: Number(p?.id??p?.postId), content: p?.content||'', createdAt: p?.createdAt,
      likeCount: 0, commentCount: 0, profileName: p?.profileName||'Promoted', liked: false, saved: false,
      isPromoted: true, imageUrl: p?.imageUrl, ctaType: p?.ctaType, ctaUrl: p?.ctaUrl,
      isPinned: !!p?.pinned||!!p?.isPinned };
  }

  applyHashtagFilter(): void {
    const q = this.hashtagQuery.trim().toLowerCase();
    if (!q) { this.filteredPosts = [...this.posts]; return; }
    const needle = q.startsWith('#') ? q : `#${q}`;
    this.filteredPosts = this.posts.filter(p => {
      const re = new RegExp(`(^|\\s)${needle.replace('#','\\#')}(\\b|\\s|$)`,'i');
      return re.test((p?.content||'').toLowerCase());
    });
  }
  clearHashtagSearch(): void { this.hashtagQuery = ''; this.applyHashtagFilter(); }

  onLike(post: FeedPost): void {
    if (this.likingPostIds.has(post.postId)||post.liked||post.isPromoted) return;
    this.likingPostIds.add(post.postId); post.liked = true; post.likeCount = (post.likeCount??0)+1;
    this.postService.postLike(post.postId).subscribe({
      next: () => this.likingPostIds.delete(post.postId),
      error: () => { post.liked=false; post.likeCount=Math.max(0,(post.likeCount??1)-1); this.likingPostIds.delete(post.postId); },
    });
  }
  onUnlike(post: FeedPost): void {
    if (this.likingPostIds.has(post.postId)||!post.liked||post.isPromoted) return;
    this.likingPostIds.add(post.postId); post.liked=false; post.likeCount=Math.max(0,(post.likeCount??1)-1);
    this.postService.postUnlike(post.postId).subscribe({
      next: () => this.likingPostIds.delete(post.postId),
      error: () => { post.liked=true; post.likeCount=(post.likeCount??0)+1; this.likingPostIds.delete(post.postId); },
    });
  }
  onSave(post: FeedPost): void {
    if (post.isPromoted) return;
    if (this.savedPostIds.has(post.postId)) { this.savedPostIds.delete(post.postId); post.saved=false; }
    else { this.savedPostIds.add(post.postId); post.saved=true; }
    localStorage.setItem('savedPostIds', JSON.stringify([...this.savedPostIds]));
  }
  openShareModal(post: FeedPost): void {
    if (post.isPromoted) return;
    this.sharePost=post; this.shareText=''; this.shareError=''; this.shareSuccess=''; this.shareModalOpen=true;
  }
  closeShareModal(): void { this.shareModalOpen=false; this.sharePost=null; this.shareText=''; this.shareError=''; this.shareSuccess=''; }
  submitShare(): void {
    if (!this.sharePost||this.sharing) return;
    this.sharing=true; this.shareError=''; this.shareSuccess='';
    this.postService.createShare(this.sharePost.postId, this.shareText.trim()||undefined).subscribe({
      next: () => { this.sharing=false; this.shareSuccess='Post shared!'; setTimeout(()=>this.closeShareModal(),1200); },
      error: (err:any) => { this.sharing=false; this.shareError=err?.error?.message||'Failed to share.'; },
    });
  }
  openComments(post: FeedPost): void {
    this.activePost=post; this.commentModalOpen=true;
    this.comments=[]; this.newCommentText=''; this.commentError=''; this.loadComments(post.postId);
  }
  closeComments(): void { this.commentModalOpen=false; this.activePost=null; this.comments=[]; this.newCommentText=''; this.commentError=''; }
  loadComments(postId: number): void {
    this.loadingComments=true; this.commentError='';
    this.postService.getCommentsByPost(postId).subscribe({
      next: (data:any) => { this.comments=(Array.isArray(data)?data:[]).sort((a:any,b:any)=>new Date(b.createdAt).getTime()-new Date(a.createdAt).getTime()); this.loadingComments=false; },
      error: () => { this.commentError='Failed to load comments.'; this.loadingComments=false; },
    });
  }
  addComment(): void {
    const text=this.newCommentText.trim();
    if (!text||!this.activePost||this.postingComment) return;
    this.postingComment=true; this.commentError='';
    this.postService.addComment({ content:text, userId:this.currentUserId, postId:this.activePost.postId, parentCommentId:null }).subscribe({
      next: (saved:any) => { this.postingComment=false; this.newCommentText=''; this.comments=[saved,...this.comments]; this.activePost!.commentCount=(this.activePost!.commentCount??0)+1; },
      error: (err:any) => { this.commentError=err?.error?.message||'Failed to add comment.'; this.postingComment=false; },
    });
  }
  isMyComment(c: any): boolean { return Number(c?.userId??0)===this.currentUserId; }
  onDeleteComment(c: any): void {
    const id=Number(c?.commentId??c?.id);
    if (!id||this.deletingCommentIds.has(id)) return;
    this.deletingCommentIds.add(id);
    this.postService.deleteComment(id).subscribe({
      next: () => { this.comments=this.comments.filter(x=>(x?.commentId??x?.id)!==id); if(this.activePost) this.activePost.commentCount=Math.max(0,(this.activePost.commentCount??0)-1); this.deletingCommentIds.delete(id); },
      error: (err:any) => { this.commentError=err?.error?.message||'Failed to delete.'; this.deletingCommentIds.delete(id); },
    });
  }
  openCta(url?: string): void {
    if (!url) return; window.open(url.startsWith('http')?url:`https://${url}`,'_blank');
  }
}
