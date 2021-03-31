import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HTTP_INTERCEPTORS, HttpClientModule, HttpClientXsrfModule } from '@angular/common/http';
//import { Ng2SmartTableModule } from 'ng2-smart-table';

import { FormsModule } from '@angular/forms';
import { AlertsModule } from 'angular-alert-module';
//import { ConfirmationDialogService } from './confirmation-dialog/confirmation-dialog.service';

import { AppComponent } from './app.component';
import { RouteExampleComponent } from './route-example/route-example.component';

import { AppService } from './app.service';
import { AppHttpInterceptorService } from './http-interceptor.service';
import { AppRoutingModule } from './/app-routing.module';

import { AddComponent } from './add/add.component';


import { BurrowComponent } from './burrow/burrow.component';
import { BurrowbookComponent } from './burrowbook/burrowbook.component';
import { BurrowdvdComponent } from './burrowdvd/burrowdvd.component';

import { DeleteComponent } from './delete/delete.component';
import { ReturnComponent } from './return/return.component';
import { DisplayComponent } from './display/display.component';
import { ReportComponent } from './report/report.component';
import { AddbookComponent } from './addbook/addbook.component';
import { AdddvdComponent } from './adddvd/adddvd.component';
import { RegisterreaderComponent } from './registerreader/registerreader.component';
import { ReturnbookComponent } from './returnbook/returnbook.component';
import { ReturndvdComponent } from './returndvd/returndvd.component';

import { DeletebookComponent } from './deletebook/deletebook.component';
import { DeletedvdComponent } from './deletedvd/deletedvd.component';



const routes: Routes = [
  {
    path: 'java',
    component: RouteExampleComponent,
    data: { technology: 'Java' },
  },
   {
      path: 'registerreader',
      component: RegisterreaderComponent,

    },

    {
    path:'viewreport',
    component: ReportComponent,
    },

{
    path: 'add',
    component: AddComponent,
    children:[{path:'addbook', component:AddbookComponent},//defining child routes
               {path:'adddvd', component:AdddvdComponent},
    ],

    },

    {
        path: 'delete',
        component: DeleteComponent,
        children:[{path:'deletebook', component:DeletebookComponent},//defining child routes
                   {path:'deletedvd', component:DeletedvdComponent},
        ],

        },

    {
        path: 'return',
        component: ReturnComponent,
        children:[{path:'returnbook', component:ReturnbookComponent},//defining child routes
                   {path:'returndvd', component:ReturndvdComponent},
        ],

        },



    {
     path: 'burrow',
     component: BurrowComponent,
     children:[{path:'burrowbook', component:BurrowbookComponent},//defining child routes
                {path:'burrowdvd', component:BurrowdvdComponent},
         ],
    },


    {
        path: 'display',
        component: DisplayComponent,


       },


  {
    path: 'play',
    component: RouteExampleComponent,
    data: { technology: 'Play' },
  },
  {
    path: 'angular',
    component: RouteExampleComponent,
    data: { technology: 'Angular' },
  },
  {
    path: '**',
    redirectTo: '/play',
    pathMatch: 'full',
  },
];

@NgModule({
  declarations: [
    AppComponent,
    RouteExampleComponent,
    AddComponent,
    BurrowComponent,
    BurrowbookComponent,
    BurrowdvdComponent,
    DeleteComponent,
    ReturnComponent,
    DisplayComponent,
    ReportComponent,
    AddbookComponent,
    AdddvdComponent,
    RegisterreaderComponent,
    ReturnbookComponent,
    ReturndvdComponent,

    DeletebookComponent,
    DeletedvdComponent,


  ],
  imports: [
   FormsModule,
   AlertsModule.forRoot(),
    BrowserModule,
    HttpClientModule,
    HttpClientXsrfModule.withOptions({
      cookieName: 'Csrf-Token',
      headerName: 'Csrf-Token',
    }),
    RouterModule.forRoot(routes),
    AppRoutingModule,
  ],
  providers: [
 // ConfirmationDialogService,
    AppService,
    {
      multi: true,
      provide: HTTP_INTERCEPTORS,
      useClass: AppHttpInterceptorService,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {
}
