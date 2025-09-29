import { Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { Users } from './users.model';
import { HttpClient } from '@angular/common/http';
import { catchError, delay, map, throwError } from 'rxjs';
import { NotificationService } from '../../notification.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-user-list',
  imports: [FormsModule],
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.css',
})
export class UserListComponent implements OnInit {
  private httpClient = inject(HttpClient);
  private destroyRef = inject(DestroyRef);
  isFetching = signal(true);
  error = signal('');
  notificationService = inject(NotificationService);
  username = '';

  searchData = signal<Users[] | undefined>(undefined);
  allUsersData = signal<Users[] | undefined>(undefined);

  ngOnInit() {
    this.isFetching.set(true);
    const subscription = this.httpClient
      .get<{ users: Users[] }>('http://localhost:8080/api/userList', {
        withCredentials: true,
      })
      .pipe(
        delay(300),
        map((response) => response.users),
        catchError((error) => {
          console.log(error);
          return throwError(() => new Error('Something went wrong'));
        })
      )
      .subscribe({
        next: (usersResponse) => {
          console.log(usersResponse);
          this.searchData.set(usersResponse);
          this.allUsersData.set(usersResponse);
        },
        error: (error) => {
          this.error.set(error.message);
        },
        complete: () => {
          this.isFetching.set(false);
        },
      });

    this.destroyRef.onDestroy(() => {
      subscription.unsubscribe();
    });
  }

  deleteUserHandler(userId: string) {
    console.log('delete ' + userId);
    const sub = this.httpClient
      .delete<{ user: string }>(
        `http://localhost:8080/api/userList/${userId}`,
        {
          withCredentials: true,
        }
      )
      .subscribe({
        next: (resData) => {
          console.log(resData);

          // console.log('to delete: ' + JSON.stringify(this.searchData()));

          this.allUsersData.update(
            (users) => users?.filter((c) => c.id !== userId) ?? []
          );
          this.searchData.update(
            (users) => users?.filter((u) => u.id !== userId) ?? []
          );

          // console.log('sear ' + JSON.stringify(this.searchData()));
          // console.log('all ' + JSON.stringify(this.allUsersData()));

          this.notificationService.show(
            'success',
            `${resData.user} was deleted!`
          );
        },
      });
  }

  searchHandlerUser() {
    if (!this.username) {
      return;
    }

    if (this.username.trim().length < 3) {
      this.notificationService.show('info', `put at least 3 chars`);
      return;
    }

    const filteredUsers = this.allUsersData()?.filter((user) =>
      user.user_id.toLowerCase().includes(this.username.toLowerCase())
    );

    this.searchData.set(filteredUsers);
  }

  onResetForm() {
    this.searchData.set(this.allUsersData());
  }
}
