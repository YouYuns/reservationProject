package com.reservation.domain;


import com.reservation.config.BaseEntity;
import com.reservation.enu.ColoerEnum;
import com.reservation.enu.CutEnum;
import com.reservation.enu.PermEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;


@RequiredArgsConstructor
@Getter
@Entity
public class TreatmentInformation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CutEnum cutName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PermEnum permName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ColoerEnum coloerName;

    @OneToOne(mappedBy = "treatmentInformation")
    private Reservation reservation;
}
