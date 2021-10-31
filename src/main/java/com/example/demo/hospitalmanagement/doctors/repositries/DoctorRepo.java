package com.example.demo.hospitalmanagement.doctors.repositries;

import com.example.demo.hospitalmanagement.doctors.entities.Doctor;
import com.mongodb.client.result.DeleteResult;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@AllArgsConstructor
public class DoctorRepo {
  private final ReactiveMongoTemplate reactiveTemplate;

  public Flux<Doctor> getDoctors() {
    return reactiveTemplate.findAll(Doctor.class);
  }

  public Mono<Doctor> getDoctorById(String doctorId) {
    return reactiveTemplate.findOne(Query.query(Criteria.where(Doctor.DOCTORID).is(doctorId)), Doctor.class);
  }

  public Mono<Doctor> createDoctor(Doctor doctor) {
    return reactiveTemplate.insert(doctor);
  }

  public Mono<Boolean> exists(String doctorId) {
    return reactiveTemplate.exists(Query.query(Criteria.where(Doctor.ID).is(doctorId)), Doctor.class);
  }

  public Mono<Doctor> updateDoctor(Doctor doctor){
    return reactiveTemplate.save(doctor);
  }

  public Mono<DeleteResult> deleteDoctor(String doctorId) {

    return reactiveTemplate.remove(Query.query(Criteria.where(Doctor.DOCTORID).is(doctorId)),Doctor.class);
  }
}
