import { Routes, RouterModule } from '@angular/router';

import { HomeComponent } from './home';
import { LoginComponent } from './login';
import { RegisterComponent } from './register';
import { AuthGuard } from './_helpers';
import { WorkoutComponent } from './workout/year/workout.component';
import { WorkoutMonthComponent} from './workout/month/workout.component';


const routes: Routes = [
     { path: '', component: HomeComponent, canActivate: [AuthGuard] },
    { path: 'home', component: HomeComponent },
        { path: 'login', component: LoginComponent },
 { path: 'register', component: RegisterComponent },
 { path: 'year', component: WorkoutComponent },
 { path: 'month', component: WorkoutMonthComponent },

    // otherwise redirect to home
    { path: '**', redirectTo: 'login' }
];

export const appRoutingModule = RouterModule.forRoot(routes);