package com.example.demo.hospitalmanagement.weekends.services;

import com.example.demo.hospitalmanagement.exceptions.BadRequestException;
import com.example.demo.hospitalmanagement.weekends.entities.Weekend;
import com.example.demo.hospitalmanagement.weekends.repositories.WeekendRepo;
import com.example.demo.hospitalmanagement.doctors.services.DoctorService;
import com.mongodb.client.result.DeleteResult;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class WeekendService {

    private WeekendRepo weekendRepo;
    private DoctorService doctorService;

    public Mono<Weekend> addWeekendDay(Weekend weekend) {
        return doctorService.exists(weekend.getDoctorId())
//                .<Weekend>handle((exist,sink) -> {     // METHOD 1)
//                    if (exist) sink.next(weekend);
//                    sink.error(new BadRequestException("doctor doesnt't exist"));
//                }).flatMap(e -> weekendRepo.addWeekendDay(e));
                .flatMap(exist -> {        // METHOD 2)
                    if (exist) {
                        weekend.setWeekendDay(weekend.getWeekendDay().toUpperCase());
                        return weekendRepo.addWeekendDay(weekend);
                    }
                    return Mono.error(new BadRequestException("doctor does not exists"));
                });
    }

    public Mono<DeleteResult> deleteWeekendDay(Weekend weekend) {
        return weekendRepo.deleteWeekendDay(weekend);
    }

    public Flux<Weekend> getAllWeekendDays() {
        return weekendRepo.getAllWeekendDays();
    }

    public Mono<Boolean> checkForWeekendDay(String doctorId, String weekendDay) {
        return weekendRepo.checkForWeekendDay(doctorId, weekendDay);
    }

}
