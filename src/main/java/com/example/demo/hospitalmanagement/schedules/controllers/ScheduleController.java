package com.example.demo.hospitalmanagement.schedules.controllers;

import com.example.demo.hospitalmanagement.schedules.entities.Schedule;
import com.example.demo.hospitalmanagement.schedules.services.ScheduleService;
import com.mongodb.client.result.DeleteResult;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/schedules", produces = MediaType.APPLICATION_JSON_VALUE)
public class ScheduleController {
  private final ScheduleService service;

  @GetMapping("")
  public Flux<Schedule> getSchedules() {
    return service.getSchedules();
  }

  @PostMapping("")
  public Mono<Schedule> createSchedule(@RequestBody @Valid Schedule schedule) {
    return service.createSchedule(schedule);
  }

  @DeleteMapping("/{scheduleId}")
  public Mono<DeleteResult> deleteDoctor(@PathVariable String scheduleId) {
    return service.deleteSchedule(scheduleId);
  }

  @GetMapping("/ts")
  public Mono<DeleteResult> ts(){
    return service.deleteSchedulesByPatientId("4633");
  }
}
