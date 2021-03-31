import { Component, OnInit } from '@angular/core';
import { AppService } from 'src/app/app.service';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.css']
})
export class ReportComponent implements OnInit {

year:string="";
month:string="";
day:string="";
minute:string="";
hour:string="";

private getReportDetails='api/generateReport';

reportDetails:any

  constructor(private appService: AppService) { }

  ngOnInit() {
  }

  generateReportAction(){
     if(((this.year.localeCompare("")==0||this.month.localeCompare("")==0)||(this.day.localeCompare("")==0||this.hour.localeCompare("")==0))||this.minute.localeCompare("")==0){
               alert('ALL FIELDS ARE REQUIRED');
                }

     else{
          this.appService.sendRequestForReport(this.getReportDetails,this.day,this.month,this.year,this.minute,this.hour).subscribe((data:any) => {
                  this.reportDetails=data;

                  });
     }
  }

}
