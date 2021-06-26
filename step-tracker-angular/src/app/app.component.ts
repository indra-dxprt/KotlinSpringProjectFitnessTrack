import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { AuthenticationService, UserService } from './_services';

import { NewLogin } from './Users/newlogin';
import {BaseAPIService} from './_services/base.service';
import { Observable } from 'rxjs';

@Component({ selector: 'app', templateUrl: 'app.component.html' })
export class AppComponent implements OnInit {
    currentUser: boolean=true;
    isLoggedIn$: Observable<boolean>;
    constructor(
        private router: Router,
        private authenticationService: AuthenticationService,
        private userService: UserService
    ) {
        this.authenticationService.currentUser.subscribe(x => this.currentUser = false);
    }
    ngOnInit(): void {
        this.isLoggedIn$ = this.userService.isUserLoggedIn;
    }


    logout() {
        this.userService.setLoginStatus(false);
        this.authenticationService.logout();
        this.router.navigate(['/login']);
    }
}