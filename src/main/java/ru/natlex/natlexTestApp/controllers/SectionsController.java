package ru.natlex.natlexTestApp.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.natlex.natlexTestApp.dto.GeologicalClassDTO;
import ru.natlex.natlexTestApp.dto.SectionDTO;
import ru.natlex.natlexTestApp.models.GeologicalClass;
import ru.natlex.natlexTestApp.models.Section;
import ru.natlex.natlexTestApp.services.SectionsServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/sections")
public class SectionsController {

    private final SectionsServices sectionServiceService;

    @Autowired
    public SectionsController(SectionsServices sectionServiceService) {
        this.sectionServiceService = sectionServiceService;
    }
    @GetMapping("/by-code")
    public List<SectionDTO> findSectionByCode(HttpServletRequest request){
        String code = request.getParameter("code");
        Set<Section> sections = sectionServiceService.findSectionsByCode(code);
        List<SectionDTO> sectionDTOS = new ArrayList<>();
        for(Section section :sections){
            sectionDTOS.add(convertToSectionDTO(section));
        }
        return sectionDTOS;
    }

    private Section convertToSection(SectionDTO sectionDTO){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(sectionDTO,Section.class);
    }

    private SectionDTO convertToSectionDTO(Section section){
        ModelMapper modelMapper = new ModelMapper();
        SectionDTO sectionDTO = modelMapper.map(section,SectionDTO.class);
        List<GeologicalClassDTO> geologicalClassDTOList = new ArrayList<>();
        for(GeologicalClass g : section.getGeologicalClasses()){
            geologicalClassDTOList.add(convertToGeologicalClassDTO(g));
        }
        sectionDTO.setGeologicalClasses(geologicalClassDTOList);
        return sectionDTO;
    }
    private GeologicalClassDTO convertToGeologicalClassDTO(GeologicalClass geologicalClass){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(geologicalClass,GeologicalClassDTO.class);
    }
}
