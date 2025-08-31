import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../auth.service';
import { NotificationService } from '../../notification.service';

@Component({
  selector: 'app-loginpage',
  imports: [FormsModule],
  templateUrl: './loginpage.component.html',
  styleUrl: './loginpage.component.css',
})
export class LoginpageComponent {
  username = '';
  password = '';

  notificationService = inject(NotificationService);
  public authService = inject(AuthService);
  constructor(private http: HttpClient, private router: Router) {}

  authenticate() {
    console.log('auth');
    console.log(this.username);
    console.log(this.password);

    if (this.username === '' || this.password === '') {
      this.notificationService.show('warning', 'All values should be filled');
      return;
    }

    const body = new HttpParams()
      .set('username', this.username)
      .set('password', this.password);

    this.http
      .post('http://localhost:8080/api/login', body.toString(), {
        withCredentials: true,
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      })
      .subscribe({
        next: (res) => {
          console.log('Login success:', res);
          this.authService.isLoggedIn = true;

          this.authService.setAdminFlag();

          this.notificationService.show(
            'success',
            'Login successfully finished!'
          );
          this.router.navigate(['/welcome']);
        },
        error: (err) => {
          if (err.status === 401) {
            console.error('Unauthorized: Invalid username or password');
            this.notificationService.show(
              'error',
              'Unauthorized: Invalid username or password'
            );
          } else {
            console.error('Something wrong: ', err);
            this.notificationService.show('error', 'Something wrong...');
          }
        },
      });
  }
}
