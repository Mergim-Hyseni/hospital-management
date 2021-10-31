package com.example.demo.hospitalmanagement.workingTime.services;

import com.example.demo.hospitalmanagement.exceptions.BadRequestException;
import com.example.demo.hospitalmanagement.doctors.services.DoctorService;
import com.example.demo.hospitalmanagement.exceptions.NotFoundException;
import com.example.demo.hospitalmanagement.workingTime.entities.WorkingTime;
import com.example.demo.hospitalmanagement.workingTime.repositories.WorkingTimeRepo;
import com.mongodb.client.result.DeleteResult;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@AllArgsConstructor
public class WorkingTimeService {

    private final WorkingTimeRepo workingTimeRepo;
    private final DoctorService doctorService;

    public Mono<WorkingTime> addWorkingTime(WorkingTime workingTime) {
        return doctorService.exists(workingTime.getDoctorId())
                .<WorkingTime>handle((existDoctor, sink) -> {
                    if (existDoctor) sink.next(workingTime);
                    sink.error(new NotFoundException("The doctor doesn't exist"));
                })
                .flatMap(w -> {
                    w.setDayOfWork(w.getDayOfWork().toUpperCase());
                    int startMinuteOfDay = w.getStartHour() * 60 + w.getStartMinute();
                    int endMinuteOfDay = w.getEndHour() * 60 + w.getEndMinute();
                    workingTime.setStartMinuteOfDay(startMinuteOfDay);
                    workingTime.setEndMinuteOfDay(endMinuteOfDay);

                    return checkForIntersectBetweenTimeWorkingIntervals(workingTime.getDoctorId(),
                            workingTime.getDayOfWork(),
                            workingTime.getStartMinuteOfDay(),
                            workingTime.getEndMinuteOfDay());
                })
                .flatMap(isValid -> isValid ?
                        Mono.error(new BadRequestException("Error because has intersection between working intervals"))
                        : workingTimeRepo.addWorkingTime(workingTime)
                );
    }

    public Flux<WorkingTime> getAllWorkingTime() {
        return workingTimeRepo.getAllWorkingTime();
    }

    public Flux<WorkingTime> getWorkingTimeByDoctorid(String doctorId) {
        return workingTimeRepo.getWorkingTimeByDoctorId(doctorId);
    }

    public Flux<WorkingTime> getWorkingTimeByDayAndDoctorId(String doctorId, String day) {
        return workingTimeRepo.getWorkingTimeByDayAndDoctorId(doctorId, day);
    }

    public Mono<Boolean> checkIfDoctorIsWorking(String doctorId, String day, int startMinute, int endMinute) {
        return workingTimeRepo.checkIfDoctorIsWorking(doctorId, day, startMinute, endMinute);
    }

    public Mono<Boolean> checkForIntersectBetweenTimeWorkingIntervals(String doctorId, String day, int startMinute, int endMinute) {
        return workingTimeRepo.checkForIntersectBetweenTimeWorkingIntervals(doctorId, day, startMinute, endMinute);
    }

    public Mono<DeleteResult> deleteWorkingTimesByDoctorId(String doctorId) {
        return workingTimeRepo.deleteWorkingTimesByDoctorId(doctorId);
    }

    public Mono<DeleteResult> deleteWorkingTimesByDoctorIdAndDayOfWork(String doctorId, String day) {
        return workingTimeRepo.deleteWorkingTimesByDoctorIdAndDayOfWork(doctorId, day);
    }

    public Mono<DeleteResult> deleteWorkingTimeByObjectId(ObjectId id) {
        return workingTimeRepo.deleteWorkingTimeByObjectId(id);
    }
}