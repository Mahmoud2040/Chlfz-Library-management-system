package com.maids.chelfz.repository;
import com.maids.chelfz.entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatronRepository extends JpaRepository<Patron, Long> {

    Optional<Patron> findByEmail(String Email);
}
