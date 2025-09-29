import { Component, inject, OnInit } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../auth.service';
import { HttpClient } from '@angular/common/http';
import { NotificationService } from '../notification.service';

@Component({
  selector: 'app-header',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
})
export class HeaderComponent implements OnInit {

  notificationService = inject(NotificationService);
  authService = inject(AuthService);

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit(): void {}

  logoutHandler() {
    this.http
      .post('http://localhost:8080/api/logout', {}, { withCredentials: true })
      .subscribe({
        next: (res) => {
          console.log('Logout success.');
          this.notificationService.show('success', 'You are logged out successfully!');
          this.authService.isLoggedIn = false;
          this.authService.isAdmin.set(false);
          this.authService.roles.set([]);
          this.router.navigate(['/']);
          this.authService.loggedName.set("");
        },
        error: (err) => {
          // console.log("Logout error")
          this.notificationService.show('error', 'Something wrong...');
        },
      });
  }
}
