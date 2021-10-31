package com.example.demo.hospitalmanagement.doctors.services;

import com.example.demo.hospitalmanagement.doctors.entities.Doctor;
import com.example.demo.hospitalmanagement.doctors.repositries.DoctorRepo;
import com.example.demo.hospitalmanagement.workingTime.services.WorkingTimeService;
import com.example.demo.hospitalmanagement.schedules.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DoctorService {
  @Autowired private DoctorRepo repo;

  @Lazy @Autowired private ScheduleService scheduleService;
  @Autowired private WorkingTimeService workingTimeService;

  public Mono<Boolean> exists(String doctorId) {
    return repo.exists(doctorId);
  }

  public Flux<Doctor> getDoctors() {
    return repo.getDoctors();
  }

  public Mono<Doctor> getDoctorById(String doctorId) {
    return repo.getDoctorById(doctorId);
  }

  public Mono<Doctor> createDoctor(Doctor doctor) {
    return repo.createDoctor(doctor);
  }

  public Mono<Void> deleteDoctor(String doctorId) {
    return repo.deleteDoctor(doctorId).and(scheduleService.deleteSchedulesByDoctorId(doctorId))
            .and(workingTimeService.deleteWorkingTimesByDoctorId(doctorId));
  }

  public ScheduleService getScheduleService() {
    return scheduleService;
  }
}
