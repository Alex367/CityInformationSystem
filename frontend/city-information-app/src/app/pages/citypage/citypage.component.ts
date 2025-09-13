import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { NotificationService } from '../../notification.service';
import { AuthService } from '../../auth.service';
import { catchError, throwError } from 'rxjs';

@Component({
  selector: 'app-citypage',
  imports: [FormsModule],
  templateUrl: './citypage.component.html',
  styleUrl: './citypage.component.css',
})
export class CityPageComponent implements OnInit{
  receivedId = '';
  enteredCity = '';
  enteredCountry = '';
  enteredFile = '';
  private httpClient = inject(HttpClient);
  private destroyRef = inject(DestroyRef);
  notificationService = inject(NotificationService);
  isFilledParams = signal(false);
  authService = inject(AuthService);
  errorMessage: string | null = null;

  constructor(private route: ActivatedRoute, private router: Router) {}

  ngOnInit() {
    const subscription = this.route.queryParams.subscribe((params) => {
      const idValue = params['id'];
      const cityValue = params['city'];
      const descriptionValue = params['description'];

      if (cityValue != '' && descriptionValue) {
        this.isFilledParams.set(true);
      }
      this.receivedId = idValue || '';
      this.enteredCity = cityValue || '';
      this.enteredCountry = descriptionValue || '';
    });

    this.destroyRef.onDestroy(() => {
      subscription.unsubscribe();
    });
  }

  onSubmit() {
    if (
      this.enteredCity === '' ||
      this.enteredCountry === '' ||
      this.enteredFile === ''
    ) {
      this.notificationService.show('warning', 'All values should be filled!');
      return;
    }

    // console.log(this.authService.loggedName());
    console.log(this.enteredFile);

    if (!this.isFilledParams()) {
      this.httpClient
        .post(
          'http://localhost:8080/api/city',
          {
            city: this.enteredCity,
            path_file: this.enteredFile,
            description: this.enteredCountry,
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
            this.enteredCity = '';
            this.enteredCountry = '';
            this.enteredFile = '';
            this.notificationService.show('success', 'Added a new city!');
          },
        });
    } else {
      // console.log('PATCHED ENTITY');
      this.httpClient
        .patch(
          'http://localhost:8080/api/city',
          {
            id: this.receivedId,
            city: this.enteredCity,
            path_file: this.enteredFile,
            description: this.enteredCountry,
          },
          {
            withCredentials: true,
          }
        )
        .pipe(
          catchError((err: HttpErrorResponse) => {
            this.errorMessage = err.error?.city || 'An unknown error occurred';
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
            this.notificationService.show(
              'success',
              'Information was changed successfully!'
            );
            this.isFilledParams.set(false);
            this.enteredCity = '';
            this.enteredCountry = '';
            this.enteredFile = '';
            this.router.navigate(['/cityList']);
          },
        });
    }
  }
}
