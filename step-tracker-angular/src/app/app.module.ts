import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
// tslint:disable-next-line: max-line-length
import { DialogModule } from 'primeng/dialog';
import { HammerModule } from "@angular/platform-browser";
import {NgxMaterialTimepickerModule} from "ngx-material-timepicker";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {
    IgxTimePickerModule,
    IgxInputGroupModule,
    IgxIconModule
} from "igniteui-angular";
import { CommonModule } from '@angular/common';  

import { AppComponent } from './app.component';
import { appRoutingModule } from './app.routing';

import { AuthInterceptor, ErrorInterceptor } from './_helpers';
import { HomeComponent } from './home';
import { LoginComponent } from './login';
import { RegisterComponent } from './register/register.component';
import { AlertComponent } from './alert/alert.component';
import { WorkoutComponent } from './workout/year/workout.component';
import { WorkoutMonthComponent } from './workout/month/workout.component';

@NgModule({
    imports: [
        CommonModule,
        BrowserModule,
        ReactiveFormsModule,
        HttpClientModule,
        FormsModule  ,
        appRoutingModule,
        DialogModule,
        BrowserAnimationsModule, HammerModule, IgxTimePickerModule,
        IgxInputGroupModule,
        IgxIconModule,
        NgxMaterialTimepickerModule
    ],
    declarations: [
        AppComponent,
        HomeComponent,
        LoginComponent,
        RegisterComponent,
        AlertComponent,
        WorkoutComponent,
        WorkoutMonthComponent
    ],
    providers: [
        { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
        { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
        CookieService
        // provider used to create fake backend
    ],
    bootstrap: [AppComponent]
})
export class AppModule { }