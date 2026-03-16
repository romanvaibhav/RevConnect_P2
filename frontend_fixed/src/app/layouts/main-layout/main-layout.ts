import { Component } from '@angular/core';
import { Sidebar } from "../../modules/sidebar/sidebar";
import { RouterOutlet } from "@angular/router";
import { Navbar } from "../../modules/navbar/navbar";

@Component({
  selector: 'app-main-layout',
  imports: [Sidebar, RouterOutlet, Navbar],
  templateUrl: './main-layout.html',
  styleUrl: './main-layout.css',
})
export class MainLayout {

}
