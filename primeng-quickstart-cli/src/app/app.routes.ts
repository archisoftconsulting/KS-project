import { Routes } from '@angular/router';

//import { Step1Component } from './step1/step1.component';
//import { Step2Component } from './step2/step2.component';
//import { Step3Component } from './step3/step3.component';
//import { Step4Component } from './step4/step4.component';

import { StudentComponent } from './student/student.component';
import { UpdateComponent } from './update/update.component';
//import { AddComponent } from './student/add/Add.component';

export const rootRouterConfig: Routes = [
  { path: '', redirectTo: 'student', pathMatch: 'full' },
  { path: 'student', component: StudentComponent },
  { path: 'update', component: UpdateComponent }
];

