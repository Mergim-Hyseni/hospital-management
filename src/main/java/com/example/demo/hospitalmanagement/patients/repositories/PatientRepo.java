package com.example.demo.hospitalmanagement.patients.repositories;

import com.example.demo.hospitalmanagement.patients.entities.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class PatientRepo {
    private final ReactiveMongoTemplate reactiveTemplate;

    public Flux<Patient> getPatients() {
        return reactiveTemplate.findAll(Patient.class);
    }

    public Mono<Patient> getPatientById(String patientId) {
        return reactiveTemplate.findById(patientId, Patient.class);
    }

    public Mono<Patient> createPatient(Patient patient) {
        return reactiveTemplate.insert(patient);
    }

    public Mono<Patient> deletePatient(String patientId) {
        return reactiveTemplate.remove(Query.query(Criteria.where(Patient.ID).is(patientId)), Patient.class)
                .then(getPatientById(patientId));
    }



}
