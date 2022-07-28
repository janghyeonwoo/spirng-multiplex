package com.example.multiplex.repository;

import com.example.multiplex.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUserId(@Param("userId") String userId);
}
