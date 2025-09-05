import { HttpClient } from '@angular/common/http';
import {
  Component,
  DestroyRef,
  inject,
  OnInit,
  signal,
  Type,
} from '@angular/core';
import { catchError, delay, map, throwError } from 'rxjs';
import { MessageResponse, TypeI } from './type.model';
import { AuthService } from '../../auth.service';
import { NotificationService } from '../../notification.service';

@Component({
  selector: 'app-type-list',
  imports: [],
  templateUrl: './type-list.component.html',
  styleUrl: './type-list.component.css',
})
export class TypeListComponent implements OnInit {
  isFetching = signal(true);
  private httpClient = inject(HttpClient);
  error = signal('');
  private destroyRef = inject(DestroyRef);
  authService = inject(AuthService);
  notificationService = inject(NotificationService);

  typeData = signal<TypeI[] | undefined>(undefined);

  ngOnInit() {
    this.isFetching.set(true);
    const subscription = this.httpClient
      .get<{ message: TypeI[] }>('http://localhost:8080/api/typeList', {
        withCredentials: true,
      })
      .pipe(
        delay(1000),
        map((response) => response.message),
        catchError((error) => {
          console.log(error);
          return throwError(() => new Error('Something went wrong'));
        })
      )
      .subscribe({
        next: (typesResponse) => {
          console.log(typesResponse);
          this.typeData.set(typesResponse);
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

  deleteHandler(typeId: number) {
    console.log('type ' + typeId);
    const sub = this.httpClient
      .delete<{ deletedType: string }>(
        `http://localhost:8080/api/typeList/${typeId}`,
        {
          withCredentials: true,
        }
      )
      .subscribe({
        next: (resData) => {
          console.log(resData);
          this.typeData.update(
            (item) => item?.filter((c) => c.id !== typeId) ?? []
          );
          this.notificationService.show(
            'success',
            `${resData.deletedType} was deleted!`
          );
        },
      });
  }

  editHandler(typeItem: TypeI) {}
}
