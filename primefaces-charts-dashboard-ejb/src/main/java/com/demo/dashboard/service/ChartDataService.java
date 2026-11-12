package com.demo.dashboard.service;

import java.math.BigDecimal;
import java.util.List;

import jakarta.ejb.Stateless;

/**
 * EJB sin estado que suministra datos de demostración al dashboard web.
 * Sustituible por acceso a BD sin cambiar la capa Faces.
 */
@Stateless
public class ChartDataService {

    /**
     * Datos estáticos alineados con el Showcase Doughnut (Red/Blue/Yellow = 300/50/100).
     */
    public ChartSampleData loadSampleData() {
        return new ChartSampleData(
                List.of("Red", "Blue", "Yellow"),
                List.of(
                        BigDecimal.valueOf(300),
                        BigDecimal.valueOf(50),
                        BigDecimal.valueOf(100)),
                List.of("Norte", "Sur", "Este", "Oeste"),
                List.of(
                        BigDecimal.valueOf(42),
                        BigDecimal.valueOf(28),
                        BigDecimal.valueOf(18),
                        BigDecimal.valueOf(12)),
                List.of("Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul"),
                List.of(65, 59, 80, 81, 56, 55, 40),
                List.of(45, 72, 60, 70, 68, 62, 48),
                List.of(28, 35, 42, 38, 55, 62, 70),
                List.of(18, 22, 30, 33, 40, 48, 52),
                List.of(120, 113, 175, 143, 118, 159, 110),
                List.of(119, 144, 179, 165, 195, 170, 135));
    }
}
