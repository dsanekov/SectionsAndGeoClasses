package ru.natlex.natlexTestApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.natlex.natlexTestApp.models.GeologicalClass;
import ru.natlex.natlexTestApp.models.Section;

import java.util.Set;

@Repository
public interface GeologicalClassRepository extends JpaRepository<GeologicalClass,Integer> {
    @Query(value = "SELECT COUNT(*) AS Count FROM geologicalclass GROUP BY geologicalclass.section_id ORDER BY Count DESC LIMIT 1", nativeQuery = true)
    int findMaxGeo();
}
