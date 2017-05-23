import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { RouterModule } from '@angular/router';
import { rootRouterConfig } from './app.routes';

import { InputTextModule, ButtonModule, CalendarModule, TabMenuModule, MenuItem, StepsModule }  from 'primeng/primeng';

import { AppComponent } from './app.component';
import { UpdateComponent } from './update/update.component';
import { StudentComponent } from './student/student.component';

@NgModule({
  declarations: [
    AppComponent,
    UpdateComponent,
    StudentComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpModule,
    InputTextModule, 
    ButtonModule,
	CalendarModule,
	TabMenuModule,
	StepsModule,
	RouterModule.forRoot(rootRouterConfig, { useHash: true })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { 

    items: MenuItem[];
	
	value: Date;

    ngOnInit() {
        this.items = [
            {label: 'Step 1'},
            {label: 'Step 2'},
            {label: 'Step 3'}
        ];
    }

}
