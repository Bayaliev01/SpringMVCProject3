package peaksoft.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import peaksoft.model.Groups;
import peaksoft.repository.GroupsRepository;
import peaksoft.service.GroupsService;

import java.io.IOException;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupsService {

    private final GroupsRepository groupRepository;

    @Autowired
    public GroupServiceImpl(GroupsRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public List<Groups> getAllGroup(Long id) {
        return groupRepository.getAllGroup(id);
    }

    @Override
    public List<Groups> getAllGroupsByCourseId(Long courseId) {
        return groupRepository.getAllGroupsByCourseId(courseId);
    }

    @Override
    public void addGroupByCourseId(Long id, Long courseId, Groups group) {
        groupRepository.addGroupByCourseId(id, courseId, group);
    }

    @Override
    public void addGroup(Long id, Groups group) {
        groupRepository.addGroup(id, group);
    }

    @Override
    public Groups getGroupById(Long id) {
        return groupRepository.getGroupById(id);
    }

    @Override
    public void updateGroup(Groups group, Long id) {
        groupRepository.updateGroup(group, id);
    }

    @Override
    public void deleteGroup(Long id) {
        groupRepository.deleteGroup(id);
    }

    @Override
    public void assignGroup(Long courseId, Long groupId) throws IOException {
        groupRepository.assignGroup(courseId, groupId);
    }
}