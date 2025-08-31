import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { City } from './city.model';
import { catchError, delay, map, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { NotificationService } from '../../notification.service';

@Component({
  selector: 'app-city-list',
  imports: [],
  templateUrl: './city-list.component.html',
  styleUrl: './city-list.component.css',
})
export class CityListComponent implements OnInit {
  private httpClient = inject(HttpClient);
  private destroyRef = inject(DestroyRef);
  cityData = signal<City[] | undefined>(undefined);
  isFetching = signal(true);
  error = signal('');
  notificationService = inject(NotificationService);

  constructor(private router: Router) {}

  deleteHandler(cityId: string) {
    const sub = this.httpClient
      .delete<{ city: string }>(
        `http://localhost:8080/api/cityList/${cityId}`,
        {
          withCredentials: true,
        }
      )
      .subscribe({
        next: (resData) => {
          console.log(resData);
          this.cityData.update(
            (cities) => cities?.filter((c) => c.id !== cityId) ?? []
          );
          this.notificationService.show(
            'success',
            `${resData.city} was deleted!`
          );
        },
      });
  }

  editHandler(cityItem: City) {
    this.router.navigate(['/city'], {
      queryParams: {
        id: cityItem.id,
        city: cityItem.city,
        description: cityItem.description,
      },
    });
  }

  ngOnInit() {
    this.isFetching.set(true);
    const subscription = this.httpClient
      .get<{ cities: City[] }>('http://localhost:8080/api/cityList', {
        withCredentials: true,
      })
      .pipe(
        delay(1000),
        map((response) => response.cities),
        catchError((error) => {
          console.log(error);
          return throwError(() => new Error('Something went wrong'));
        })
      )
      .subscribe({
        next: (cityResponse) => {
          console.log(cityResponse);
          this.cityData.set(cityResponse);
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
}
