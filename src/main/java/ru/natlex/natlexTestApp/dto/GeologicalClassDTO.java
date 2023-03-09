package ru.natlex.natlexTestApp.dto;


public class GeologicalClassDTO {

    private String name;

    private String code;


    public GeologicalClassDTO(){};

    public GeologicalClassDTO(String name, String code) {
        this.name = name;
        this.code = code;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
