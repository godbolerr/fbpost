package com.work.fbpost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.work.fbpost.domain.Fbpost;

/**
 * Spring Data JPA repository for the Fbpost entity.
 */
@SuppressWarnings("unused")
public interface FbpostRepository extends JpaRepository<Fbpost,Long> {
	
	@Query("SELECT max(t.id) FROM Fbpost t where t.status = :status") 
	Long getMaxIdOfUnreadPost(@Param("status") String status);

}
