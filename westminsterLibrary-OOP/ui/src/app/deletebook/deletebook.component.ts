import { Component, OnInit } from '@angular/core';
import { AppService } from 'src/app/app.service';

@Component({
  selector: 'app-deletebook',
  templateUrl: './deletebook.component.html',
  styleUrls: ['./deletebook.component.css']
})
export class DeletebookComponent implements OnInit {

isbnSelectedBook:string="";
isbn:string="";
  title: any;
  sector: any;
  author: any;
  publisher: any;
  status: any;
  availableSlots: any;

  constructor(private appService: AppService) { }

  ngOnInit() {
  }

  showBook(){
   if(this.isbnSelectedBook.localeCompare("")==0){
       alert('BOOK ISBN FIELD IS REQUIRED!');
              }

   else{
       this.appService.showDeletingBookData(this.isbnSelectedBook).subscribe((data:any) => {
               this.isbn = data.detailArray[0];
               this.title=data.detailArray[1]
               this.sector=data.detailArray[2]
               this.author=data.detailArray[3]
               this.publisher=data.detailArray[4]

             });
   }

  }

  deleteBook(){
      if(this.isbn.localeCompare("")==0){
            alert('INVALID INPUT FOR ISBN OR A CURRENTLY BURROWED BOOK');
                   }

         else{
               this.appService.sendDeleteBook(this.isbn).subscribe((data:any) => {
                       this.status = data.detailArray[0];
                       this.availableSlots=data.detailArray[1]


                     });
  }

}
}
