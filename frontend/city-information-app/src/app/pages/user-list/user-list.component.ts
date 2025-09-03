import { Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { Users } from './users.model';
import { HttpClient } from '@angular/common/http';
import { catchError, delay, map, throwError } from 'rxjs';
import { NotificationService } from '../../notification.service';

@Component({
  selector: 'app-user-list',
  imports: [],
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.css',
})
export class UserListComponent implements OnInit {
  private httpClient = inject(HttpClient);
  private destroyRef = inject(DestroyRef);
  isFetching = signal(true);
  error = signal('');
  allUsersData = signal<Users[] | undefined>(undefined);
  notificationService = inject(NotificationService);

  ngOnInit() {
    this.isFetching.set(true);
    const subscription = this.httpClient
      .get<{ users: Users[] }>('http://localhost:8080/api/userList', {
        withCredentials: true,
      })
      .pipe(
        delay(1000),
        map((response) => response.users),
        catchError((error) => {
          console.log(error);
          return throwError(() => new Error('Something went wrong'));
        })
      )
      .subscribe({
        next: (usersResponse) => {
          console.log(usersResponse);
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


  deleteUserHandler(userId: string){
    console.log("delete " + userId);
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
          this.allUsersData.update(
            (users) => users?.filter((c) => c.id !== userId) ?? []
          );
          this.notificationService.show(
            'success',
            `${resData.user} was deleted!`
          );
        },
      });
  }

}
