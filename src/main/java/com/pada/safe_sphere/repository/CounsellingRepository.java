package com.pada.safe_sphere.repository;

import com.pada.safe_sphere.entity.ReportAbuse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CounsellingRepository extends JpaRepository<ReportAbuse, Long> {
}
