import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faPaperPlane, faEraser, faBullhorn, faPen } from '@fortawesome/free-solid-svg-icons';
import { Postservice } from '../../cors/postService/postservice';
import { Promotionalservice } from '../../cors/promotionalPosts/promotionalservice';

@Component({
  selector: 'app-create-post',
  standalone: true,
  imports: [CommonModule, FormsModule, FontAwesomeModule],
  templateUrl: './createpost.html',
  styleUrl: './createpost.css',
})
export class CreatePost implements OnInit {
  faPaperPlane = faPaperPlane;
  faEraser = faEraser;
  faBullhorn = faBullhorn;
  faPen = faPen;

  postText = '';
  maxLen = 500;
  isPosting = false;
  successMsg = '';
  errorMsg = '';
  postType: 'normal' | 'promo' = 'normal';
  isBusinessUser = false;
  imageUrl = '';
  ctaType = 'Learn More';
  ctaUrl = '';

  constructor(private postService: Postservice, private promoService: Promotionalservice) {}

  ngOnInit(): void {
    this.isBusinessUser = (localStorage.getItem('accountType') || '') === 'BUSINESS';
  }

  get remainingChars(): number { return this.maxLen - (this.postText?.length || 0); }

  canPost(): boolean {
    const text = this.postText.trim();
    if (!text || this.isPosting || text.length > this.maxLen) return false;
    if (this.postType === 'promo') return !!this.ctaUrl.trim();
    return true;
  }

  onCreatePost(): void {
    this.successMsg = ''; this.errorMsg = '';
    const text = this.postText.trim();
    if (!text) { this.errorMsg = 'Write something before posting.'; return; }
    this.isPosting = true;

    if (this.postType === 'normal') {
      this.postService.createPosts({ content: text }).subscribe({
        next: () => { this.postText = ''; this.successMsg = 'Post created successfully!'; this.isPosting = false; },
        error: (err) => { this.errorMsg = err?.error?.message || 'Failed to create post.'; this.isPosting = false; },
      });
      return;
    }

    const businessProfileId = Number(localStorage.getItem('userId'));
    const productIds: number[] = JSON.parse(localStorage.getItem('productIds') || '[]');
    if (!businessProfileId) { this.errorMsg = 'Business profile not found.'; this.isPosting = false; return; }

    this.promoService.createPromotionalPost(businessProfileId, text, this.imageUrl.trim() || '', productIds, this.ctaType.trim(), this.ctaUrl.trim()).subscribe({
      next: () => { this.postText = ''; this.imageUrl = ''; this.ctaUrl = ''; this.successMsg = 'Promotional post created!'; this.isPosting = false; },
      error: (err: any) => { this.errorMsg = err?.error?.message || 'Failed to create promotional post.'; this.isPosting = false; },
    });
  }

  clear(): void {
    this.postText = ''; this.imageUrl = ''; this.ctaUrl = '';
    this.successMsg = ''; this.errorMsg = ''; this.postType = 'normal';
  }
}
