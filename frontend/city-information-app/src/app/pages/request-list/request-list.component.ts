import { Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../auth.service';
import { HttpClient } from '@angular/common/http';
import { Request } from './request.model';
import { catchError, delay, map, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { SharedDataService } from '../../shared-data.service';
import { NotificationService } from '../../notification.service';

@Component({
  selector: 'app-request-list',
  imports: [FormsModule],
  templateUrl: './request-list.component.html',
  styleUrl: './request-list.component.css',
})
export class RequestListComponent implements OnInit {
  private httpClient = inject(HttpClient);
  private destroyRef = inject(DestroyRef);
  private router = inject(Router);
  private sharedDataService = inject(SharedDataService);
  private notificationService = inject(NotificationService);

  isFetching = signal(true);
  requestData = signal<Request[] | undefined>(undefined);
  error = signal('');
  authService = inject(AuthService);

  ngOnInit(): void {
    this.isFetching.set(true);
    const subscription = this.httpClient
      .get<{ requestList: Request[] }>(
        'http://localhost:8080/api/requestList',
        {
          withCredentials: true,
        }
      )
      .pipe(
        delay(300),
        map((response) => response.requestList),
        catchError((error) => {
          console.log(error);
          return throwError(() => new Error('Something went wrong'));
        })
      )
      .subscribe({
        next: (requestResponse) => {
          console.log(requestResponse);
          this.requestData.set(requestResponse);
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

  deleteRequestHandler(requestId: string) {
    console.log('delete: ' + requestId);
    this.httpClient
      .delete<{ message: string }>(
        `http://localhost:8080/api/request/${requestId}`,
        {
          withCredentials: true,
        }
      )
      .subscribe({
        next: (resData) => {
          console.log(resData);
          this.requestData.update(
            (item) => item?.filter((c) => c.id !== requestId) ?? []
          );
          this.notificationService.show('success', `${requestId} was deleted!`);
        },
      });
  }

  sendResponseHandler(request: Request) {
    this.sharedDataService.setRequestEdit(request);
    this.router.navigate(['/type']);
  }
}
