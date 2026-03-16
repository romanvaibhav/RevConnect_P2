import { Routes } from '@angular/router';
import { Login } from './modules/login/login';
import { Registration } from './modules/registration/registration';
import { Posts } from './pages/posts/posts';
import { AuthLayout } from './layouts/auth-layout/auth-layout';
import { MainLayout } from './layouts/main-layout/main-layout';
import { Profile } from './pages/profile/profile';
import { Networks } from './pages/networks/networks';
import { CreatePost } from './pages/createpost/createpost';
import { Feed } from './pages/feed/feed';
import { Notifications } from './pages/notification/notification';
import { BusinessDashboard } from './pages/business-dashboard/business-dashboard';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
  {
    path: '',
    component: AuthLayout,
    children: [
      { path: 'login', component: Login },
      { path: 'regi', component: Registration },
      { path: '', redirectTo: 'login', pathMatch: 'full' },
    ],
  },
  {
    path: '',
    component: MainLayout,
    canActivate: [authGuard],
    children: [
      { path: 'home', component: Posts, canActivate: [authGuard] },
      { path: 'profile', component: Profile, canActivate: [authGuard] },
      { path: 'networks', component: Networks, canActivate: [authGuard] },
      { path: 'creatpost', component: CreatePost, canActivate: [authGuard] },
      { path: 'feed', component: Feed, canActivate: [authGuard] },
      { path: 'notification', component: Notifications, canActivate: [authGuard] },
      { path: 'business', component: BusinessDashboard, canActivate: [authGuard] },
    ],
  },
  { path: '**', redirectTo: 'login' },
];
