package com.example.demo.hospitalmanagement.weekends.controllers;

import com.example.demo.hospitalmanagement.weekends.entities.Weekend;
import com.example.demo.hospitalmanagement.weekends.services.WeekendService;
import com.mongodb.client.result.DeleteResult;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/weekend", produces = MediaType.APPLICATION_JSON_VALUE)
public class WeekendController {

    private final WeekendService weekendService;

    @GetMapping("")
    public Flux<Weekend> getAllWeekendDays() {
        return weekendService.getAllWeekendDays();
    }

    @PostMapping("")
    public Mono<Weekend> addWeekendDay(@Valid @RequestBody Weekend weekend) {
        return weekendService.addWeekendDay(weekend);
    }

    @DeleteMapping("")
    public Mono<DeleteResult> deleteWeekendDay(@Valid @RequestBody Weekend weekend) {
        return weekendService.deleteWeekendDay(weekend);
    }

}
