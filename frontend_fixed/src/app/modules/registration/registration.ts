import { Component } from '@angular/core';
import { Service } from '../../cors/service/service';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-registration',
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './registration.html',
  styleUrl: './registration.css',
})
export class Registration {
  constructor(private authService: Service) {}
  regiForm: FormGroup = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    username: new FormControl('', [Validators.required]),
    accountType: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required, Validators.minLength(8)]),
  });
  adminRegistration() {
    if (this.regiForm.valid) {
      console.log('Form Submitted:', this.regiForm.value);
      this.authService.postRegistration(this.regiForm.value).subscribe({
        next: (value) => {
          console.log('Succesfully registered', value);
        },
        error: (err) => {
          console.log('Getting error at registration', err);
        },
      });
    } else {
      console.log('Form is Invalid');
    }
  }
}
