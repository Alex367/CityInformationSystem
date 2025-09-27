import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import {
  Component,
  DestroyRef,
  inject,
  OnDestroy,
  OnInit,
  signal,
} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { catchError, share, throwError } from 'rxjs';
import { NotificationService } from '../../notification.service';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../auth.service';
import { SharedDataService } from '../../shared-data.service';

@Component({
  selector: 'app-typepage',
  imports: [FormsModule],
  templateUrl: './typepage.component.html',
  styleUrl: './typepage.component.css',
})
export class TypePageComponent implements OnInit, OnDestroy {
  receivedId = '';
  enteredTypename = '';
  enteredTypeAvatar = '';
  enteredDescription = '';
  enteredRequest = '';
  enteredStatus = '';
  enteredResponse = '';
  notificationService = inject(NotificationService);
  errorMessage: string | null = null;
  isFilledParams = signal(false);
  authService = inject(AuthService);
  sharedService = inject(SharedDataService);
  responseMode = signal(false);

  statusOptions = [
    { label: 'PENDING', value: 'PENDING' },
    { label: 'REJECTED', value: 'REJECTED' },
    { label: 'ACCEPTED', value: 'ACCEPTED' },
  ];

  private httpClient = inject(HttpClient);
  private router = inject(Router);

  ngOnInit() {
    if (this.sharedService.editTypeData()) {
      this.receivedId = String(this.sharedService.editTypeData()?.id) || '';
      this.enteredTypename = this.sharedService.editTypeData()?.type || '';
      this.enteredTypeAvatar =
        this.sharedService.editTypeData()?.path_file || '';
      this.enteredDescription =
        this.sharedService.editTypeData()?.description || '';
      this.isFilledParams.set(true);
    }
    if (this.sharedService.editRequestData()) {
      this.receivedId = String(this.sharedService.editRequestData()?.id) || '';
      this.enteredTypename = this.sharedService.editRequestData()?.type || '';
      this.enteredTypeAvatar =
        this.sharedService.editRequestData()?.path_file || '';
      this.enteredDescription =
        this.sharedService.editRequestData()?.description || '';
      this.enteredRequest =
        String(this.sharedService.editRequestData()?.request) || '';
      this.enteredStatus = this.sharedService.editRequestData()?.status || '';
      this.responseMode.set(true);
    }
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
              err.error[0]?.message || 'An unknown error occurred';

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
            this.errorMessage =
              err.error[0]?.message || 'An unknown error occurred';

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

  onCreateNewRequest() {
    console.log('new request');

    this.httpClient
      .post(
        'http://localhost:8080/api/request',
        {
          request: this.enteredRequest,
          type: this.enteredTypename,
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
            err.error[0]?.message || 'An unknown error occurred';
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
          this.enteredRequest = '';
          this.enteredTypename = '';
          this.enteredTypeAvatar = '';
          this.enteredDescription = '';

          this.notificationService.show('success', 'Request was sent!');
        },
      });
  }

  sendResponse() {
    if (
      this.enteredResponse == '' ||
      this.enteredTypename === '' ||
      this.enteredTypeAvatar === '' ||
      this.enteredDescription === '' ||
      this.enteredStatus === ''
    ) {
      this.notificationService.show('warning', 'All values should be filled!');
      return;
    }

    if (this.enteredStatus == 'PENDING') {
      this.notificationService.show(
        'warning',
        'Set status to ACCEPT or REJECT'
      );
      return;
    }

    console.log('new response status: ' + this.enteredStatus);

    this.httpClient
      .patch(
        'http://localhost:8080/api/request',
        {
          id: this.receivedId,
          response: this.enteredResponse,
          type: this.enteredTypename,
          path_file: this.enteredTypeAvatar,
          description: this.enteredDescription,
          status: this.enteredStatus,
        },
        {
          withCredentials: true,
        }
      )
      .pipe(
        catchError((err: HttpErrorResponse) => {
          this.errorMessage =
            err.error[0]?.message || 'An unknown error occurred';

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
          this.notificationService.show('success', 'Responded!');
          this.isFilledParams.set(false);
          this.router.navigate(['/requestList']);
        },
      });
  }

  ngOnDestroy() {
    this.sharedService.clearRequest();
    this.responseMode.set(false);
  }

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const file = input.files[0];
      this.enteredTypeAvatar = file.name;
    } else {
      this.enteredTypeAvatar = '';
    }
  }
}
