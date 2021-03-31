import { Component, OnInit } from '@angular/core';
import { AppService } from '../../app/app.service';
import { AlertsService } from 'angular-alert-module';

@Component({
  selector: 'app-addbook',
  templateUrl: './addbook.component.html',
  styleUrls: ['./addbook.component.css']
})
export class AddbookComponent implements OnInit {

isbn:string="";
title:string="";
sector:string="";
authors:string="";
publisher:string="";
publicationDate:string="";
numOfPages:string="";
 postRequestResponse: string;


  constructor(private appService: AppService,private alerts: AlertsService) {
  }

  ngOnInit() {
  }

public saveNewBook(): void {
if((((this.isbn.localeCompare("")==0||this.title.localeCompare("")==0)||(this.sector.localeCompare("")==0||this.authors.localeCompare("")==0))||(this.publisher.localeCompare("")==0||this.publicationDate.localeCompare("")==0))||this.numOfPages.localeCompare("")==0){
      this.postRequestResponse = "PLEASE FILL ALL THE FIELDS!";
   alert('All the fields are required');

}

else{
    this.appService.sendBookData(this.isbn,this.title,this.sector,this.authors,this.publisher,this.publicationDate,this.numOfPages).subscribe((data:any) => {
    this.postRequestResponse = data.content;
    });
    }


  }

}
