package com.maids.chelfz.service.impl;

import com.maids.chelfz.entity.Patron;
import com.maids.chelfz.exception.RecordNotFoundException;
import com.maids.chelfz.repository.PatronRepository;
import com.maids.chelfz.service.PatronService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatronServiceImpl implements PatronService {

    private final PatronRepository patronRepository;

    public PatronServiceImpl(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    @Override
    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }
    @Override
    public Optional<Patron> getPatronById(Long id) {
        return patronRepository.findById(id);
    }

    @Override
    public Optional<Patron> getPatronByEmail(String email) {
        return patronRepository.findByEmail(email);
    }

    @Override
    public Patron savePatron(Patron patron) {
        return patronRepository.save(patron);

    }
    @Override
    public void deletePatron(Long id) {
        patronRepository.deleteById(id);
    }
}
