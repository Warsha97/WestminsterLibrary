import { Component, OnInit } from '@angular/core';
import { AppService } from '../../app/app.service';
import { AlertsService } from 'angular-alert-module';

@Component({
  selector: 'app-adddvd',
  templateUrl: './adddvd.component.html',
  styleUrls: ['./adddvd.component.css']
})
export class AdddvdComponent implements OnInit {

isbn:string="";
title:string="";
sector:string="";
producer:string="";
actors:string="";
publicationDate:string="";
languages:string="";
subtitles:string="";
postRequestResponse: string;

  constructor(private appService: AppService,private alerts: AlertsService) {

  }

  ngOnInit() {
  }

  saveNewDVD(){
  if((((this.isbn.localeCompare("")==0||this.title.localeCompare("")==0)||(this.sector.localeCompare("")==0||this.producer.localeCompare("")==0))||(this.actors.localeCompare("")==0||this.publicationDate.localeCompare("")==0))||(this.languages.localeCompare("")==0||this.subtitles.localeCompare("")==0)){
        this.postRequestResponse = "PLEASE FILL ALL THE FIELDS!";
     alert('All the fields are required');

  }

  else{
   //this.formInputs.push(this.userName);
   //this.formInputs.push(this.school);
      this.appService.sendDVDData(this.isbn,this.title,this.sector,this.producer,this.actors,this.publicationDate,this.languages,this.subtitles).subscribe((data:any) => {
      //console.log("success!",data);
      //console.log(data);
        this.postRequestResponse = data.content;
      });
      }

  }

}
