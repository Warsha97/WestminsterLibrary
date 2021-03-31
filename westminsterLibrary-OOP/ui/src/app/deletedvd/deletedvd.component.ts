import { Component, OnInit } from '@angular/core';
import { AppService } from 'src/app/app.service';

@Component({
  selector: 'app-deletedvd',
  templateUrl: './deletedvd.component.html',
  styleUrls: ['./deletedvd.component.css']
})
export class DeletedvdComponent implements OnInit {
  title: any;
  sector: any;
  producer: any;
  actors: any;
  status: any;
  availableSlots: any;

  constructor(private appService: AppService) { }

isbnSelectedDVD:string="";
isbn:string="";


  ngOnInit() {
  }


  showDVD(){
  if(this.isbnSelectedDVD.localeCompare("")==0){
         alert('DVD ISBN FIELD IS REQUIRED!');
                }



     else{
           this.appService.showDeletingDVDData(this.isbnSelectedDVD).subscribe((data:any) => {
                   this.isbn = data.detailArray[0];
                   this.title=data.detailArray[1]
                   this.sector=data.detailArray[2]
                   this.producer=data.detailArray[3]
                   this.actors=data.detailArray[4]

                 });
       }
  }



  deleteDVD(){
      if(this.isbn.localeCompare("")==0){
        alert('INVALID INPUT FOR ISBN OR A CURRENTLY BURROWED DVD');
                     }

           else{
                 this.appService.sendDeleteDVD(this.isbn).subscribe((data:any) => {
                         this.status = data.detailArray[0];
                         this.availableSlots=data.detailArray[1]


                       });
    }
}
}
