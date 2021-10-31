package com.example.demo.hospitalmanagement.patients.services;

import com.example.demo.hospitalmanagement.patients.entities.Patient;
import com.example.demo.hospitalmanagement.patients.repositories.PatientRepo;
import com.example.demo.hospitalmanagement.schedules.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PatientService {
    @Autowired
    private PatientRepo repo;
    @Lazy
    @Autowired
    private ScheduleService scheduleService;

    public Flux<Patient> getPatients() {
        return repo.getPatients();
    }

    public Mono<Patient> createPatient(Patient patient) {
        return repo.createPatient(patient);
    }

    public Mono<Void> deletePatient(String patientId) {
        return repo.deletePatient(patientId).and(scheduleService.deleteSchedulesByPatientId(patientId));
    }

    public Mono<Patient> getPatientById(String patientId) {
        return repo.getPatientById(patientId);
    }


}
