package com.example.chatbot.Models;

import java.util.ArrayList;

public class pinCodeModal {
private String Message;
private String Status;
private ArrayList<PostOfficeData> PostOffice ;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public ArrayList<PostOfficeData> getPostOffice() {
        return PostOffice;
    }

    public void setPostOffice(ArrayList<PostOfficeData> postOffice) {
        PostOffice = postOffice;
    }

    public pinCodeModal(String message, String status, ArrayList<PostOfficeData> postOffice) {
        Message = message;
        Status = status;
        PostOffice = postOffice;
    }
}
