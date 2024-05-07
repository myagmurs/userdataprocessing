package org.example.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long>, JpaSpecificationExecutor<UserDetail> {
}
