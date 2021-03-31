import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { map } from 'rxjs/operators';
import { Observable } from 'rxjs/index';

/**
 * Class representing application service.
 *
 * @class AppService.
 */
@Injectable()

export class AppService {

 //all the urls
  private serviceUrl = '/api/summary';
  private dataPostTestUrl = '/api/postTest';
  private addNewBookUrl = 'api/addNewBook';
  private addNewDVDUrl = 'api/addNewDVD';
  private showDeletingBookUrl = 'api/showDeletingBook';
  private deleteBookUrl = 'api/deleteBook';
  private showDeletingDVDUrl = 'api/showDeletingDVD';
  private deleteDVDUrl = 'api/deleteDVD';
  private displayBooksUrl = 'api/displayBooks';
  private registerReaderUrl = 'api/registerReader';
  private showSelectedBookUrl = 'api/showSelectedBook';
  private burrowBookUrl = 'api/burrowBook';
  private showSelectedDVDUrl = 'api/showSelectedDVD';
  private burrowDVDUrl = '/api/burrowDVD ';
  private showReturningBookUrl = 'api/showReturningBook';
  private returnBookUrl     = 'api/returnBook';
  private showReturningDVDUrl = 'api/showReturningDVD';
  private ReturnDVDUrl = 'api/ReturnDVD';
  private generateReportUrl = 'api/generateReport';



  constructor(private http: HttpClient) {
  }


/**
   * Makes a http get request to retrieve the welcome message from the backend service.
   */
  public getWelcomeMessage() {
    return this.http.get(this.serviceUrl).pipe(
      map(response => response)
    );
  }

  /**
   * Makes a http post request to send some data to backend & get response.
   */
  public sendData(): Observable<any> {
    return this.http.post(this.dataPostTestUrl, {});
  }



//sending post request for reader registering data
public sendReaderData(readerName:string,readerContact:string,readerEmail:string): Observable<any> {
              return this.http.post(this.registerReaderUrl, readerName+":"+readerContact+":"+readerEmail,{});
            }


//sending data of selected book for deleting
public showDeletingBookData(isbn:string): Observable<any> {
              return this.http.post(this.showDeletingBookUrl, isbn,{});
            }

//sending data for confirmed deleting of a book
public sendDeleteBook(isbn:string): Observable<any> {
              return this.http.post(this.deleteBookUrl, isbn,{});
              }

//sending data of selected DVD for deleting
public showDeletingDVDData(isbn:string): Observable<any> {
               return this.http.post(this.showDeletingDVDUrl, isbn,{});
             }

//sending data for confirmed deleting of a DVD
public sendDeleteDVD(isbn:string): Observable<any> {
               return this.http.post(this.deleteDVDUrl, isbn,{});
               }


//sending data of selected book for burrowing
public sendSelectedBookData(selectedIsbn:string): Observable<any> {
                 return this.http.post(this.showSelectedBookUrl, selectedIsbn,{});
               }

//sending data of selected DVD for burrowing
public sendSelectedDVDData(selectedIsbn:string): Observable<any> {
                 return this.http.post(this.showSelectedDVDUrl, selectedIsbn,{});
               }

//sending data for confirmed burrowing of a book
public sendBurrowBookData(isbn:string,readerId:string,burrowedYear:string,burrowedMonth:string,burrowedDay:string,burrowedHour:string,burrowedMinute:string): Observable<any> {
                    return this.http.post(this.burrowBookUrl, isbn+"@"+readerId+"@"+burrowedYear+"@"+burrowedMonth+"@"+burrowedDay+"@"+burrowedHour+"@"+burrowedMinute,{});
                  }

//sending data for confirmed burrowing of a book
public sendBurrowDVDData(isbn:string,readerId:string,burrowedYear:string,burrowedMonth:string,burrowedDay:string,burrowedHour:string,burrowedMinute:string): Observable<any> {
                     return this.http.post(this.burrowDVDUrl, isbn+"@"+readerId+"@"+burrowedYear+"@"+burrowedMonth+"@"+burrowedDay+"@"+burrowedHour+"@"+burrowedMinute,{});
                   }

//sending data for selection of reterning book.
public sendReturningBookIsbn(returningIsbn:string): Observable<any> {
                 return this.http.post(this.showReturningBookUrl, returningIsbn,{});
               }

//sending data for selection of reterning book
 public sendReturningDVDIsbn(returningIsbn:string): Observable<any> {
                   return this.http.post(this.showReturningDVDUrl, returningIsbn,{});
                 }

//confirmed return Book data sending
public sendReturnBook(returningIsbn:string,returningDay:string,returningMonth:string,returningYear:string,returningMin:string,returningHour:string): Observable<any> {
                  return this.http.post(this.returnBookUrl, returningIsbn+"@"+returningDay+"@"+returningMonth+"@"+returningYear+"@"+returningMin+"@"+returningHour,{});
                }

//confirmed return DVD data sending
public sendReturnDVD(returningIsbn:string,returningDay:string,returningMonth:string,returningYear:string,returningMin:string,returningHour:string): Observable<any> {
                    return this.http.post(this.ReturnDVDUrl, returningIsbn+"@"+returningDay+"@"+returningMonth+"@"+returningYear+"@"+returningMin+"@"+returningHour,{});
                  }

//sending data for adding a new book
public sendBookData(isbn:string,title:string,sector:string,authors:string,publisher:string,publicationDate:string,numOfPages:string): Observable<any> {
        return this.http.post(this.addNewBookUrl, isbn+"@"+title+"@"+sector+"@"+authors+"@"+publisher+"@"+publicationDate+"@"+numOfPages,{});
      }

//sending data for adding a new DVD
public sendDVDData(isbn:string,title:string,sector:string,producer:string,actors:string,publicationDate:string,languages:string,subtitles:string): Observable<any> {
          return this.http.post(this.addNewDVDUrl, isbn+"@"+title+"@"+sector+"@"+producer+"@"+actors+"@"+publicationDate+"@"+languages+"@"+subtitles,{});
        }

//sending data to display book
public sendRequestToDisplayBooks(displayBooksUrl:any): Observable<any> {
     return this.http.post(this.displayBooksUrl, {});
   }


//sending data to generate report
public sendRequestForReport(generateReportUrl:any,year:string,month:string,day:string,minute:string,hour:string): Observable<any> {
     return this.http.post(this.generateReportUrl, year+"@"+month+"@"+day+"@"+minute+"@"+hour, {});
   }

}
