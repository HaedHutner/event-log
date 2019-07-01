package dev.mvvasilev.eventlog.repository;

import dev.mvvasilev.eventlog.entity.LoggedEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoggedEventRepository extends MongoRepository<LoggedEvent, String> {
}