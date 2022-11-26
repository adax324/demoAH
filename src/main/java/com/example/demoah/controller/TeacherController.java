package com.example.demoah.controller;

import com.example.demoah.dto.TeacherDTO;
import com.example.demoah.manager.TeacherManager;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private TeacherManager teacherManager;

    @PostMapping("/save")
    public ResponseEntity<TeacherDTO> save(@RequestBody TeacherDTO teacherDTO) {
        return ResponseEntity.ok(teacherManager.save(teacherDTO));
    }

    @GetMapping("/getById")
    public ResponseEntity<TeacherDTO> getById(@RequestParam Long id) {
        return ResponseEntity.ok(teacherManager.get(id));
    }
    @GetMapping("/getByUuid")
    public ResponseEntity<TeacherDTO> getByUuid(@RequestParam String uuid) {
        return ResponseEntity.ok(teacherManager.get(uuid));
    }

    @GetMapping("/list")
    public ResponseEntity<List<TeacherDTO>> list(@RequestParam Integer pageNumber, @RequestParam Integer pageSize, @RequestParam String sortBy, @RequestParam Boolean asc) {
        return ResponseEntity.ok(teacherManager.list(pageNumber, pageSize, sortBy, asc));
    }

    @PostMapping("/assignToStudent")
    public ResponseEntity<TeacherDTO> assignToStudent(@RequestParam Long teacherId, @RequestParam Long studentId) {
        return ResponseEntity.ok(teacherManager.assignToStudent(teacherId, studentId));
    }

    @GetMapping("/getByStudent")
    public ResponseEntity<List<TeacherDTO>> getByStudent(@RequestParam Long studentId) {
        return ResponseEntity.ok(teacherManager.getByStudent(studentId));
    }

    @GetMapping("/listByParams")
    public ResponseEntity<List<TeacherDTO>> listByParams(HttpServletRequest request, @RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName) {
        List<Criterion> criterionList = new ArrayList<>();
        if (request.getParameter("firstName") != null)
            criterionList.add(Restrictions.eq("firstName", firstName));
        if (request.getParameter("lastname") != null)
            criterionList.add(Restrictions.eq("lastName", lastName));
        return ResponseEntity.ok(teacherManager.listByCriteria(criterionList.toArray(new Criterion[0])));
    }

    @PutMapping("/update")
    public ResponseEntity<TeacherDTO> update(@RequestBody TeacherDTO teacherDTO) {
        return ResponseEntity.ok(teacherManager.update(teacherDTO));
    }
    @DeleteMapping("/deleteById")
    public ResponseEntity deleteById(@RequestParam Long id) {
        teacherManager.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
    @DeleteMapping("/deleteByUuid")
    public ResponseEntity deleteByUuid(@RequestParam String uuid) {
        teacherManager.delete(uuid);
        return new ResponseEntity(HttpStatus.OK);
    }
}
