package com.example.multiplex.repository;

import com.example.multiplex.entity.Code;
import com.example.multiplex.type.AdStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeRepository extends JpaRepository<Code,Long> {
}
