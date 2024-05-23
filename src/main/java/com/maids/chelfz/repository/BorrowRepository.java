package com.maids.chelfz.repository;


import com.maids.chelfz.entity.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Long> {

    public List<Borrow> findByPatronId(Long PatronId);
    public Optional <Borrow> findByPatronIdAndBookId(Long PatronId , Long BookId);

}
