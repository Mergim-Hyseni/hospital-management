package com.example.demo.hospitalmanagement.schedules.repositries;

import com.example.demo.hospitalmanagement.schedules.entities.Schedule;
import com.mongodb.client.result.DeleteResult;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@AllArgsConstructor
public class SchedulerRepo {
    private final ReactiveMongoTemplate reactiveTemplate;

    public Flux<Schedule> getSchedules() {
        return reactiveTemplate.findAll(Schedule.class);
    }

    public Mono<Boolean> checkForFreeScheduler(String doctorId, Schedule.Interval timeInterval) {
        val c1 =
                new Criteria()
                        .andOperator(
                                Criteria.where(Schedule.TIME_INTERVAL_FROM).gte(timeInterval.getFrom()),
                                Criteria.where(Schedule.TIME_INTERVAL_FROM).lte(timeInterval.getTo()));
        val c2 =
                new Criteria()
                        .andOperator(
                                Criteria.where(Schedule.TIME_INTERVAL_FROM).lte(timeInterval.getFrom()),
                                Criteria.where(Schedule.TIME_INTERVAL_FROM).gte(timeInterval.getTo()));
        val c3 =
                new Criteria()
                        .andOperator(
                                Criteria.where(Schedule.TIME_INTERVAL_TO).gte(timeInterval.getFrom()),
                                Criteria.where(Schedule.TIME_INTERVAL_TO).lte(timeInterval.getTo()));
        val c4 =
                new Criteria()
                        .andOperator(
                                Criteria.where(Schedule.TIME_INTERVAL_FROM).gte(timeInterval.getFrom()),
                                Criteria.where(Schedule.TIME_INTERVAL_FROM).lte(timeInterval.getTo()));

        val criteria = Criteria.where(Schedule.DOCTOR_ID).is(doctorId);
        val query = Query.query(criteria);
        query.addCriteria(new Criteria().orOperator(c1, c2, c3, c4));

        return reactiveTemplate.exists(query, Schedule.class);
    }

    public Mono<Schedule> createSchedule(Schedule schedule) {

        return reactiveTemplate.insert(schedule);
    }

    public Mono<DeleteResult> deleteSchedule(String scheduleId) {
        val query = Query.query(Criteria.where(Schedule.ID).is(scheduleId));
        return reactiveTemplate.remove(query, Schedule.class);
    }

    public Mono<DeleteResult> deleteSchedulesByDoctorId(String doctorId) {
        val query = Query.query(Criteria.where(Schedule.DOCTOR_ID).is(doctorId));
        return reactiveTemplate.remove(query, Schedule.class);
    }

    public Mono<DeleteResult> deleteSchedulesByPatientId(String patientId) {
        val query = Query.query(Criteria.where("patientId").is(patientId));
        return reactiveTemplate.remove(query, Schedule.class);
    }
}
