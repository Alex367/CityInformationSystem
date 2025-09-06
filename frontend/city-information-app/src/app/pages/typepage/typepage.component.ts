import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { catchError, throwError } from 'rxjs';
import { NotificationService } from '../../notification.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-typepage',
  imports: [FormsModule],
  templateUrl: './typepage.component.html',
  styleUrl: './typepage.component.css',
})
export class TypePageComponent implements OnInit{
  receivedId = '';
  enteredTypename = '';
  enteredTypeAvatar = '';
  enteredDescription = '';
  notificationService = inject(NotificationService);
  errorMessage: string | null = null;
  isFilledParams = signal(false);
  private destroyRef = inject(DestroyRef);

  private httpClient = inject(HttpClient);

  constructor(private route: ActivatedRoute, private router: Router) {}

  ngOnInit() {
    const subscription = this.route.queryParams.subscribe((params) => {
      const idValue = params['id'];
      const typeValue = params['type'];
      const descriptionValue = params['description'];

      // console.log(typeValue);

      if (
        typeValue === undefined ||
        idValue === undefined ||
        descriptionValue === undefined
      ) {
        this.isFilledParams.set(false);
        return;
      }

      this.receivedId = idValue;
      this.enteredTypename = typeValue;
      this.enteredDescription = descriptionValue;
      this.isFilledParams.set(true);
    });

    this.destroyRef.onDestroy(() => {
      subscription.unsubscribe();
    });
  }

  onSubmit() {
    console.log(this.enteredTypename);
    console.log(this.enteredTypeAvatar);
    console.log(this.enteredDescription);

    if (
      this.enteredTypename === '' ||
      this.enteredTypeAvatar === '' ||
      this.enteredDescription === ''
    ) {
      this.notificationService.show('warning', 'All values should be filled!');
      return;
    }

    if (!this.isFilledParams()) {
      this.httpClient
        .post(
          'http://localhost:8080/api/type',
          {
            typename: this.enteredTypename,
            path_file: this.enteredTypeAvatar,
            description: this.enteredDescription,
          },
          {
            withCredentials: true,
          }
        )
        .pipe(
          catchError((err: HttpErrorResponse) => {
            this.errorMessage =
              err.error?.message || 'An unknown error occurred';
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
            this.notificationService.show('success', 'Added a new type!');
            this.enteredTypename = '';
            this.enteredTypeAvatar = '';
            this.enteredDescription = '';
          },
        });
    } else {
      console.log('Pathed');
      this.httpClient
        .patch(
          'http://localhost:8080/api/type',
          {
            id: this.receivedId,
            type: this.enteredTypename,
            path_file: 'test.jpg',
            description: this.enteredDescription,
          },
          {
            withCredentials: true,
          }
        )
        .pipe(
          catchError((err: HttpErrorResponse) => {
            this.errorMessage = err.error?.typename || 'An unknown error occurred';
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
            this.enteredTypename = '';
            this.enteredTypeAvatar = '';
            this.enteredDescription = '';
            this.router.navigate(['/typeList']);
          },
        });
    }
  }
}
