package ru.natlex.natlexTestApp.services;

import ru.natlex.natlexTestApp.models.Section;

import java.util.Set;

public interface SectionsServices {
    public Set<Section> findSectionsByCode(String code);
}
