import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { City } from '../city-list/city.model';
import { catchError, delay, forkJoin, map, throwError } from 'rxjs';
import { TypeI } from '../type-list/type.model';
import { NotificationService } from '../../notification.service';

@Component({
  selector: 'app-placepage',
  imports: [FormsModule],
  templateUrl: './placepage.component.html',
  styleUrl: './placepage.component.css',
})
export class PlacePageComponent implements OnInit {
  enteredSelectedCity = '';
  enteredSelectedType = '';
  enteredPlacename = '';
  enteredPlaceAvatar = '';
  enteredPlaceDescription = '';
  isFilledParams = signal(false);
  cityData = signal<City[] | undefined>(undefined);
  error = signal('');
  isFetching = signal(true);
  typeData = signal<TypeI[] | undefined>(undefined);
  errorMessage: string | null = null;

  private destroyRef = inject(DestroyRef);
  private httpClient = inject(HttpClient);
  private notificationService = inject(NotificationService);

  ngOnInit(): void {
    this.isFetching = signal(true);

    const cityRequest = this.httpClient
      .get<{ cities: City[] }>('http://localhost:8080/api/cityList', {
        withCredentials: true,
      })
      .pipe(
        delay(300),
        map((response) => response.cities),
        catchError((error) => {
          console.log(error);
          return throwError(() => new Error('Something went wrong'));
        })
      );

    const typeRequest = this.httpClient
      .get<{ types: TypeI[] }>('http://localhost:8080/api/typeList', {
        withCredentials: true,
      })
      .pipe(
        delay(1000),
        map((response) => response.types),
        catchError((error) => {
          console.log(error);
          return throwError(() => new Error('Something went wrong'));
        })
      );

    const subscription = forkJoin([cityRequest, typeRequest]).subscribe({
      next: ([cities, types]) => {
        // console.log('Cities: ' + JSON.stringify(cities));
        // console.log('Types: ' + JSON.stringify(types));
        this.cityData.set(cities);
        this.typeData.set(types);
      },
      error: (error) => {
        this.error.set(error.message);
        this.isFetching.set(false);
      },
      complete: () => {
        this.isFetching.set(false);
      },
    });

    this.destroyRef.onDestroy(() => {
      subscription.unsubscribe();
    });
  }

  onSubmit() {
    console.log(this.enteredSelectedCity);
    console.log(this.enteredSelectedType);
    console.log(this.enteredPlacename);
    console.log(this.enteredPlaceAvatar);
    console.log(this.enteredPlaceDescription);

    this.httpClient
      .post(
        'http://localhost:8080/api/place',
        {
          city: this.enteredSelectedCity,
          type: this.enteredSelectedType,
          place: this.enteredPlacename,
          path_file: 'test.jpg',
          description: this.enteredPlaceDescription,
        },
        {
          withCredentials: true,
        }
      )
      .pipe(
        catchError((err: HttpErrorResponse) => {
          this.errorMessage = err.error?.message || 'An unknown error occurred';
          console.log(this.errorMessage);

          this.notificationService.show(
            'error',
            this.errorMessage ?? 'An unknown error occurred'
          );

          return throwError(
            () => new Error(this.errorMessage ?? 'An unknown error occurred')
          );
        })
      )
      .subscribe({
        next: (resData) => {
          console.log(resData);
          this.errorMessage = null;
          this.enteredSelectedCity = '';
          this.enteredSelectedType = '';
          this.enteredPlacename = '';
          this.enteredPlaceAvatar = '';
          this.enteredPlaceDescription = '';
          this.notificationService.show('success', 'Added a new place!');
        },
      });
  }
}
