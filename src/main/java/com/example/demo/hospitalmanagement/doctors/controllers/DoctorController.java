package com.example.demo.hospitalmanagement.doctors.controllers;

import com.example.demo.hospitalmanagement.doctors.entities.Doctor;
import com.example.demo.hospitalmanagement.doctors.services.DoctorService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/doctors", produces = MediaType.APPLICATION_JSON_VALUE)
public class DoctorController {
  private final DoctorService service;

  @GetMapping("")
  public Flux<Doctor> getDoctors() {
    return service.getDoctors();
  }

  @PostMapping("")
  public Mono<Doctor> getDoctors(@RequestBody @Valid Doctor doctor) {
    return service.createDoctor(doctor);
  }

  @DeleteMapping("/{doctorId}")
  public Mono<Void> deleteDoctor(@PathVariable String doctorId) {
    return service.deleteDoctor(doctorId);
  }

  @GetMapping("/{doctorId}")
  public Mono<Doctor> getDoctorById(@PathVariable String doctorId) {
    return service.getDoctorById(doctorId);
  }

}
