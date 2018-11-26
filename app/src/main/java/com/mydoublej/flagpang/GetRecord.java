package com.mydoublej.flagpang;

public class GetRecord {
    private int id;
    private String country;
    private String continent;
    private String image;
    private String level;

    public GetRecord(int id, String country, String continent, String image, String level) {
        this.id = id;
        this.country = country;
        this.continent = continent;
        this.image = image;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public String getContinent() {
        return continent;
    }

    public String getCountry() {
        return country;
    }

    public String getImage() {
        return image;
    }

    public String getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return "GetRecord{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", continent='" + continent + '\'' +
                ", image='" + image + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
