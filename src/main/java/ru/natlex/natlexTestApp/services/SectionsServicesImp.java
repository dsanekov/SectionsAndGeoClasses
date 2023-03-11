package ru.natlex.natlexTestApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Set<Section> findSectionsByCode(String code){
        return sectionsRepository.findSectionsByGeologicalClassCode(code);
    }
}
