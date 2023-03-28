package ru.natlex.natlexTestApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.natlex.natlexTestApp.models.Section;
import ru.natlex.natlexTestApp.repositories.SectionsRepository;

import java.util.List;
import java.util.Optional;
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

    @Override
    public Section findSectionById(int id) {
        Optional<Section> optionalSection = sectionsRepository.findById(id);
        return optionalSection.orElse(null);
    }

    @Override
    public void delete(int id) {
        Section sectionToDelete = findSectionById(id);
        sectionsRepository.delete(sectionToDelete);
    }

    @Override
    public int create(String name) {
        Section section = new Section();
        section.setName(name);
        return sectionsRepository.save(section).getId();
    }

    @Override
    public Section edit(int id, String name) {
        Section section = findSectionById(id);
        section.setName(name);
        sectionsRepository.save(section);
        return section;
    }

    @Override
    public List<Section> showAll() {
        return sectionsRepository.findAll();
    }

}
