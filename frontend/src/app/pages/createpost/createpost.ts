import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Postservice } from '../../cors/postService/postservice';
import { Promotionalservice } from '../../cors/promotionalPosts/promotionalservice';

@Component({
  selector: 'app-create-post',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './createpost.html',
  styleUrl: './createpost.css',
})
export class CreatePost implements OnInit {
  postText = '';
  maxLen = 500;

  isPosting = false;
  successMsg = '';
  errorMsg = '';

  postType: 'normal' | 'promo' = 'normal';

  // 🔥 role-based control
  isBusinessUser = false;

  // promotional fields (no manual businessId or productIds)
  imageUrl = '';
  ctaType = 'Learn More';
  ctaUrl = '';

  constructor(
    private postService: Postservice,
    private promoService: Promotionalservice,
  ) {}

  ngOnInit(): void {
    const role = localStorage.getItem('accountType');
    this.isBusinessUser = role === 'BUSINESS';
  }

  get remainingChars(): number {
    return this.maxLen - (this.postText?.length || 0);
  }

  canPost(): boolean {
    const text = this.postText.trim();

    if (!text || this.isPosting || text.length > this.maxLen) {
      return false;
    }

    if (this.postType === 'promo') {
      return !!this.ctaUrl.trim();
    }

    return true;
  }

  onCreatePost(): void {
    this.successMsg = '';
    this.errorMsg = '';

    const text = this.postText.trim();
    if (!text) {
      this.errorMsg = 'Write something before posting.';
      return;
    }

    this.isPosting = true;

    // ================= NORMAL POST =================
    if (this.postType === 'normal') {
      this.postService.createPosts({ content: text }).subscribe({
        next: () => {
          this.postText = '';
          this.successMsg = 'Post created successfully ✅';
          this.isPosting = false;
        },
        error: (err) => {
          this.errorMsg = err?.error?.message || 'Failed to create post.';
          this.isPosting = false;
        },
      });
      return;
    }

    // ================= PROMOTIONAL POST =================
    const businessProfileId = Number(localStorage.getItem('userId'));
    const productIds: number[] = JSON.parse(localStorage.getItem('productIds') || '[]');

    if (!businessProfileId) {
      this.errorMsg = 'Business profile not found.';
      this.isPosting = false;
      return;
    }

    this.promoService
      .createPromotionalPost(
        businessProfileId,
        text,
        this.imageUrl.trim() || '',
        productIds,
        this.ctaType.trim(),
        this.ctaUrl.trim(),
      )
      .subscribe({
        next: () => {
          this.postText = '';
          this.imageUrl = '';
          this.ctaUrl = '';
          this.successMsg = 'Promotional post created successfully ✅';
          this.isPosting = false;
        },
        error: (err: any) => {
          this.errorMsg = err?.error?.message || 'Failed to create promotional post.';
          this.isPosting = false;
        },
      });
  }

  clear(): void {
    this.postText = '';
    this.imageUrl = '';
    this.ctaUrl = '';
    this.successMsg = '';
    this.errorMsg = '';
    this.postType = 'normal';
  }
}
