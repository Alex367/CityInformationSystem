import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { catchError, throwError } from 'rxjs';
import { NotificationService } from '../../notification.service';

@Component({
  selector: 'app-typepage',
  imports: [FormsModule],
  templateUrl: './typepage.component.html',
  styleUrl: './typepage.component.css',
})
export class TypePageComponent {
  enteredTypename = '';
  enteredTypeAvatar = '';
  enteredDescription = '';
  notificationService = inject(NotificationService);
  errorMessage: string | null = null;

  private httpClient = inject(HttpClient);

  onSubmit() {
    console.log(this.enteredTypename);
    console.log(this.enteredTypeAvatar);
    console.log(this.enteredDescription);

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
          this.notificationService.show('success', 'Added a new type!');
          this.enteredTypename = '';
          this.enteredTypeAvatar = '';
          this.enteredDescription = '';
        },
      });
  }
}
