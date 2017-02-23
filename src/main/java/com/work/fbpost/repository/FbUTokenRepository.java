package com.work.fbpost.repository;

import com.work.fbpost.domain.FbUToken;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the FbUToken entity.
 */
@SuppressWarnings("unused")
public interface FbUTokenRepository extends JpaRepository<FbUToken,Long> {

}
