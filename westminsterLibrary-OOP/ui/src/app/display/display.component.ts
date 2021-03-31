import { Component, OnInit } from '@angular/core';
import { AppService } from 'src/app/app.service';

@Component({
  selector: 'app-display',
  templateUrl: './display.component.html',
  styleUrls: ['./display.component.css']
})
export class DisplayComponent implements OnInit {


private getBookDetails='api/displayBooks';

bookDetails:any;


  constructor(private appService: AppService) { }

//since this is inside ngOnIt, the display function with generate in the application starts

  ngOnInit() {

  //sending a post http request to retrieve data from the displayItems.java class

     this.appService.sendRequestToDisplayBooks(this.getBookDetails).subscribe((data:any) => {
      this.bookDetails = data;
         });
}



}
