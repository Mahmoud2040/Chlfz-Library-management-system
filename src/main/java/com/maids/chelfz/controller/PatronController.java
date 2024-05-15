package com.maids.chelfz.controller;

import com.maids.chelfz.entity.Book;
import com.maids.chelfz.entity.Patron;
import com.maids.chelfz.exception.ConflictException;
import com.maids.chelfz.exception.RecordNotFoundException;
import com.maids.chelfz.service.impl.PatronServiceImpl;
import com.maids.chelfz.util.response.ApiResponse;
import com.maids.chelfz.util.response.ApiResponseManager;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {

    private PatronServiceImpl patronServiceImpl;
    private final ApiResponseManager<Patron> patronApiResponseManager;
    private final ApiResponseManager<List<Patron>> patronListApiResponseManager;
    private final ApiResponseManager<Void> defualtApiResponseManager;

    public PatronController(PatronServiceImpl patronServiceImpl,
                            ApiResponseManager<Patron> patronApiResponseManager,
                            ApiResponseManager<List<Patron>> patronListApiResponseManager,
                            ApiResponseManager<Void> defualtApiResponseManager) {
        this.patronServiceImpl = patronServiceImpl;
        this.patronApiResponseManager = patronApiResponseManager;
        this.patronListApiResponseManager = patronListApiResponseManager;
        this.defualtApiResponseManager = defualtApiResponseManager;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Patron>>> getAllPatrons() {
        List<Patron> patrons = patronServiceImpl.getAllPatrons();
        return ResponseEntity.ok(patronListApiResponseManager
                .successResponse(patrons));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Patron>> getPatronById(@PathVariable Long id) {
        return patronServiceImpl.getPatronById(id)
                .map(patron -> ResponseEntity.status(HttpStatus.OK).body(patronApiResponseManager.successResponse(patron)))
                .orElseThrow(() -> new RecordNotFoundException("Patron not found with id: " + id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Patron>> addPatron(@Valid @RequestBody Patron patron) {

        boolean isEmailExist = validateEmailExistance(patron.getEmail());
        if (isEmailExist)
            throw new ConflictException("the email address is already exist");

        Patron savedPatron = patronServiceImpl.savePatron(patron);
        return ResponseEntity.status(HttpStatus.CREATED).body(patronApiResponseManager.successMassageData("Patron Added Successfully", savedPatron));
    }

    private boolean validateEmailExistance(String patronEmail) {
        Optional<Patron> existPatron = patronServiceImpl.getPatronByEmail(patronEmail);
        return existPatron.isPresent();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Patron>> updatePatron(@PathVariable Long id, @Valid @RequestBody Patron updatedpatron) {
        Optional<Patron> existingPatronOptional = patronServiceImpl.getPatronById(id);
        if (existingPatronOptional.isEmpty()) {
            throw new RecordNotFoundException("this Patron Not Found");
        }
        Patron existingPatron = existingPatronOptional.get();
        existingPatron.setName(updatedpatron.getName());
        existingPatron.setEmail(updatedpatron.getEmail());
        existingPatron.setPhone(updatedpatron.getPhone());
        existingPatron.setAddress(updatedpatron.getAddress());

        Patron savedPatron = patronServiceImpl.savePatron(existingPatron);

        return ResponseEntity.ok(patronApiResponseManager.successMassageData("Patron Updated Successfully", savedPatron));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePatron(@PathVariable Long id) {
        Optional<Patron> existingPatronOptional = patronServiceImpl.getPatronById(id);
        if (existingPatronOptional.isEmpty()) {
            throw new RecordNotFoundException("this Patron Not Found");
        }
        patronServiceImpl.deletePatron(id);
        return ResponseEntity.ok(defualtApiResponseManager.successMassage("Deleted successfully"));
    }
}