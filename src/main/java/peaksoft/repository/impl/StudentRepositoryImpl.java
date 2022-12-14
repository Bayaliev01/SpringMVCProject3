package peaksoft.repository.impl;

import peaksoft.repository.StudentRepository;

import org.springframework.stereotype.Repository;
import peaksoft.model.Course;
import peaksoft.model.Groups;
import peaksoft.model.Instructor;
import peaksoft.model.Student;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class StudentRepositoryImpl implements StudentRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Student> getAllStudents(Long id) {
        return manager.createQuery("select s from Student s where s.groups.id = :id", Student.class).setParameter("id", id).getResultList();
    }

    @Override
    public void addStudent(Long id, Student student) {
        Groups group = manager.find(Groups.class, id);
        group.addStudent(student);
        student.setGroups(group);
        manager.merge(student);
        for (Course c : student.getGroups().getCompany().getCourses()) {
            for (Instructor i : c.getInstructors()) {
                i.plus();
            }
        }
    }

    @Override
    public Student getStudentById(Long id) {
        return manager.find(Student.class, id);
    }

    @Override
    public void updateStudent(Student student, Long id) {
        Student student1 = manager.find(Student.class, id);
        student1.setFirstName(student.getFirstName());
        student1.setLastName(student.getLastName());
        student1.setPhoneNumber(student.getPhoneNumber());
        student1.setEmail(student.getEmail());
        student1.setStudyFormat(student.getStudyFormat());
        manager.merge(student1);
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = getStudentById(id);
        student.getGroups().getCompany().minusStudent();
        for (Course c : student.getGroups().getCourses()) {
            for (Instructor i : c.getInstructors()) {
                i.minus();
            }
        }
        student.setGroups(null);
        manager.remove(manager.find(Student.class, id));
    }

    @Override
    public void assignStudent(Long groupId, Long studentId) {
        Student student = manager.find(Student.class, studentId);
        Groups group = manager.find(Groups.class, groupId);
        group.addStudent(student);
        student.setGroups(group);
        manager.merge(student);
    }

    @Override
    public List<Student> listAllStudents() {
        return manager.createQuery("select s from Student s", Student.class).getResultList();
    }
}