package com.luxoft.wheretogo.models;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ArchiveServiceRequest {

    public String searchFrom;
    public String searchTo;

    public void setSearchFrom(String searchFrom) {
        this.searchFrom = searchFrom;
    }

    public void setSearchTo(String searchTo) {
        this.searchTo = searchTo;
    }

    public Date getSearchFrom() {
        try {
            return new SimpleDateFormat("dd-MM-yy").parse(searchFrom);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Date getSearchTo() {
        try {
            return new SimpleDateFormat("dd-MM-yy").parse(searchTo);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
