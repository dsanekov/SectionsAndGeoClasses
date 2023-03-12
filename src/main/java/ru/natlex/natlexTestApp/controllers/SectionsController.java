package ru.natlex.natlexTestApp.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.natlex.natlexTestApp.dto.GeologicalClassDTO;
import ru.natlex.natlexTestApp.dto.SectionDTO;
import ru.natlex.natlexTestApp.dto.UnsuccessfulResponse;
import ru.natlex.natlexTestApp.models.GeologicalClass;
import ru.natlex.natlexTestApp.models.Section;
import ru.natlex.natlexTestApp.services.SectionsServices;
import ru.natlex.natlexTestApp.services.SectionsServicesImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/sections")
public class SectionsController {

    private final SectionsServices sectionServiceService;

    @Autowired
    public SectionsController(SectionsServicesImp sectionServiceService) {
        this.sectionServiceService = sectionServiceService;
    }
    @GetMapping("/by-code")
    public ResponseEntity<Object> findSectionByCode(HttpServletRequest request){
        String code = request.getParameter("code");
        if(code.isEmpty()){
            return new ResponseEntity<>(new UnsuccessfulResponse("This code is empty!",HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
        }
        Set<Section> sections = sectionServiceService.findSectionsByCode(code);
        List<SectionDTO> sectionDTOS = new ArrayList<>();
        for(Section section :sections){
            sectionDTOS.add(convertToSectionDTO(section));
        }
        return new ResponseEntity<>(sectionDTOS, HttpStatus.OK);
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
