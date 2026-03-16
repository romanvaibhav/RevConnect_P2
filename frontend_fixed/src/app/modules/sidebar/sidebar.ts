import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import {
  faHouse, faNewspaper, faUser, faGlobe,
  faPen, faBell, faChartBar
} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterModule, CommonModule, FontAwesomeModule],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.css',
})
export class Sidebar implements OnInit {
  isBusinessUser = false;
  faHouse = faHouse;
  faNewspaper = faNewspaper;
  faUser = faUser;
  faGlobe = faGlobe;
  faPen = faPen;
  faBell = faBell;
  faChartBar = faChartBar;

  ngOnInit(): void {
    const accountType = (localStorage.getItem('accountType') || '').toUpperCase();
    this.isBusinessUser = accountType === 'BUSINESS';
  }
}
