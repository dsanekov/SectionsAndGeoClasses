package ru.natlex.natlexTestApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.natlex.natlexTestApp.models.Section;
import ru.natlex.natlexTestApp.repositories.SectionsRepository;

import java.util.Set;

@Service
public class SectionsServicesImp implements SectionsServices{

    private final SectionsRepository sectionsRepository;

    @Autowired
    public SectionsServicesImp(SectionsRepository sectionsRepository) {
        this.sectionsRepository = sectionsRepository;
    }
    @Override
    public Set<Section> findSectionsByCode(String code){
        return sectionsRepository.findSectionsByGeologicalClassCode(code);
    }
}
