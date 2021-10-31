package com.example.demo.hospitalmanagement.patients.controllers;

import com.example.demo.hospitalmanagement.patients.entities.Patient;
import com.example.demo.hospitalmanagement.patients.services.PatientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/patients", produces = MediaType.APPLICATION_JSON_VALUE)
public class PatientController {
    private final PatientService service;

    @GetMapping("")
    public Flux<Patient> getPatients() {
        return service.getPatients();
    }

    @GetMapping("/{patientId}")
    public Mono<Patient> getPatientById(@PathVariable String patientId) {
        return service.getPatientById(patientId);
    }

    @PostMapping("")
    public Mono<Patient> createPatient(@RequestBody @Valid Patient patient) {
        return service.createPatient(patient);
    }

    @DeleteMapping("/{patientId}")
    public Mono<Void> deletePatient(@PathVariable String patientId) {
        return service.deletePatient(patientId);
    }


}
