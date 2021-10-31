package com.example.demo.hospitalmanagement.weekends.repositories;

import com.example.demo.hospitalmanagement.doctors.entities.Doctor;
import com.example.demo.hospitalmanagement.weekends.entities.Weekend;
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
public class WeekendRepo {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public Mono<Weekend> addWeekendDay(Weekend weekend){
        return reactiveMongoTemplate.save(weekend);
    }

    public Flux<Weekend> getAllWeekendDays(){
        return reactiveMongoTemplate.findAll(Weekend.class);
    }

    public Mono<DeleteResult> deleteWeekendDay(Weekend weekend){
        return reactiveMongoTemplate.remove(Query.query(Criteria.where(Weekend.WEEKENDDAY).is(weekend.getWeekendDay())),Weekend.class);
    }

    public Mono<Boolean> checkForWeekendDay(String doctorId,String weekendDay){
        return reactiveMongoTemplate.exists(Query.query(Criteria.where(Weekend.WEEKENDDAY).is(weekendDay)
        .and(Doctor.DOCTORID).is(doctorId)),Weekend.class);
    }

}
