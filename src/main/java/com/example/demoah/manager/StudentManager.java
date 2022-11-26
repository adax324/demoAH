package com.example.demoah.manager;

import com.example.demoah.dto.StudentDTO;
import com.example.demoah.entity.Student;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudentManager extends AbstractManager<Student, StudentDTO, Long> {
    @Autowired
    private StudentTeacherManager studentTeacherManager;

    public StudentDTO assignToTeacher(Long studentId, Long teacherId) {
        studentTeacherManager.assign(studentId, teacherId);
        return get(studentId);
    }

    public List<StudentDTO> getByTeacher(Long teacherId) {
        return listByCriteria(Map.of("teachers", "teachers"), null, null, Restrictions.eq("teachers.id", teacherId));
    }

}
