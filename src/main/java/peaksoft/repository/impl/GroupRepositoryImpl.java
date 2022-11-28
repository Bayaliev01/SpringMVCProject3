package peaksoft.repository.impl;

import org.springframework.stereotype.Repository;
import peaksoft.model.Company;
import peaksoft.model.Course;
import peaksoft.model.Groups;
import peaksoft.repository.GroupsRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Repository
@Transactional
public class GroupRepositoryImpl implements GroupsRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Groups> getAllGroup(Long id) {
        return entityManager.createQuery("select g from Groups g where g.company.id = :id", Groups.class).setParameter("id", id).getResultList();
    }

    @Override
    public List<Groups> getAllGroupsByCourseId(Long courseId) {
        List<Groups> groupList = entityManager.find(Course.class, courseId).getGroups();
        groupList.forEach(System.out::println);
        return groupList;
    }

    @Override
    public void addGroup(Long id, Groups group) {
        Company company = entityManager.find(Company.class, id);
        company.addGroup(group);
        group.setCompany(company);
        entityManager.merge(group);
    }

    @Override
    public void addGroupByCourseId(Long id, Long courseId, Groups group) {
        Company company = entityManager.find(Company.class, id);
        Course course = entityManager.find(Course.class, courseId);
        company.addGroup(group);
        group.setCompany(company);
        group.addCourse(course);
        course.addGroup(group);
        entityManager.merge(course);
        entityManager.merge(company);
    }

    @Override
    public Groups getGroupById(Long id) {
        return entityManager.find(Groups.class, id);
    }

    @Override
    public void updateGroup(Groups group, Long id) {
        Groups group1 = getGroupById(id);
        group1.setGroupName(group.getGroupName());
        group1.setDateOfStart(group.getDateOfStart());
        group1.setImage(group.getImage());
        entityManager.merge(group1);
    }

    @Override
    public void deleteGroup(Long id) {
        Groups group = entityManager.find(Groups.class, id);
        for (Course c : group.getCourses()) {
            c.getGroups().remove(group);
            group.minusCount();
        }
        group.setCourses(null);
        entityManager.remove(group);
    }

    @Override
    public void assignGroup(Long courseId, Long groupId) throws IOException {
        Groups group = entityManager.find(Groups.class, groupId);
        Course course = entityManager.find(Course.class, courseId);
        if (course.getGroups() != null) {
            for (Groups g : course.getGroups()) {
                if (g.getId() == groupId) {
                    throw new IOException("This group already exists!");
                }
            }
        }
        group.addCourse(course);
        course.addGroup(group);
        entityManager.merge(group);
        entityManager.merge(course);
//        Groups groups = entityManager.find(Groups.class, groupId);
//        Course course = entityManager.find(Course.class, courseId);
//        groups.addCourse(course);
//        course.addGroup(groups);
//        entityManager.merge(groups);
//        entityManager.merge(course);
    }
}
