package com.example.chatbot.Models;

public class PostOfficeData {
    private  String Name;
    private String  District;
    private  String Division;
    private  String Region;
    private  String Country;

    public PostOfficeData(String name, String district, String division, String region, String country) {
        Name = name;
        District = district;
        Division = division;
        Region = region;
        Country = country;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getDivision() {
        return Division;
    }

    public void setDivision(String division) {
        Division = division;
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }
}
