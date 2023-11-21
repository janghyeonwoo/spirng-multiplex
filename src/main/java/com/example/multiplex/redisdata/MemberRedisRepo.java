package com.example.multiplex.redisdata;

import org.springframework.data.repository.CrudRepository;

public interface MemberRedisRepo extends CrudRepository<MemberRedis, String> {
}
