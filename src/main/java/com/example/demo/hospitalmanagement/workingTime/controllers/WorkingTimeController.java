package com.example.demo.hospitalmanagement.workingTime.controllers;

import com.example.demo.hospitalmanagement.workingTime.entities.WorkingTime;
import com.example.demo.hospitalmanagement.workingTime.services.WorkingTimeService;
import com.mongodb.client.result.DeleteResult;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/workingTime", produces = MediaType.APPLICATION_JSON_VALUE)
public class WorkingTimeController {

    private final WorkingTimeService workingTimeService;

    @GetMapping("")
    public Flux<WorkingTime> getAllWorkingTime() {
        return workingTimeService.getAllWorkingTime();
    }

    @GetMapping("/{doctorId}")
    public Flux<WorkingTime> getWorkingTimeByDoctorId(@PathVariable String doctorId) {
        return workingTimeService.getWorkingTimeByDoctorid(doctorId);
    }

    @PostMapping("")
    public Mono<WorkingTime> addWorkingTime(@Valid @RequestBody WorkingTime workingTime) {
        return workingTimeService.addWorkingTime(workingTime);
    }

    @GetMapping("/getWorkingTimeByDayAndDoctorId")
    public Flux<WorkingTime> getWorkingTimeByDayAndDoctorId(@RequestParam Map<String, String> allParams) {
        return workingTimeService.getWorkingTimeByDayAndDoctorId(allParams.get("doctorId"), allParams.get("day").toUpperCase());
    }

    @DeleteMapping("/{doctorId}")
    public Mono<DeleteResult> deleteAllWorkingTimesByDoctorId(@PathVariable String doctorId) {
        return workingTimeService.deleteWorkingTimesByDoctorId(doctorId);
    }

    @DeleteMapping("/deleteWorkingTimesByDoctorIdAndDayOfWork")
    public Mono<DeleteResult> deleteWorkingTimesByDoctorIdAndDayOfWork(@RequestParam(name = "doctorId") String id,
                                                                       @RequestParam(name = "day") String dayOfWork) {
        return workingTimeService.deleteWorkingTimesByDoctorIdAndDayOfWork(id, dayOfWork);
    }

    @DeleteMapping("/{id}")
    public Mono<DeleteResult> deleteWorkingTimeByObjectId(@PathVariable ObjectId id) {
        return workingTimeService.deleteWorkingTimeByObjectId(id);
    }

}
