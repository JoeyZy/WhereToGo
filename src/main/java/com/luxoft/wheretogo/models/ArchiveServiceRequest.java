package com.luxoft.wheretogo.models;

import com.luxoft.wheretogo.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ArchiveServiceRequest {

    private String searchFrom;
    private String searchTo;

    private SimpleDateFormat format =new SimpleDateFormat("dd-MM-yy");

    public void setSearchFrom(String searchFrom) {
        this.searchFrom = searchFrom;
    }

    public void setSearchTo(String searchTo) {
        this.searchTo = searchTo;
    }

    public Date getSearchFrom() {
        try {
            return format.parse(searchFrom);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Date getSearchTo() {
        try {
            if (DateUtils.isSameDay(format.parse(searchTo), new Date())) return new Date();
            return new Date (format.parse(searchTo).getTime() + TimeUnit.DAYS.toMillis(1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
