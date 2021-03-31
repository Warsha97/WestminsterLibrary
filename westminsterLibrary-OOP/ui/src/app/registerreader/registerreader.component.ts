import { Component, OnInit } from '@angular/core';
import { AppService } from 'src/app/app.service';
import { AlertsService } from 'angular-alert-module';


@Component({
  selector: 'app-registerreader',
  templateUrl: './registerreader.component.html',
  styleUrls: ['./registerreader.component.css']
})
export class RegisterreaderComponent implements OnInit {

newReaderStatus:string;
readerName:string="";
readerContact:string="";
readerEmail:string="";


  constructor(private appService: AppService,private alerts: AlertsService) { }

  ngOnInit() {
  }

  saveNewReader(){
  if((this.readerName.localeCompare("")==0||this.readerContact.localeCompare("")==0)||this.readerEmail.localeCompare("")==0){
        this.newReaderStatus = "PLEASE FILL ALL THE FIELDS!";
     alert('All the fields are required');

  }
  else{
  this.appService.sendReaderData(this.readerName,this.readerContact,this.readerEmail).subscribe((data:any) => {

 this.newReaderStatus = data.content;
      });
      }
  }

}
