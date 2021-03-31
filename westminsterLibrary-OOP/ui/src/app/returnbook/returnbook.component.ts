import { Component, OnInit } from '@angular/core';
import { AppService } from 'src/app/app.service';

@Component({
  selector: 'app-returnbook',
  templateUrl: './returnbook.component.html',
  styleUrls: ['./returnbook.component.css']
})
export class ReturnbookComponent implements OnInit {
  errorStatus: string;

  constructor(private appService: AppService) { }

  isbn:string="";
  bookTitle:string;
  //readerName:string;
  burrowDateTime:string;
  readerDetails:string;
  dueDateTime:string;
  isbnReturningBook:string;
  year:string="";
  month:string="";
  day:string="";
  hour:string="";
  minute:string="";

  duration:string;
  fine:string;


  ngOnInit() {
  }

  showDetailsBeforeReturn(){
this.appService.sendReturningBookIsbn(this.isbnReturningBook).subscribe((data:any) => {
           this.isbn=data.detailArray[0];
          this.bookTitle = data.detailArray[1];
          this.burrowDateTime=data.detailArray[2];
          this.readerDetails=data.detailArray[3];
           this.dueDateTime=data.detailArray[4];
        });

  }

  returnAction(){

  if(this.isbn.localeCompare("")==0){
           this.errorStatus="OOPS! You can't complete this action, either due to INVALID INPUT or YOU HAVEN'T SELECTED A BOOK TO RETURN";
           alert('INVALID INPUT OR NOT SELECTED ANY BOOK');
           }

           else if(((this.year.localeCompare("")==0||this.month.localeCompare("")==0)||(this.day.localeCompare("")==0||this.hour.localeCompare("")==0))||this.minute.localeCompare("")==0){
           this.errorStatus="All fields are required!";
           alert('ALL FIELDS ARE REQUIRED');
           }

  else{
  this.appService.sendReturnBook(this.isbn,this.day,this.month,this.year,this.minute,this.hour).subscribe((data:any) => {
             this.duration="burrowed Duration : "+data.detailArray[0];
            this.fine = "Fine : "+data.detailArray[1];

          });
          }
  }



}
