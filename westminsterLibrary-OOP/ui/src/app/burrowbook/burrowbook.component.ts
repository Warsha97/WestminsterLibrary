import { Component, OnInit } from '@angular/core';
import { AppService } from 'src/app/app.service';

@Component({
  selector: 'app-burrowbook',
  templateUrl: './burrowbook.component.html',
  styleUrls: ['./burrowbook.component.css']
})
export class BurrowbookComponent implements OnInit {

isbnSelectedBook:string;

availability:string;
willBeAvailable:string;
currentUserId:string;
currentUserName:string;
currentUserNumber:string;
currentUserEmail:string;
isbn:string="";
title:string;
sector:string;

burrowingStatus:string;
newReaderId:string;
newReaderName:string;
newReaderNumber:string;
newReaderEmail:string;
dueDate:string;

txtReaderId:string="";
year:string="";
month:string="";
day:string="";
hour:string="";
minute:string="";
  errorStatus: string;


  constructor(private appService: AppService) { }

  ngOnInit() {
  }

  showBook(){
  this.appService.sendSelectedBookData(this.isbnSelectedBook).subscribe((data:any) => {
        this.availability = data.detailArray[0];
        this.willBeAvailable= data.detailArray[1];
        this.currentUserId= data.detailArray[2];
        this.currentUserName= data.detailArray[3];
        this.currentUserNumber= data.detailArray[4];
        this.currentUserEmail= data.detailArray[5];
        this.isbn= data.detailArray[6];
        this.title= data.detailArray[7];
        this.sector= data.detailArray[8];
      });
  }

  burrowBook(){

  if(this.isbn.localeCompare("")==0){
    this.errorStatus = "OOPS! you can't complete this action. Either due to UNAVAILABILITY or you HAVEN'T SELECTED A DVD";
         alert('UNAVAILABLE DVD OR NOT SELECTED ANY DVD');
            }

    else if(((this.txtReaderId.localeCompare("")==0||this.year.localeCompare("")==0)||(this.month.localeCompare("")==0||this.day.localeCompare("")==0))||(this.hour.localeCompare("")==0||this.minute.localeCompare("")==0)){
            this.errorStatus = "PLEASE FILL ALL THE FIELDS!";
         alert('All the fields are required');

      }


  else{
   this.appService.sendBurrowBookData(this.isbnSelectedBook,this.txtReaderId,this.year,this.month,this.day,this.hour,this.minute).subscribe((data:any) => {
          this.burrowingStatus = data.detailArray[0];
          this.dueDate=data.detailArray[1]
          this.newReaderId=data.detailArray[2]
          this.newReaderName=data.detailArray[3]
          this.newReaderNumber=data.detailArray[4]
          this.newReaderEmail=data.detailArray[5]

        });
        }
  }

}
