package com.maids.chelfz.service.impl;

import com.maids.chelfz.entity.Patron;
import com.maids.chelfz.repository.PatronRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class PatronServiceImplTest {
    @Mock
    private PatronRepository patronRepository;

    @InjectMocks
    private PatronServiceImpl patronService;

    @Test
    public void testGetAllPatrons() {
        List<Patron> mockPatrons = new ArrayList<>();
        mockPatrons.add(new Patron(1L, "Patron1", "Patron1@chelfz.com", "1234567890", "Address 1"));
        mockPatrons.add(new Patron(2L, "Patron2", "Patron2@chelfz.com", "0987654321", "Address 2"));
        when(patronRepository.findAll()).thenReturn(mockPatrons);
        List<Patron> returnedPatrons = patronService.getAllPatrons();
        assertEquals(2, returnedPatrons.size());
        assertEquals(mockPatrons, returnedPatrons);
    }

    @Test
    public void testGetPatronById_PatronExists() {
        Long patronId = 1L;
        Patron mockPatron = new Patron(1L, "Patron1", "Patron1@chelfz.com", "1234567890", "Address 1");
        when(patronRepository.findById(patronId)).thenReturn(Optional.of(mockPatron));
        Optional<Patron> returnedPatron = patronService.getPatronById(patronId);
        assertEquals(Optional.of(mockPatron), returnedPatron);
    }

    @Test
    public void testGetPatronById_PatronNotFound() {
        Long patronId = 2L;
        Optional<Patron> returnedPatron = patronService.getPatronById(patronId);
        assertEquals(Optional.empty(), returnedPatron);
    }

    // Similar tests can be written for getPatronByEmail, savePatron, and deletePatron methods

    @Test
    public void testSavePatron_ValidPatron() {
        Patron patronToSave = new Patron(null, "John Doe", "john@example.com", "1234567890", "Address 1");
        Patron savedPatron = new Patron(1L, "John Doe", "john@example.com", "1234567890", "Address 1");
        when(patronRepository.save(patronToSave)).thenReturn(savedPatron);
        Patron returnedPatron = patronService.savePatron(patronToSave);

        assertEquals(savedPatron, returnedPatron);
        verify(patronRepository, times(1)).save(patronToSave);
    }

    @Test
    public void testGetPatronByEmail_PatronExists() {
        String email = "john@example.com";
        Patron mockPatron = new Patron(1L, "John Doe", email, "1234567890", "Address 1");
        when(patronRepository.findByEmail(email)).thenReturn(Optional.of(mockPatron));
        Optional<Patron> returnedPatron = patronService.getPatronByEmail(email);
        assertEquals(Optional.of(mockPatron), returnedPatron);
    }

    @Test
    public void testGetPatronByEmail_PatronNotFound() {
        String email = "nonexistent@example.com";
        when(patronRepository.findByEmail(email)).thenReturn(Optional.empty());
        Optional<Patron> returnedPatron = patronService.getPatronByEmail(email);
        assertEquals(Optional.empty(), returnedPatron);
    }

}