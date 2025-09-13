import { Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { Place } from './place.model';
import { catchError, delay, map, throwError } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { NotificationService } from '../../notification.service';

@Component({
  selector: 'app-place-list',
  imports: [],
  templateUrl: './place-list.component.html',
  styleUrl: './place-list.component.css',
})
export class PlaceListComponent implements OnInit {
  isFetching = signal(true);
  placeData = signal<Place[] | undefined>(undefined);
  error = signal('');

  private httpClient = inject(HttpClient);
  private destroyRef = inject(DestroyRef);
  private notificationService = inject(NotificationService);

  ngOnInit() {
    this.isFetching.set(true);
    const subscription = this.httpClient
      .get<{ places: Place[] }>('http://localhost:8080/api/placeList', {
        withCredentials: true,
      })
      .pipe(
        delay(300),
        map((response) => response.places),
        catchError((error) => {
          console.log(error);
          return throwError(() => new Error('Something went wrong'));
        })
      )
      .subscribe({
        next: (placeResponse) => {
          console.log(placeResponse);
          this.placeData.set(placeResponse);
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

  deleteHandler(placeId: string) {
    console.log('ppp ' + placeId);
    const sub = this.httpClient
      .delete<{ message: string }>(
        `http://localhost:8080/api/placeList/${placeId}`,
        {
          withCredentials: true,
        }
      )
      .subscribe({
        next: (resData) => {
          console.log(resData);
          this.placeData.update(
            (places) => places?.filter((p) => p.id !== placeId) ?? []
          );
          this.notificationService.show(
            'success',
            `${resData.message} was deleted!`
          );
        },
      });
  }
}
