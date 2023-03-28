package ru.natlex.natlexTestApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.natlex.natlexTestApp.models.GeologicalClass;
import ru.natlex.natlexTestApp.models.Section;
import ru.natlex.natlexTestApp.repositories.GeologicalClassRepository;
import ru.natlex.natlexTestApp.repositories.SectionsRepository;

import java.util.List;

@Service
public class GeologicalClassServiceImp implements GeologicalClassService {
    private final GeologicalClassRepository geologicalClassRepository;
    private final SectionsRepository sectionsRepository;

    @Autowired
    public GeologicalClassServiceImp(GeologicalClassRepository geologicalClassRepository, SectionsRepository sectionsRepository) {
        this.geologicalClassRepository = geologicalClassRepository;
        this.sectionsRepository = sectionsRepository;
    }

    @Override
    public GeologicalClass findGeologicalClassById(int id) {
        return geologicalClassRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(int id) {
        GeologicalClass geologicalClass = findGeologicalClassById(id);
        Section section = geologicalClass.getSection();
        section.getGeologicalClasses().remove(geologicalClass);
        geologicalClassRepository.delete(geologicalClass);
        sectionsRepository.save(section);
    }

    @Override
    public int create(String name, String code, int sectionId) {
        Section section = sectionsRepository.findById(sectionId).orElse(null);
        GeologicalClass geologicalClass = new GeologicalClass(name,code,section);
        return geologicalClassRepository.save(geologicalClass).getId();
    }

    @Override
    public GeologicalClass edit(int id, String name, String code) {
        GeologicalClass geologicalClass = findGeologicalClassById(id);
        geologicalClass.setName(name);
        geologicalClass.setCode(code);
        geologicalClassRepository.save(geologicalClass);
        return geologicalClass;
    }



    @Override
    public List<GeologicalClass> showAll() {
        return geologicalClassRepository.findAll();
    }
}
