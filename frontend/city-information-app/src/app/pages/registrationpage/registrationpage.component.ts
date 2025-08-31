import { HttpClient } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { NotificationService } from '../../notification.service';

@Component({
  selector: 'app-registrationpage',
  imports: [FormsModule],
  templateUrl: './registrationpage.component.html',
  styleUrl: './registrationpage.component.css',
})
export class RegistrationpageComponent {
  username = '';
  password = '';
  password_repeat = '';

  notificationService = inject(NotificationService);

  constructor(private http: HttpClient, private router: Router) {}

  registrationHandler() {
    console.log('registration: ');
    // console.log(this.username);
    // console.log(this.password);

    if (this.password !== this.password_repeat) {
      this.notificationService.show(
        'warning',
        'Try again... Password values are incorrect'
      );
      return;
    } else if (
      this.username === '' ||
      this.password === '' ||
      this.password_repeat === ''
    ) {
      this.notificationService.show('warning', 'All values should be filled');
      return;
    }

    this.http
      .post(
        'http://localhost:8080/api/registration',
        {
          user_id: this.username,
          pw: this.password,
          active: 1,
        },
        {
          withCredentials: true,
          headers: { 'Content-Type': 'application/json' },
        }
      )
      .subscribe({
        next: (res) => {
          console.log(res);
          this.notificationService.show(
            'success',
            'Registration successfully finished!'
          );
          this.router.navigate(['/login']);
        },
        error: (err) => {
          this.notificationService.show('error', 'Something wrong...');
        },
      });
  }
}
