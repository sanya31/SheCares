package com.sanyagupta.shecares.model;

public class DatesModel {
    private String start;
    private String end;
    private int duration;
    private int cycle;

    public DatesModel(){
    }
    public DatesModel(String start){
        this.start = start;
        duration = calcDuration();
        end = null;
        cycle = 0;
    }
    public DatesModel(String start, String end){
        this.start = start;
        this.end = end;
        duration = calcDuration();
        cycle = -1;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
    public void setDuration(int duration){
        this.duration = duration;
    }

    public int calcDuration(){

        String sub= new String(),sub1= new String();
        int i=0,j=0;
        while(start.charAt(i)!='/')
        {
            sub+=start.charAt(i);
            i++;
        }
        i++;
        while(end.charAt(j)!='/')
        {
            sub1+=end.charAt(j);
            j++;
        }
        j++;
        int date= Integer.valueOf(sub);
        sub="";
        int date1=Integer.valueOf(sub1);
        sub1="";
        while(start.charAt(i)!='/')
        {
            sub+=start.charAt(i);
            i++;
        }
        while(end.charAt(j)!='/')
        {
            sub1+=end.charAt(j);
            j++;
        }
        int month1=Integer.valueOf(sub1);
        int month = Integer.valueOf(sub);

        int datediff=date1-date;
        int monthdiff=month1-month;
        if(monthdiff!=0)
        {
            if(month==4||month==6||month==8||month==10)
            {
                duration = 30-date+date1+1;
            }
            else if(month==2)
            {
                duration = 28- date +date1 + 1;
            }
            else
                duration = 31- date + date1+1;
        }

        else{
            duration = date1-date;
        }

        return duration;
    }
    public int getDuration(){
        return calcDuration();
    }

    public int getCycle() {
        return cycle;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }



}
