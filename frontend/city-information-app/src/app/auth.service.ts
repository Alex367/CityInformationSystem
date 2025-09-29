import { HttpClient } from '@angular/common/http';
import { Injectable, signal } from '@angular/core';

interface UserInfoResponse {
  username: string;
  roles: { authority: string }[];
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  public isLoggedIn = false;
  public roles = signal<string[]>([]);
  public isAdmin = signal<boolean>(false);
  public loggedName = signal<string>('');

  constructor(private http: HttpClient) {}

  getUserInfo() {
    return this.http.get<UserInfoResponse>(
      'http://localhost:8080/api/user-info',
      {
        withCredentials: true, // important to send JSESSIONID cookie
      }
    );
  }

  setUserInfo(user: UserInfoResponse) {
    this.isLoggedIn = true;
    this.roles.set(user.roles.map((r) => r.authority)); // ["ROLE_EMPLOYEE"]
    this.loggedName.set(user.username);
    this.setAdminFlag();
  }

  hasRole(role: string): boolean {
    return this.roles().includes(role);
  }

  public setAdminFlag() {
    if (this.roles().includes('ROLE_ADMIN')) {
      this.isAdmin.set(true);
    }
  }
}
