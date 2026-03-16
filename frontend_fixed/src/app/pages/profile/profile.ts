import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import {
  faThumbsUp, faThumbsDown, faComment, faShareNodes,
  faBookmark, faPenToSquare, faXmark, faLocationDot,
  faGlobe, faReply, faTrash
} from '@fortawesome/free-solid-svg-icons';
import { Profileservice } from '../../cors/profileService/profileservice';
import { Postservice } from '../../cors/postService/postservice';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, FormsModule, FontAwesomeModule],
  templateUrl: './profile.html',
  styleUrl: './profile.css',
})
export class Profile implements OnInit {
  faThumbsUp=faThumbsUp; faThumbsDown=faThumbsDown; faComment=faComment;
  faShareNodes=faShareNodes; faBookmark=faBookmark; faPenToSquare=faPenToSquare;
  faXmark=faXmark; faLocationDot=faLocationDot; faGlobe=faGlobe;
  faReply=faReply; faTrash=faTrash;

  profile: any = null;
  posts: any[] = []; sharedPosts: any[] = []; savedPosts: any[] = [];
  loadingShared=false; loadingSaved=false;
  activeTab: 'posts'|'saved'|'shared' = 'posts';
  isEditOpen=false; isSaving=false; errorMsg=''; successMsg='';
  editForm: any = { name:'',bio:'',location:'',websiteUrl:'',profilePicUrl:'',privacy:'public' };
  likingPostIds = new Set<number>();
  commentModalOpen=false; activePost: any=null; comments: any[]=[]; newCommentText='';
  postingComment=false; loadingComments=false; commentError='';
  currentUserId = Number(localStorage.getItem('userId'));

  constructor(private profileService: Profileservice, private postService: Postservice) {}

  ngOnInit(): void { this.getUserProfile(); this.getUserPosts(); }

  getUserProfile(): void {
    this.profileService.getUserProfile().subscribe({
      next: (data) => this.profile=data,
      error: (err) => console.error(err),
    });
  }
  getUserPosts(): void {
    this.profileService.getUserPosts().subscribe({
      next: (data) => this.posts=(data||[]).map((p:any)=>({...p,liked:p.liked??false,likeCount:p.likeCount??0,commentCount:p.commentCount??0})),
      error: (err) => console.error(err),
    });
  }
  loadSharedPosts(): void {
    if (this.sharedPosts.length>0) return;
    this.loadingShared=true;
    this.postService.getSharesByUser().subscribe({
      next: (data:any) => { this.sharedPosts=Array.isArray(data)?data:[]; this.loadingShared=false; },
      error: () => this.loadingShared=false,
    });
  }
  loadSavedPosts(): void {
    if (this.savedPosts.length>0) return;
    this.loadingSaved=true;
    try {
      const ids: number[] = JSON.parse(localStorage.getItem('savedPostIds')||'[]');
      if (!ids.length) { this.loadingSaved=false; return; }
      this.postService.getAllPosts().subscribe({
        next: (all:any) => { this.savedPosts=(Array.isArray(all)?all:[]).filter((p:any)=>ids.includes(Number(p.postId))); this.loadingSaved=false; },
        error: () => this.loadingSaved=false,
      });
    } catch { this.loadingSaved=false; }
  }
  setTab(tab:'posts'|'saved'|'shared'): void {
    this.activeTab=tab;
    if (tab==='shared') this.loadSharedPosts();
    if (tab==='saved') this.loadSavedPosts();
  }
  onLike(post: any): void {
    if (!post?.postId||this.likingPostIds.has(post.postId)||post.liked) return;
    this.likingPostIds.add(post.postId); post.liked=true; post.likeCount=(post.likeCount??0)+1;
    this.postService.postLike(post.postId).subscribe({
      next:()=>this.likingPostIds.delete(post.postId),
      error:()=>{ post.liked=false; post.likeCount=Math.max((post.likeCount??1)-1,0); this.likingPostIds.delete(post.postId); },
    });
  }
  onUnlike(post: any): void {
    if (!post?.postId||this.likingPostIds.has(post.postId)||!post.liked) return;
    this.likingPostIds.add(post.postId); post.liked=false; post.likeCount=Math.max((post.likeCount??1)-1,0);
    this.postService.postUnlike(post.postId).subscribe({
      next:()=>this.likingPostIds.delete(post.postId),
      error:()=>{ post.liked=true; post.likeCount=(post.likeCount??0)+1; this.likingPostIds.delete(post.postId); },
    });
  }
  openEditModal(): void {
    if (!this.profile) return;
    this.editForm={name:this.profile.name||'',bio:this.profile.bio||'',location:this.profile.location||'',websiteUrl:this.profile.websiteUrl||'',profilePicUrl:this.profile.profilePicUrl||'',privacy:this.profile.privacy||'public'};
    this.errorMsg=''; this.successMsg=''; this.isEditOpen=true;
  }
  closeEditModal(): void { this.isEditOpen=false; }
  saveProfile(): void {
    this.isSaving=true; this.errorMsg=''; this.successMsg='';
    this.profileService.updateProfile({...this.editForm}).subscribe({
      next:(updated)=>{ this.profile=updated??{...this.profile,...this.editForm}; this.successMsg='Profile updated!'; this.isSaving=false; setTimeout(()=>this.closeEditModal(),500); },
      error:()=>{ this.errorMsg='Failed to update profile.'; this.isSaving=false; },
    });
  }
  formatWebsite(url: string): string { if(!url)return''; return url.startsWith('http')?url:`https://${url}`; }
  openComments(post: any): void {
    this.activePost=post; this.commentModalOpen=true;
    this.newCommentText=''; this.commentError=''; this.loadComments(post.postId);
  }
  closeComments(): void { this.commentModalOpen=false; this.activePost=null; this.comments=[]; this.newCommentText=''; this.commentError=''; }
  loadComments(postId: number): void {
    this.loadingComments=true; this.comments=[];
    this.postService.getCommentsByPost(postId).subscribe({
      next:(res:any)=>{ this.comments=res||[]; this.loadingComments=false; },
      error:()=>{ this.commentError='Failed to load comments.'; this.loadingComments=false; },
    });
  }
  addComment(): void {
    if (!this.activePost||!this.newCommentText.trim()||this.postingComment) return;
    this.postingComment=true; this.commentError='';
    this.postService.addComment({content:this.newCommentText.trim(),userId:this.currentUserId,postId:this.activePost.postId,parentCommentId:null}).subscribe({
      next:(res:any)=>{ this.newCommentText=''; this.postingComment=false; this.activePost.commentCount=(this.activePost.commentCount??0)+1; this.loadComments(this.activePost.postId); },
      error:(err:any)=>{ this.commentError=err?.error?.message||'Failed to post comment.'; this.postingComment=false; },
    });
  }
}
