import { inject, Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router } from '@angular/router';
import { AuthService } from './auth.service';
import { catchError, map, Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  private authService = inject(AuthService);
  private router = inject(Router);

  canActivate(route: ActivatedRouteSnapshot): Observable<boolean> {
    const requiredRoles = route.data['roles'] as string[] | undefined;
    const strict = route.data['strict'] as boolean | undefined;

    return this.authService.getUserInfo().pipe(
      map((user: any) => {
        this.authService.setUserInfo(user);

        if (!requiredRoles) {
          return true; // no role restriction
        }

        if (strict) {
          if (this.authService.isAdmin()) {
            return true;
          }
        } else {
          if (
            !requiredRoles ||
            requiredRoles.some((r) => this.authService.hasRole(r))
          ) {
            return true;
          }
        }

        strict ? this.router.navigate(['/']) : this.router.navigate(['/login']);
        return false;
      }),
      catchError((err) => {
        console.log(err);
        this.authService.isLoggedIn = false;

        this.router.navigate(['/login']);
        return of(false);
      })
    );
  }
}
