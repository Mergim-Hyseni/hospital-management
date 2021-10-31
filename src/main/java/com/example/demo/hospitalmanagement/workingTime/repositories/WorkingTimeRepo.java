package com.example.demo.hospitalmanagement.workingTime.repositories;

import com.example.demo.hospitalmanagement.BaseEntity;
import com.example.demo.hospitalmanagement.doctors.entities.Doctor;
import com.example.demo.hospitalmanagement.workingTime.entities.WorkingTime;
import com.mongodb.client.result.DeleteResult;
import lombok.AllArgsConstructor;
import lombok.val;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@AllArgsConstructor
public class WorkingTimeRepo {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public Mono<WorkingTime> addWorkingTime(WorkingTime workingTime) {
        return reactiveMongoTemplate.save(workingTime);
    }

    public Flux<WorkingTime> getAllWorkingTime() {
        return reactiveMongoTemplate.findAll(WorkingTime.class);
    }

    public Flux<WorkingTime> getWorkingTimeByDoctorId(String doctorId) {
        val query = Query.query(Criteria.where(Doctor.DOCTORID).is(doctorId));
        return reactiveMongoTemplate.find(query, WorkingTime.class);
    }

    public Flux<WorkingTime> getWorkingTimeByDayAndDoctorId(String doctorId, String day) {
        val query = Query.query(Criteria.where(WorkingTime.DAYOFWORK).is(day).and(WorkingTime.DOCTORID).is(doctorId));
        return reactiveMongoTemplate.find(query, WorkingTime.class);
    }

    public Mono<Boolean> checkIfDoctorIsWorking(String doctorId, String day, int startMinute, int endMinute) {
        val c1 = Criteria.where(WorkingTime.DOCTORID).is(doctorId).and(WorkingTime.DAYOFWORK).is(day);
        val c2 = Criteria.where(WorkingTime.STARTMINUTEOFTHEDAY).lte(startMinute).and(WorkingTime.ENDMINUTEOFTHEDAY).gte(endMinute);
        val query = Query.query(new Criteria().andOperator(c1, c2));

        return reactiveMongoTemplate.exists(query, WorkingTime.class);
    }

    public Mono<Boolean> checkForIntersectBetweenTimeWorkingIntervals(String doctorId, String day, int startMinute, int endMinute) {
        val c1 =
                new Criteria()
                        .andOperator(Criteria.where(WorkingTime.STARTMINUTEOFTHEDAY).gt(startMinute),
                                Criteria.where(WorkingTime.STARTMINUTEOFTHEDAY).lt(endMinute));
        val c2 =
                new Criteria()
                        .andOperator(Criteria.where(WorkingTime.STARTMINUTEOFTHEDAY).lte(startMinute),
                                Criteria.where(WorkingTime.ENDMINUTEOFTHEDAY).gte(endMinute));
        val c3 =
                new Criteria()
                        .andOperator(Criteria.where(WorkingTime.ENDMINUTEOFTHEDAY).gt(startMinute),
                                Criteria.where(WorkingTime.ENDMINUTEOFTHEDAY).lt(endMinute));

        val c4 = Criteria.where(WorkingTime.DOCTORID).is(doctorId).and(WorkingTime.DAYOFWORK).is(day);
        val query = Query.query(c4);
        val c5 = new Criteria().orOperator(c1, c2, c3);
        query.addCriteria(c5);

        return reactiveMongoTemplate.exists(query, WorkingTime.class);
    }

    public Mono<DeleteResult> deleteWorkingTimesByDoctorId(String doctorId) {
        return reactiveMongoTemplate.remove(Query.query(Criteria.where(WorkingTime.DOCTORID).is(doctorId)), WorkingTime.class);
    }

    public Mono<DeleteResult> deleteWorkingTimesByDoctorIdAndDayOfWork(String doctorId, String day) {
        val query = Query.query(Criteria.where(WorkingTime.DOCTORID).is(doctorId).and(WorkingTime.DAYOFWORK).is(day.toUpperCase()));
        return reactiveMongoTemplate.remove(query, WorkingTime.class);
    }

    public Mono<DeleteResult> deleteWorkingTimeByObjectId(ObjectId id) {
        return reactiveMongoTemplate.remove(Query.query(Criteria.where(BaseEntity.ID).is(id)), WorkingTime.class);
    }
}

