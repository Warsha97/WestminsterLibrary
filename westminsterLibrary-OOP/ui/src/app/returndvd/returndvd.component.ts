import { Component, OnInit } from '@angular/core';
import { AppService } from 'src/app/app.service';

@Component({
  selector: 'app-returndvd',
  templateUrl: './returndvd.component.html',
  styleUrls: ['./returndvd.component.css']
})
export class ReturndvdComponent implements OnInit {
  duration: string;
  fine: string;

  constructor(private appService: AppService) { }

  isbnReturningDVD:string;
  isbn:string="";
  title:string;
  burrowDateTime:string;
  readerDetails:string;
  dueDateTime:string;

  year:string="";
  month:string="";
  day:string="";
  hour:string="";
  minute:string="";

errorStatus:string;

  ngOnInit() {
  }


       showDetailsBeforeReturn(){
        this.appService.sendReturningDVDIsbn(this.isbnReturningDVD).subscribe((data:any) => {
                   this.isbn=data.detailArray[0];
                  this.title = data.detailArray[1];
                  this.burrowDateTime=data.detailArray[2];
                  this.readerDetails=data.detailArray[3];
                   this.dueDateTime=data.detailArray[4];
                });

       }

       returnAction(){
         if(this.isbn.localeCompare("")==0){
         this.errorStatus="OOPS! You can't complete this action, either due to INVALID INPUT or YOU HAVEN'T SELECTED A DVD TO RETURN";
         alert('INVALID INPUT OR NOT SELECTED ANY DVD');
         }

         else if(((this.year.localeCompare("")==0||this.month.localeCompare("")==0)||(this.day.localeCompare("")==0||this.hour.localeCompare("")==0))||this.minute.localeCompare("")==0){
         this.errorStatus="All fields are required!";
         alert('ALL FIELDS ARE REQUIRED');
         }

         else{
          this.appService.sendReturnDVD(this.isbn,this.day,this.month,this.year,this.minute,this.hour).subscribe((data:any) => {
                      this.duration="burrowed Duration : "+data.detailArray[0];
                     this.fine = "Fine : "+data.detailArray[1];

                   });

         }
       }




}
