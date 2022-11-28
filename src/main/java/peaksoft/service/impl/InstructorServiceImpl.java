package peaksoft.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import peaksoft.model.Course;
import peaksoft.model.Instructor;
import peaksoft.repository.InstructorRepository;
import peaksoft.service.InstructorService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.List;

@Service
public class InstructorServiceImpl implements InstructorService {


    private final InstructorRepository instructorRepository;

    @Autowired
    public InstructorServiceImpl(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }


    @Override
    public List<Instructor> getAllInstructor(Long courseId) {
        return instructorRepository.getAllInstructor(courseId);
    }

    @Override
    public void addInstructor(Long id, Instructor instructor) throws IOException {
        instructorRepository.addInstructor(id, instructor);
    }

    @Override
    public Instructor getInstructorById(Long id) {
        return instructorRepository.getInstructorById(id);
    }

    @Override
    public void updateInstructor(Instructor instructor, Long id) throws IOException {
        instructorRepository.updateInstructor(instructor, id);
    }

    @Override
    public void deleteInstructor(Long id) {
        instructorRepository.deleteInstructor(id);
    }

    @Override
    public void assignInstructor(Long courseId, Long instructorId) {
        instructorRepository.assignInstructor(courseId, instructorId);
    }

    @Override
    public List<Instructor> listAllInstructors() {
        return instructorRepository.listAllInstructors();
    }
}