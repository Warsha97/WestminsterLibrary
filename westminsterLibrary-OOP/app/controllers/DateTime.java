package controllers;

//this class provides methods to manipulate date and time attributes and to return needed outputs
public class DateTime {

    //declaring private variables (encapsulation)
    private int day;
    private int month;
    private int year;
    private int hour;
    private int minute;

    //constructor
    public DateTime(int minute,int hour, int day,int month,int year){
     this.minute=minute;
     this.hour=hour;
     this.day=day;
     this.month=month;
     this.year=year;
    }

   //setter for all the attributes in a single method
    public void setDate(int day,int month,int year){
        this.day=day;
        this.month=month;
        this.year=year;
    }

    //getDate method to return a String date in a formatted pattern (dd/mm/yyy)
    public String getDate(){
        String d="";
        String m="";
        if(day<10){
            d=0+(String.valueOf(day));
        }
        else{
            d=String.valueOf(day);
        }

        if(month<10){
            m=0+(String.valueOf(month));
        }
        else{
            m=String.valueOf(month);
        }
        //returning day/month/year
        return (d+"/"+m+"/"+String.valueOf(year));
    }



    //getTime method to return a String time in a formatted pattern (hh:mm)
    public String getTime(){
        String mt="";
        String hr="";
        if(minute<10){
            mt=0+(String.valueOf(minute));
        }

        else{
            mt=String.valueOf(minute);
        }

        if(hour<10){
            hr=0+(String.valueOf(hour));
        }
        else{
            hr=String.valueOf(hour);
        }
        //return hour:minute
        return (hr+":"+mt);
    }

    //this method provides the logic to calculate the returning date of an Item once the burrowed date is given
    //possible day count is the number of days an item can be kept. Book=7, DVD=3 (as to the requirement)
    public String getBookReturningDate(int possibleDayCount){
        boolean leapYear=false;
        int dayLimit=0;
        int lastDayOfMonth=0;
        String d="";
        String m="";
        String y="";
        String returningDate="";
        if ((year%4==0&&year%100==0)||year%400==0){
            leapYear=true;
        }

        switch (month){
            //in the case of february if it's a leap year lastDayOf month will be changed
            case 2:
                if (leapYear==true){
                    lastDayOfMonth=29;

                }
                else{
                    lastDayOfMonth=28;
                }
                break;

            case 4: case 6:case 9:case 11:
                lastDayOfMonth=30;
                break;

            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                lastDayOfMonth=31;
                break;

            default:returningDate="invalid inputs";
            }


            dayLimit=lastDayOfMonth-possibleDayCount;

               if(day<=dayLimit){
               d=String.valueOf(day+possibleDayCount);
               m=String.valueOf(month);
               y=String.valueOf(year);

            }

            //if any day exceeding dayLimit, will go to next month with a new day
            else{
            if(month==12) {
                //if it is december month will be 1 and year will be next year
                int newDay = possibleDayCount - (lastDayOfMonth - day);
                d = String.valueOf(newDay);
                m = "1";
                y=String.valueOf(year+1);
            }
            else{
                int newDay = possibleDayCount - (lastDayOfMonth - day);
                d = String.valueOf(newDay);
                m = String.valueOf(month + 1);
                y=String.valueOf(year);
            }

            }

            returningDate=(d+"/"+m+"/"+y);

            return returningDate;
    }

    //method to calculate overdue
    public int keptPeriod(int returnedDay,int returendMonth, int returnedYear){
        //following equation can convert georgian date to a julian day number
        int julianDue=((1461*(year + 4800 + (month-14)/12))/4 +(367 * (month - 2 - 12 * ((month- 14)/12)))/12 - (3 * ((year + 4900 + (month - 14)/12)/100))/4 + day - 32075);
        int julianReturned=((1461*(returnedYear + 4800 + (returendMonth-14)/12))/4 +(367 * (returendMonth - 2 - 12 * ((returendMonth- 14)/12)))/12 - (3 * ((returnedYear + 4900 + (returendMonth - 14)/12)/100))/4 + returnedDay - 32075);
        //getting difference between the due date and the returning date by subtracting julian day numbers.
        //this method will also consider leap years, if there is any.
        int duration=julianReturned-julianDue;

        return duration;
    }

    //calculating fine
    //parameters=overdue day count, due hour, returning hour, due minute, returning minute
    public double fineAsToHours(int numOfDays,int h1,int h2,int m1, int m2){
        double fine=0.0;
        int y=0;

        //if overdue is minus there is no need to calculate a fine
        if(numOfDays<0){
         fine=0.0;
        }

        //if it is 0 if the reader returns book on or before the due hour, again no fine
        //if reader returns book after due hour minutes will be taken in to count
        else if(numOfDays==0){
            if(h2<=h1){
                fine=0.0;
            }
            else{
                //ex:if due=10.30, returnTime=12.20, fine will be charged only for 10.30 to 11.30 period
                //which is returnTime-1-due
                if(m2<m1){
                   y=(h2-h1)-1;
                   fine=y*0.2;
                }
                //if returnTime=12.45 fine will be charged for 10.30 to 12.45 period
                //which is returnTime-due
                else if(m2>=m1){
                    y=h2-h1;
                    fine=y*0.2;
                }
            }
        }

        //if over due is 3days or less
        else if(numOfDays<=3){
            //ex: due=7th 10.30, returned=9th 9.30
            //finr will be charged for 24h and the extra hours from 8th, and extra hours from 9th
            if(h2<=h1){
                y=((numOfDays-1)*24)+((24-h1)+h2);
                fine=y*0.2;
            }
            else{
                //ex: due=7th 10.30, returned= 9th 12.15
                //total 2*24h will be added. plus, time period frm 9th 10.30 to 11.30
                if(m2<m1){
                    y=(numOfDays*24)+((h2-h1)-1);
                    fine=y*0.2;
                }
                else if(m2>=m1){
                    //returned=9th 12.45
                    //total 2*24h, plus 2hours difference from 10.30 to 12.30
                    y=(numOfDays*24)+(h2-h1);
                    fine=y*0.2;
                }
            }

        }


        //same logic and will multiply the 1st 3 days form 0.2 as to the requirement

        else if(numOfDays>3){
            if(h2<=h1){
                y=((numOfDays-4)*24)+((24-h1)+h2);
                fine=(y*0.5)+(24*3*0.2);
            }

            else{
                if(m2<m1){
                    y=((numOfDays-3)*24)+((h2-h1)-1);
                    fine=(y*0.5)+(3*24*0.2);
                }

                else if(m2>m1){
                    y=((numOfDays-3)*24)+(h2-h1);
                    fine=(y*0.5)+(3*24*0.2);
                }
            }


        }

        return fine;

    }


}
