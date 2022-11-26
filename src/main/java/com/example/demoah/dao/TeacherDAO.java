package com.example.demoah.dao;

import com.example.demoah.entity.Student;
import com.example.demoah.entity.Teacher;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class TeacherDAO extends AbstractDAO<Teacher, Long> {
    public List<Teacher> listByStudent(Long studentId) {
        return listByCriteria(Map.of("students", "students"), null, null, Restrictions.eq("students.id", studentId));
    }
}
