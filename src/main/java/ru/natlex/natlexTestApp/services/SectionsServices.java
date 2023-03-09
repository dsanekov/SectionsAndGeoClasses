package ru.natlex.natlexTestApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.natlex.natlexTestApp.models.Section;
import ru.natlex.natlexTestApp.repositories.SectionsRepository;

import java.util.List;
import java.util.Set;

@Service
public class SectionsServices {

    private final SectionsRepository sectionsRepository;

    @Autowired
    public SectionsServices(SectionsRepository sectionsRepository) {
        this.sectionsRepository = sectionsRepository;
    }
    public Set<Section> findSectionsByCode(String code){
        return sectionsRepository.findSectionsByGeologicalClassCode(code);
    }
}
