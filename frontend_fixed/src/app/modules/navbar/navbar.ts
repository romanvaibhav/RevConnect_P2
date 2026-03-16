import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faRightFromBracket } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, FontAwesomeModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar implements OnInit {
  isLoggedIn = false;
  faRightFromBracket = faRightFromBracket;

  constructor(private router: Router) {}

  ngOnInit() {
    this.isLoggedIn = !!localStorage.getItem('userId');
  }

  logout() {
    localStorage.removeItem('userId');
    localStorage.removeItem('token');
    localStorage.removeItem('accountType');
    this.isLoggedIn = false;
    this.router.navigate(['/login']);
  }
}
