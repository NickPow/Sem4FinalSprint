package com.example.bst_app.repo;

import com.example.bst_app.model.TreeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreeRecordRepository extends JpaRepository<TreeRecord, Long> {
}
