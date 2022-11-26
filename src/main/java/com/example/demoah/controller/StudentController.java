package com.example.demoah.controller;

import com.example.demoah.dto.StudentDTO;
import com.example.demoah.manager.StudentManager;
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
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentManager studentManager;

    @PostMapping("/save")
    public ResponseEntity<StudentDTO> save(@RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok(studentManager.save(studentDTO));
    }

    @GetMapping("/getById")
    public ResponseEntity<StudentDTO> getById(@RequestParam Long id) {
        return ResponseEntity.ok(studentManager.get(id));
    }

    @GetMapping("/getByUuid")
    public ResponseEntity<StudentDTO> getByUuid(@RequestParam String uuid) {
        return ResponseEntity.ok(studentManager.get(uuid));
    }

    @GetMapping("/list")
    public ResponseEntity<List<StudentDTO>> list(@RequestParam Integer pageNumber, @RequestParam Integer pageSize, @RequestParam String sortBy, @RequestParam Boolean asc) {
        return ResponseEntity.ok(studentManager.list(pageNumber, pageSize, sortBy, asc));
    }

    @PostMapping("/assignToTeacher")
    public ResponseEntity<StudentDTO> assignToTeacher(@RequestParam Long studentId, @RequestParam Long teacherId) {
        return ResponseEntity.ok(studentManager.assignToTeacher(studentId, teacherId));
    }

    @GetMapping("/getByTeacher")
    public ResponseEntity<List<StudentDTO>> getByTeacher(@RequestParam Long teacherId) {
        return ResponseEntity.ok(studentManager.listByTeacher(teacherId));
    }

    @GetMapping("/listByParams")
    public ResponseEntity<List<StudentDTO>> listByParams(HttpServletRequest request, @RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName) {
        List<Criterion> criterionList = new ArrayList<>();
        if (request.getParameter("firstName") != null)
            criterionList.add(Restrictions.eq("firstName", firstName));
        if (request.getParameter("lastName") != null)
            criterionList.add(Restrictions.eq("lastName", lastName));
        return ResponseEntity.ok(studentManager.listByCriteria(criterionList.toArray(new Criterion[0])));
    }

    @PutMapping("/update")
    public ResponseEntity<StudentDTO> update(@RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok(studentManager.update(studentDTO));
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity deleteById(@RequestParam Long id) {
        studentManager.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/deleteByUuid")
    public ResponseEntity deleteByUuid(@RequestParam String uuid) {
        studentManager.delete(uuid);
        return new ResponseEntity(HttpStatus.OK);
    }
}
