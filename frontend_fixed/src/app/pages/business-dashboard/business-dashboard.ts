import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import {
  faChartBar, faBoxOpen, faBullhorn, faClock,
  faLink, faPlus, faPenToSquare, faTrash, faThumbTack,
  faXmark, faBuilding, faChartLine
} from '@fortawesome/free-solid-svg-icons';
import { BusinessService } from '../../cors/businessService/business.service';

type DashTab = 'overview'|'products'|'posts'|'hours'|'links';

@Component({
  selector: 'app-business-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule, FontAwesomeModule],
  templateUrl: './business-dashboard.html',
  styleUrl: './business-dashboard.css',
})
export class BusinessDashboard implements OnInit {
  faChartBar=faChartBar; faBoxOpen=faBoxOpen; faBullhorn=faBullhorn;
  faClock=faClock; faLink=faLink; faPlus=faPlus; faPenToSquare=faPenToSquare;
  faTrash=faTrash; faThumbTack=faThumbTack; faXmark=faXmark;
  faBuilding=faBuilding; faChartLine=faChartLine;

  activeTab: DashTab = 'overview';
  userId = Number(localStorage.getItem('userId'));
  businessProfile: any = null; loadingProfile = false;

  products: any[] = []; loadingProducts = false;
  showProductForm = false; productForm: any = { name:'',description:'',price:null,category:'',imageUrl:'',businessProfileId:null };
  editingProductId: number|null = null; productError=''; productSuccess=''; savingProduct=false;

  promoPosts: any[] = []; loadingPosts = false;
  showPostForm = false; postForm: any = { content:'',imageUrl:'',ctaType:'Learn More',ctaUrl:'' };
  editingPostId: number|null = null; postError=''; postSuccess=''; savingPost=false;

  hours: any[] = []; loadingHours = false;
  hoursForm: any = { dayOfWeek:1,openTime:'09:00',closeTime:'18:00',isClosed:false };
  hoursError=''; hoursSuccess=''; savingHours=false;
  dayNames = ['Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'];

  links: any[] = []; loadingLinks = false;
  linkForm: any = { label:'',url:'',linkType:'Social Media' };
  linkError=''; linkSuccess=''; savingLink=false;

  analytics: any = null; analyticsPostId: number|null = null; loadingAnalytics=false; analyticsError='';

  constructor(private bizService: BusinessService) {}
  ngOnInit(): void { this.loadBusinessProfile(); }

  loadBusinessProfile(): void {
    this.loadingProfile=true;
    this.bizService.getBusinessProfile(this.userId).subscribe({
      next:(data)=>{ this.businessProfile=data; this.productForm.businessProfileId=this.userId; this.loadingProfile=false; },
      error:()=>this.loadingProfile=false,
    });
  }

  setTab(tab: DashTab): void {
    this.activeTab=tab;
    if(tab==='products') this.loadProducts();
    if(tab==='posts') this.loadPromoPosts();
    if(tab==='hours') this.loadHours();
    if(tab==='links') this.loadLinks();
  }

  loadProducts(): void {
    this.loadingProducts=true;
    this.bizService.getAllProducts().subscribe({ next:(d:any)=>{ this.products=Array.isArray(d)?d:[]; this.loadingProducts=false; }, error:()=>this.loadingProducts=false });
  }
  openProductForm(p?: any): void {
    this.productForm=p?{...p,businessProfileId:this.userId}:{name:'',description:'',price:null,category:'',imageUrl:'',businessProfileId:this.userId};
    this.editingProductId=p?p.id:null; this.productError=''; this.productSuccess=''; this.showProductForm=true;
  }
  closeProductForm(): void { this.showProductForm=false; this.editingProductId=null; }
  saveProduct(): void {
    if (!this.productForm.name.trim()) { this.productError='Product name is required.'; return; }
    this.savingProduct=true; this.productError=''; this.productSuccess='';
    const obs=this.editingProductId?this.bizService.updateProduct(this.editingProductId,this.productForm):this.bizService.createProduct(this.productForm);
    obs.subscribe({
      next:()=>{ this.productSuccess=this.editingProductId?'Updated!':'Created!'; this.savingProduct=false; this.loadProducts(); setTimeout(()=>this.closeProductForm(),800); },
      error:(err:any)=>{ this.productError=err?.error?.message||'Failed.'; this.savingProduct=false; },
    });
  }
  deleteProduct(id: number): void {
    if (!confirm('Delete this product?')) return;
    this.bizService.deleteProduct(id).subscribe({ next:()=>this.loadProducts(), error:()=>{} });
  }

  loadPromoPosts(): void {
    this.loadingPosts=true;
    this.bizService.getPromotionalPostsByBusiness(this.userId).subscribe({ next:(d:any)=>{ this.promoPosts=Array.isArray(d)?d:[]; this.loadingPosts=false; }, error:()=>this.loadingPosts=false });
  }
  openPostForm(p?: any): void {
    this.postForm=p?{content:p.content||'',imageUrl:p.imageUrl||'',ctaType:p.ctaType||'Learn More',ctaUrl:p.ctaUrl||''}:{content:'',imageUrl:'',ctaType:'Learn More',ctaUrl:''};
    this.editingPostId=p?p.id:null; this.postError=''; this.postSuccess=''; this.showPostForm=true;
  }
  closePostForm(): void { this.showPostForm=false; this.editingPostId=null; }
  savePromoPost(): void {
    if (!this.postForm.content.trim()) { this.postError='Content is required.'; return; }
    this.savingPost=true; this.postError=''; this.postSuccess='';
    const obs=this.editingPostId
      ?this.bizService.updatePromotionalPost(this.editingPostId,this.postForm)
      :this.bizService.createPromotionalPost(this.userId,this.postForm.content,this.postForm.imageUrl,this.postForm.ctaType,this.postForm.ctaUrl);
    obs.subscribe({
      next:()=>{ this.postSuccess=this.editingPostId?'Updated!':'Created!'; this.savingPost=false; this.loadPromoPosts(); setTimeout(()=>this.closePostForm(),800); },
      error:(err:any)=>{ this.postError=err?.error?.message||'Failed.'; this.savingPost=false; },
    });
  }
  deletePromoPost(id: number): void {
    if (!confirm('Delete this post?')) return;
    this.bizService.deletePromotionalPost(id).subscribe({ next:()=>this.loadPromoPosts(), error:()=>{} });
  }
  togglePin(post: any): void {
    const obs=(post.pinned||post.isPinned)?this.bizService.unpinPromotionalPost(post.id):this.bizService.pinPromotionalPost(post.id);
    obs.subscribe({ next:()=>this.loadPromoPosts(), error:()=>{} });
  }
  viewAnalytics(postId: number): void {
    this.analyticsPostId=postId; this.loadingAnalytics=true; this.analyticsError=''; this.analytics=null;
    this.bizService.getPostAnalytics(postId).subscribe({
      next:(d:any)=>{ this.analytics=d; this.loadingAnalytics=false; },
      error:()=>{ this.analyticsError='Failed to load analytics.'; this.loadingAnalytics=false; },
    });
  }

  loadHours(): void {
    this.loadingHours=true;
    this.bizService.getBusinessHours(this.userId).subscribe({ next:(d:any)=>{ this.hours=Array.isArray(d)?d:[]; this.loadingHours=false; }, error:()=>this.loadingHours=false });
  }
  saveHours(): void {
    this.savingHours=true; this.hoursError=''; this.hoursSuccess='';
    this.bizService.setBusinessHours(this.userId,{...this.hoursForm,businessProfileId:this.userId}).subscribe({
      next:()=>{ this.hoursSuccess='Hours saved!'; this.savingHours=false; this.loadHours(); },
      error:(err:any)=>{ this.hoursError=err?.error?.message||'Failed.'; this.savingHours=false; },
    });
  }
  deleteHours(id: number): void {
    if (!confirm('Delete these hours?')) return;
    this.bizService.deleteBusinessHours(id).subscribe({ next:()=>this.loadHours(), error:()=>{} });
  }
  getDayName(day: number): string { return this.dayNames[day]||`Day ${day}`; }

  loadLinks(): void {
    this.loadingLinks=true;
    this.bizService.getExternalLinks(this.userId).subscribe({ next:(d:any)=>{ this.links=Array.isArray(d)?d:[]; this.loadingLinks=false; }, error:()=>this.loadingLinks=false });
  }
  addLink(): void {
    if (!this.linkForm.url.trim()) { this.linkError='URL is required.'; return; }
    this.savingLink=true; this.linkError=''; this.linkSuccess='';
    this.bizService.addExternalLink({...this.linkForm,userId:this.userId}).subscribe({
      next:()=>{ this.linkSuccess='Link added!'; this.savingLink=false; this.linkForm={label:'',url:'',linkType:'Social Media'}; this.loadLinks(); },
      error:(err:any)=>{ this.linkError=err?.error?.message||'Failed.'; this.savingLink=false; },
    });
  }
  deleteLink(id: number): void {
    this.bizService.deleteExternalLink(id).subscribe({ next:()=>this.loadLinks(), error:()=>{} });
  }
}
