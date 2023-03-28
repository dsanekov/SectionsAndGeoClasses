package ru.natlex.natlexTestApp.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.natlex.natlexTestApp.dto.GeologicalClassDTO;
import ru.natlex.natlexTestApp.dto.SectionDTO;
import ru.natlex.natlexTestApp.dto.UnsuccessfulResponse;
import ru.natlex.natlexTestApp.models.GeologicalClass;
import ru.natlex.natlexTestApp.models.Section;
import ru.natlex.natlexTestApp.services.GeologicalClassService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/geo-class")
public class GeologicalClassController {
    private final GeologicalClassService geologicalClassService;
    @Autowired
    public GeologicalClassController(GeologicalClassService geologicalClassService) {
        this.geologicalClassService = geologicalClassService;
    }

    @GetMapping()
    public ResponseEntity<Object> showGeologicalClasses(){
        List<GeologicalClass> geologicalClassList = geologicalClassService.showAll();
        List<GeologicalClassDTO> geologicalClassDTOList = new ArrayList<>();
        for(GeologicalClass geologicalClass : geologicalClassList){
            GeologicalClassDTO geologicalClassDTO = convertToGeologicalClassDTO(geologicalClass);
            geologicalClassDTOList.add(geologicalClassDTO);
        }
        return new ResponseEntity<>(geologicalClassDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> showGeoClass(@PathVariable("id") int id) {
        GeologicalClass geologicalClass = geologicalClassService.findGeologicalClassById(id);
        if(geologicalClass == null){
            return new ResponseEntity<>(new UnsuccessfulResponse("This geo class does not exist!",HttpStatus.NOT_FOUND.value()),HttpStatus.NOT_FOUND);
        }
        GeologicalClassDTO geologicalClassDTO = convertToGeologicalClassDTO(geologicalClass);
        return new ResponseEntity<>(geologicalClassDTO,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        geologicalClassService.delete(id);
        return new ResponseEntity<>("Geo class has been deleted",HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<Object> create(HttpServletRequest request) {
        String code = request.getParameter("code");
        String name = request.getParameter("name");
        int sectionId = Integer.parseInt(request.getParameter("sectionId"));
        int id = geologicalClassService.create(name,code,sectionId);
        return new ResponseEntity<>(id,HttpStatus.OK);
    }

    @GetMapping("/{id}/edit")
    public ResponseEntity<Object> edit(@PathVariable("id") int id, HttpServletRequest request) {
        String code = request.getParameter("code");
        String name = request.getParameter("name");
        GeologicalClass geologicalClass = geologicalClassService.edit(id,name,code);
        GeologicalClassDTO geologicalClassDTO = convertToGeologicalClassDTO(geologicalClass);
        return new ResponseEntity<>(geologicalClassDTO,HttpStatus.OK);
    }

    private GeologicalClassDTO convertToGeologicalClassDTO(GeologicalClass geologicalClass){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(geologicalClass,GeologicalClassDTO.class);
    }
}
