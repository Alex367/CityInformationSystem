import { Routes } from '@angular/router';
import { MainpageComponent } from './pages/mainpage/mainpage.component';
import { LoginpageComponent } from './pages/loginpage/loginpage.component';
import { RegistrationpageComponent } from './pages/registrationpage/registrationpage.component';
import { PagenotfoundComponent } from './pages/pagenotfound/pagenotfound.component';
import { WelcomepageComponent } from './pages/welcomepage/welcomepage.component';
import { CityPageComponent } from './pages/citypage/citypage.component';
import { CityListComponent } from './pages/city-list/city-list.component';
import { AuthGuard } from './auth.guard';
import { UserListComponent } from './pages/user-list/user-list.component';

export const routes: Routes = [
  { path: '', component: MainpageComponent },
  { path: 'login', component: LoginpageComponent },
  { path: 'registration', component: RegistrationpageComponent },
  {
    path: 'welcome',
    component: WelcomepageComponent,
    canActivate: [AuthGuard],
    data: { roles: ['ROLE_EMPLOYEE', 'ROLE_ADMIN'] },
  },
  {
    path: 'city',
    component: CityPageComponent,
    canActivate: [AuthGuard],
    data: { roles: ['ROLE_EMPLOYEE', 'ROLE_ADMIN'] },
  },
  {
    path: 'cityList',
    component: CityListComponent,
    canActivate: [AuthGuard],
    data: { roles: ['ROLE_EMPLOYEE', 'ROLE_ADMIN'] },
  },
  {
    path: 'userList',
    component: UserListComponent,
    canActivate: [AuthGuard],
    data: { roles: ['ROLE_ADMIN'], strict: true },
  },
  { path: '**', component: PagenotfoundComponent },
];
