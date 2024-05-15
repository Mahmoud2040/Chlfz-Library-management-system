package com.maids.chelfz.service;

import com.maids.chelfz.entity.Patron;

import java.util.List;
import java.util.Optional;

public interface PatronService {
     List<Patron> getAllPatrons();
     Optional<Patron> getPatronById(Long id);
     Optional<Patron> getPatronByEmail(String email);
     Patron savePatron(Patron patron);
     void deletePatron(Long id);
}
