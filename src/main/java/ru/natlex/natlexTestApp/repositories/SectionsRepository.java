package ru.natlex.natlexTestApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.natlex.natlexTestApp.models.Section;

import java.util.List;
import java.util.Set;

public interface SectionsRepository extends JpaRepository<Section,Integer> {
    @Query(value = "SELECT s.* FROM Section s JOIN geologicalClass g ON s.id=g.section_id WHERE g.code = :code", nativeQuery = true)
    Set<Section> findSectionsByGeologicalClassCode(String code);
}
