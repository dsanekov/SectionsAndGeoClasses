package ru.natlex.natlexTestApp.dto;


import java.util.List;

public class SectionDTO {
    private String name;
    private List<GeologicalClassDTO> geologicalClasses;

    public SectionDTO(){}

    public SectionDTO(String name, List<GeologicalClassDTO> geologicalClasses) {
        this.name = name;
        this.geologicalClasses = geologicalClasses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GeologicalClassDTO> getGeologicalClasses() {
        return geologicalClasses;
    }

    public void setGeologicalClasses(List<GeologicalClassDTO> geologicalClasses) {
        this.geologicalClasses = geologicalClasses;
    }
}
