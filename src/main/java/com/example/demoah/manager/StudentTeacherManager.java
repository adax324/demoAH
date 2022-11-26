package com.example.demoah.manager;

import com.example.demoah.dao.StudentDAO;
import com.example.demoah.dao.TeacherDAO;
import com.example.demoah.dto.StudentDTO;
import com.example.demoah.entity.Student;
import com.example.demoah.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StudentTeacherManager {
    @Autowired
    private StudentDAO studentDAO;
    @Autowired
    private TeacherDAO teacherDAO;

    public void assign(Long studentId, Long teacherId) {
        Optional<Student> student = studentDAO.get(studentId);
        Optional<Teacher> teacher = teacherDAO.get(teacherId);

        if (teacher.isEmpty())
            throw new RuntimeException("No teacher with provided id");
        else if (student.isEmpty())
            throw new RuntimeException("No student with provided id");

        teacher.get().addStudent(student.get());
        teacherDAO.update(teacher.get());

        student.get().addTeacher(teacher.get());
        studentDAO.update(student.get());
    }

    public void unassignTeacher(Long teacherId) {
        Teacher teacher = teacherDAO.get(teacherId).get();
        List<Student> student = studentDAO.listByTeacher(teacherId);
        student.forEach(item -> {
            if (item.getTeachers().contains(teacher)) {
                item.removeTeacher(teacher);
                teacher.removeStudent(item);
                studentDAO.update(item);
            }
        });
        teacherDAO.update(teacher);
    }
}
