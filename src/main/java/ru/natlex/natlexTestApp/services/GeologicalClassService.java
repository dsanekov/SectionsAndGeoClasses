package ru.natlex.natlexTestApp.services;

import ru.natlex.natlexTestApp.models.GeologicalClass;

import java.util.List;


public interface GeologicalClassService {
    GeologicalClass findGeologicalClassById(int id);

    void delete(int id);

    int create(String name, String code, int sectionId);

    GeologicalClass edit(int id, String name, String code);

    List<GeologicalClass> showAll();
}
