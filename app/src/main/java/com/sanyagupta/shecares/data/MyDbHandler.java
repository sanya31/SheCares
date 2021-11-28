package com.sanyagupta.shecares.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.sanyagupta.shecares.model.DatesModel;
import com.sanyagupta.shecares.parameters.Parameters;

import java.util.ArrayList;
import java.util.List;

public class MyDbHandler extends SQLiteOpenHelper{

    int avg=0;

    public MyDbHandler(Context context){
        super(context, Parameters.DB_Name, null, Parameters.DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create = "CREATE TABLE "+Parameters.TABLE_NAME1+"("
                +Parameters.KEY_STARTDATE+" TEXT PRIMARY KEY, "
                +Parameters.KEY_ENDDATE+" TEXT, "+Parameters.KEY_DURATION+" INTEGER, "+Parameters.KEY_CYCLELENGTH+" INTEGER)";
        sqLiteDatabase.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public int getCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+Parameters.TABLE_NAME1;
        Cursor cursor = db.rawQuery(query, null);
        int count= cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }


    public Boolean addDates(DatesModel datesModel) {

        int cyclelength = calcCycleLength(datesModel.getStart());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Parameters.KEY_STARTDATE, datesModel.getStart());
        values.put(Parameters.KEY_ENDDATE, datesModel.getEnd());
        values.put(Parameters.KEY_DURATION, datesModel.getDuration());
        values.put(Parameters.KEY_CYCLELENGTH,cyclelength);
        long result= db.insert(Parameters.TABLE_NAME1, null, values);

        db.close();
        if(result==-1)
            return false;
        return true;
    }


    public int averagePeriodLength(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Integer> durationlist = new ArrayList<>();
        String select = "SELECT * FROM "+Parameters.TABLE_NAME1;
        Cursor cursor = db.rawQuery(select, null);
        int average = 0;
        if(cursor.moveToFirst()){
            do{
                durationlist.add(cursor.getInt(2));
            }while(cursor.moveToNext());
            for(int dur:durationlist){
                average +=dur;
            }
            average = average/ durationlist.size();
        }
        cursor.close();
        db.close();
        return average;
    }

    public int calcCycleLength(String  end ){
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM "+Parameters.TABLE_NAME1;
        Cursor cursor = db.rawQuery(select,null);
        int length = 0;
        if(cursor.moveToLast())
        {
            String start = cursor.getString(0);
            int duration = 0;
            String sub = new String(), sub1 = new String();
            int i = 0, j = 0;
            while (start.charAt(i) != '/') {
                sub += start.charAt(i);
                i++;
            }
            i++;
            while (end.charAt(j) != '/') {
                sub1 += end.charAt(j);
                j++;
            }
            j++;
            int date = Integer.valueOf(sub);
            sub = "";
            int date1 = Integer.valueOf(sub1);
            sub1 = "";
            while (start.charAt(i) != '/') {
                sub += start.charAt(i);
                i++;
            }
            while (end.charAt(j) != '/') {
                sub1 += end.charAt(j);
                j++;
            }
            int month1 = Integer.valueOf(sub1);
            int month = Integer.valueOf(sub);

            int datediff = date1 - date;
            int monthdiff = month1 - month;
            if (monthdiff != 0) {
                if (month == 4 || month == 6 || month == 8 || month == 10) {
                    duration = 30 - date + date1 + 1;
                } else if (month == 2) {
                    duration = 28 - date + date1 + 1;
                } else
                    duration = 31 - date + date1 + 1;
            } else {
                duration = date1 - date;
            }
            length = duration;
//        length = length + Integer.parseInt(cyclelist[0].substring(0,2));
//        int month = Integer.parseInt(cyclelist[1].substring(2,4));
//        if(month==4 || month==6 || month==9 || month==11)
//            length = length+(30 - Integer.parseInt(cyclelist[1].substring(0,2)));
//        else if(month==2)
//            length = length+(28 - Integer.parseInt(cyclelist[1].substring(0,2)));
//        else
//            length = length+(31 - Integer.parseInt(cyclelist[1].substring(0,2)));
        }
        else
            length =26;
        cursor.close();
        db.close();
        return length;
    }

    public String nextDate(){
        SQLiteDatabase db = this.getReadableDatabase();
        int count=0;
        String nextDate = new String();
        avg=0;
        String select = "SELECT * FROM "+Parameters.TABLE_NAME1;
        Cursor cursor = db.rawQuery(select, null);

        if(cursor.moveToLast())
        {

            do {
                avg = avg + cursor.getInt(3);
                count++;
            }while(cursor.moveToPrevious()&&count<3);

            if(count==1) avg = 26;
            else if (count==2)
            {
                avg /=2;
            }
            else avg /= 3;
            String d= new String();
            cursor.moveToLast();
            d = cursor.getString(0);

            String sub=new String();
            int i=0;
            while(d.charAt(i)!='/')
            {
                sub+=d.substring(i,i+1);
                i++;
            }
            i++;
            int date= avg+ Integer.parseInt(sub);
            sub=new String();
            while(d.charAt(i)!='/')
            {
                sub+=d.substring(i,i+1);
                i++;
            }
            int month = Integer.parseInt(sub);
            i++;
            sub=new String();
            while(i!=d.length())
            {
                sub+=d.substring(i,i+1);
                i++;
            }
            int year=Integer.parseInt(sub);
            if (month == 4 || month == 6 || month == 9 || month == 11) {
                if (date > 30) {
                    date = date - 30;
                    month++;
                }

            } else if (month == 2) {
                if (date > 28) {
                    date = date - 28;
                    month++;
                }
            } else {
                if (date > 31) {
                    date = date - 31;
                    month++;
                }
            }
            if (date < 10) {
                nextDate = "0" + String.valueOf(date) + " ";
            } else
                nextDate = String.valueOf(date) + " ";
            if (month == 13) {
                year++;
                month = 1;
            }
            if (month < 10) {
                nextDate += "0" + String.valueOf(month) + " " + String.valueOf(year);
            } else {
                nextDate += String.valueOf(month) + " " + String.valueOf(year);
            }

            String m = new String();

            switch (month) {
                case 1:
                    m = "Jan";
                    break;
                case 2:
                    m = "Feb";
                    break;
                case 3:
                    m = "Mar";
                    break;
                case 4:
                    m = "Apr";
                    break;
                case 5:
                    m = "May";
                    break;
                case 6:
                    m = "Jun";
                    break;
                case 7:
                    m = "Jul";
                    break;
                case 8:
                    m = "Aug";
                    break;
                case 9:
                    m = "Sep";
                    break;
                case 10:
                    m = "Oct";
                    break;
                case 11:
                    m = "Nov";
                    break;
                case 12:
                    m = "Dec";
                    break;
                default:
                    break;
            }
            nextDate = nextDate.substring(0, 3) + m + nextDate.substring(5);
        }
        else
            nextDate =null;
        cursor.close();
        db.close();
        return nextDate;

    }

    public ArrayList<DatesModel> getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<DatesModel> displaylist= new ArrayList<>();
        String select = "SELECT * FROM "+Parameters.TABLE_NAME1;
        Cursor cursor = db.rawQuery(select, null);

        if(cursor.moveToLast()){
            do{
                DatesModel datesModel = new DatesModel();
                datesModel.setStart(cursor.getString(0));
                datesModel.setEnd(cursor.getString(1));
                datesModel.setDuration(Integer.parseInt(cursor.getString(2)));
                datesModel.setCycle(cursor.getInt(3));
                displaylist.add(datesModel);
            }while(cursor.moveToPrevious());
        }
        cursor.close();
        db.close();
        return displaylist;
    }

    public void deleteDate(String s){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Parameters.TABLE_NAME1, Parameters.KEY_STARTDATE+"=?", new String[]{s});
        db.close();
    }

    public Boolean UpdateDate(String s ,String start,String end,DatesModel datesModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Parameters.KEY_STARTDATE, start);
        values.put(Parameters.KEY_ENDDATE, end);
        values.put(Parameters.KEY_DURATION, datesModel.getDuration());
        long result = db.update(Parameters.TABLE_NAME1,values, Parameters.KEY_STARTDATE+"=?", new String[]{s});
        db.close();
        if(result==-1)
            return false;

        return true;

    }

    public void deleteDatabase(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Parameters.TABLE_NAME1,null,null);
        db.close();
    }

    public int average()
    {
        String s =nextDate();
        return avg;
    }
}
