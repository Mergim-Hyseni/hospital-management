package com.example.demo.hospitalmanagement.schedules.services;

import com.example.demo.hospitalmanagement.doctors.services.DoctorService;
import com.example.demo.hospitalmanagement.exceptions.BadRequestException;
import com.example.demo.hospitalmanagement.schedules.entities.Schedule;
import com.example.demo.hospitalmanagement.schedules.repositries.SchedulerRepo;
import com.example.demo.hospitalmanagement.workingTime.services.WorkingTimeService;
import com.mongodb.client.result.DeleteResult;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@AllArgsConstructor
public class ScheduleService {
    private final SchedulerRepo repo;
    private final DoctorService doctorService;
    private final WorkingTimeService workingTimeService;

    public Flux<Schedule> getSchedules() {
        return repo.getSchedules();
    }

    public Mono<Schedule> createSchedule(Schedule schedule) {
        return doctorService
                .exists(schedule.getDoctorId())
                .flatMap(exist -> {
                    if (exist) return Mono.just(schedule);
                    return Mono.error(new BadRequestException("doctorId does not exists"));
                })
                .flatMap(schedule1 -> {
                    LocalDateTime from = LocalDateTime.ofInstant(schedule1.getTimeInterval().getFrom(), ZoneOffset.of("+00:00"));
                    LocalDateTime to = LocalDateTime.ofInstant(schedule1.getTimeInterval().getTo(), ZoneOffset.of("+00:00"));
                    int startMinute = from.getHour() * 60 + from.getMinute();
                    int endMinute = to.getHour() * 60 + to.getMinute();
                    return workingTimeService.checkIfDoctorIsWorking(schedule1.getDoctorId(),
                            from.getDayOfWeek().toString(),
                            startMinute,
                            endMinute);
                })
                .flatMap(w -> {
                    if (!w) return Mono.just(schedule);
                    return Mono.error(new BadRequestException("The doctor is not working in this time"));
                })
                .flatMap(it -> repo.checkForFreeScheduler(it.getDoctorId(), it.getTimeInterval()))
                .flatMap(
                        isScheduled -> {
                            if (isScheduled) return Mono.error(new BadRequestException("No free time"));
                            return repo.createSchedule(schedule);
                        });
    }

    public Mono<DeleteResult> deleteSchedule(String scheduleId) {
        return repo.deleteSchedule(scheduleId);
    }

    public Mono<DeleteResult> deleteSchedulesByDoctorId(String doctorId) {
        return repo.deleteSchedulesByDoctorId(doctorId);
    }

    public Mono<DeleteResult> deleteSchedulesByPatientId(String patientId) {
        return repo.deleteSchedulesByPatientId(patientId);
    }
}

