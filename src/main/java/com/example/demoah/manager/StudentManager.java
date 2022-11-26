package com.example.demoah.manager;

import com.example.demoah.dao.StudentDAO;
import com.example.demoah.dto.StudentDTO;
import com.example.demoah.entity.Student;
import com.example.demoah.utility.CustomMapper;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudentManager extends AbstractManager<Student, StudentDTO, Long> {
    @Autowired
    private StudentTeacherManager studentTeacherManager;
    @Autowired
    private StudentDAO studentDAO;

    public StudentDTO assignToTeacher(Long studentId, Long teacherId) {
        studentTeacherManager.assign(studentId, teacherId);
        return get(studentId);
    }

    public List<StudentDTO> listByTeacher(Long teacherId) {
        return (List<StudentDTO>) CustomMapper.mapList(studentDAO.listByTeacher(teacherId).toArray(), StudentDTO.class);
    }

    @Override
    public void delete(Long id) {
        studentTeacherManager.unassignStudent(id);
        super.delete(id);
    }

    @Override
    public void delete(String uuid) {
        studentTeacherManager.unassignStudent(get(uuid).getId());
        super.delete(uuid);
    }

}
