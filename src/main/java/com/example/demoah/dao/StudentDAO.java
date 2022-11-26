package com.example.demoah.dao;

import com.example.demoah.dto.StudentDTO;
import com.example.demoah.entity.Student;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class StudentDAO extends AbstractDAO<Student, Long> {
    public List<Student> listByTeacher(Long teacherId) {
        return listByCriteria(Map.of("teachers", "teachers"), null, null, Restrictions.eq("teachers.id", teacherId));
    }
}
