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
import { TypePageComponent } from './pages/typepage/typepage.component';
import { TypeListComponent } from './pages/type-list/type-list.component';
import { PlacePageComponent } from './pages/placepage/placepage.component';
import { PlaceListComponent } from './pages/place-list/place-list.component';
import { RequestListComponent } from './pages/request-list/request-list.component';

export const routes: Routes = [
  { path: '', component: MainpageComponent },
  { path: 'login', component: LoginpageComponent },
  { path: 'registration', component: RegistrationpageComponent },
  {
    path: 'userList',
    component: UserListComponent,
    canActivate: [AuthGuard],
    data: { roles: ['ROLE_ADMIN'], strict: true },
  },
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
    path: 'type',
    component: TypePageComponent,
    canActivate: [AuthGuard],
    data: { roles: ['ROLE_EMPLOYEE', 'ROLE_ADMIN'] },
  },
  {
    path: 'typeList',
    component: TypeListComponent,
    canActivate: [AuthGuard],
    data: { roles: ['ROLE_EMPLOYEE', 'ROLE_ADMIN'] },
  },
  {
    path: 'place',
    component: PlacePageComponent,
    canActivate: [AuthGuard],
    data: { roles: ['ROLE_EMPLOYEE', 'ROLE_ADMIN'] },
  },
  {
    path: 'placeList',
    component: PlaceListComponent,
    canActivate: [AuthGuard],
    data: { roles: ['ROLE_EMPLOYEE', 'ROLE_ADMIN'] },
  },
  {
    path: 'requestList',
    component: RequestListComponent,
    canActivate: [AuthGuard],
    data: { roles: ['ROLE_EMPLOYEE', 'ROLE_ADMIN'] },
  },
  { path: '**', component: PagenotfoundComponent },
];
