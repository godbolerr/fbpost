package com.work.fbpost.repository;

import com.work.fbpost.domain.Fbpost;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Fbpost entity.
 */
@SuppressWarnings("unused")
public interface FbpostRepository extends JpaRepository<Fbpost,Long> {

}
