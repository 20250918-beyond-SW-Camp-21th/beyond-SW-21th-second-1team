package com.valetparker.chagok.reservation.service;

import com.valetparker.chagok.reservation.domain.Reservation;
import com.valetparker.chagok.reservation.dto.ReservationDto;
import com.valetparker.chagok.reservation.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    //private final UserRepository userRepository;
    //private final ParkinglotRepository parkinglotRepository;
    //private final ModelMapper modelMapper;

    public ReservationService(ReservationRepository reservationRepository/*, UserRepository userRepository, ParkinglotRepository parkinglotRepository*/) {
        this.reservationRepository = reservationRepository;
        //this.userRepository = userRepository;
        //this.parkinglotRepository = parkinglotRepository;
    }

    /*public ReservationDto regist(ReservationDto request) {

        *//* user 정보로 로그인 정보 확인*//*

        *//* 주차장에 남은 자리 있는지 확인*//*

        //Reservation reservation = modelMapper.map(reservationDto, Reservation.class)
    }*/
}
