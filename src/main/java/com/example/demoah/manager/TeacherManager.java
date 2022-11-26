package com.example.demoah.manager;

import com.example.demoah.dao.TeacherDAO;
import com.example.demoah.dto.StudentDTO;
import com.example.demoah.dto.TeacherDTO;
import com.example.demoah.entity.Teacher;
import com.example.demoah.utility.CustomMapper;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TeacherManager extends AbstractManager<Teacher, TeacherDTO, Long> {
    @Autowired
    private StudentTeacherManager studentTeacherManager;
    @Autowired
    private TeacherDAO teacherDAO;

    public TeacherDTO assignToStudent(Long teacherId, Long studentId) {
        studentTeacherManager.assign(studentId, teacherId);
        return get(teacherId);
    }

    public List<TeacherDTO> listByStudent(Long studentId) {
        return (List<TeacherDTO>) CustomMapper.mapList(teacherDAO.listByStudent(studentId).toArray(), TeacherDTO.class);
    }

    @Override
    public void delete(Long id) {
        studentTeacherManager.unassignTeacher(id);
        super.delete(id);
    }

    @Override
    public void delete(String uuid) {
        studentTeacherManager.unassignTeacher(get(uuid).getId());
        super.delete(uuid);
    }


}
