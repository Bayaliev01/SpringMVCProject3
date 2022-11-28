package peaksoft.repository;

import peaksoft.model.Groups;

import java.io.IOException;
import java.util.List;

public interface GroupsRepository {
    List<Groups> getAllGroup(Long id);

    List<Groups> getAllGroupsByCourseId(Long courseId);

    void addGroup(Long id, Groups group);

    void addGroupByCourseId(Long id, Long courseId, Groups group);

    Groups getGroupById(Long id);

    void updateGroup(Groups group, Long id);

    void deleteGroup(Long id);

    void assignGroup(Long courseId, Long groupId) throws IOException;
}
