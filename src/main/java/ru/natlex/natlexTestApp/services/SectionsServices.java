package ru.natlex.natlexTestApp.services;

import ru.natlex.natlexTestApp.models.Section;

import java.util.Set;

public interface SectionsServices {
    Set<Section> findSectionsByCode(String code);

    Section findSectionById(int id);

    void delete(int id);

    int create(String name);

    Section edit(int id, String name);
}
