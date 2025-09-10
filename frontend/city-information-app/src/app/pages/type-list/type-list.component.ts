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
import { Router } from '@angular/router';
import { SharedDataService } from '../../shared-data.service';

@Component({
  selector: 'app-type-list',
  imports: [],
  templateUrl: './type-list.component.html',
  styleUrl: './type-list.component.css',
})
export class TypeListComponent implements OnInit {
  isFetching = signal(true);
  error = signal('');
  authService = inject(AuthService);
  typeData = signal<TypeI[] | undefined>(undefined);
  
  private notificationService = inject(NotificationService);
  private destroyRef = inject(DestroyRef);
  private httpClient = inject(HttpClient);
  private router = inject(Router);
  private sharedDataService = inject(SharedDataService);

  ngOnInit() {
    this.isFetching.set(true);
    const subscription = this.httpClient
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

  editHandler(typeItem: TypeI) {
    this.sharedDataService.setTypeEdit(typeItem);
    this.router.navigate(['/type']);
  }
}
