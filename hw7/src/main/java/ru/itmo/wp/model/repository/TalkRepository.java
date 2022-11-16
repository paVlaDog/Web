package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Talk;

import java.util.List;

public interface TalkRepository {
    Talk find(long id);
    List<Talk> findBySourceOrTargetUserId(long sourceUserId, long targetUserId);
    void save(Talk talk);
}
