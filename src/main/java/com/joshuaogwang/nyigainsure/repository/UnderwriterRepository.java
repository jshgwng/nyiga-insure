package com.joshuaogwang.nyigainsure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joshuaogwang.nyigainsure.entity.Underwriter;

public interface UnderwriterRepository extends JpaRepository<Underwriter, Long>{

    Underwriter findByUniqueIdentifier(String uniqueIdentifier);

}
