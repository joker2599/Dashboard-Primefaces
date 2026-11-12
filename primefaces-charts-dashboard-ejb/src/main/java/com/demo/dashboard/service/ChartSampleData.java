package com.demo.dashboard.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * DTO inmutable con series de demostración para las gráficas del dashboard.
 */
public final class ChartSampleData implements Serializable {

    private static final long serialVersionUID = 1L;

    private final List<String> doughnutLabels;
    private final List<BigDecimal> doughnutValues;
    private final List<String> pieLabels;
    private final List<BigDecimal> pieValues;
    private final List<String> monthLabels;
    private final List<Number> salesCurrentYear;
    private final List<Number> salesPreviousYear;
    private final List<Number> activeUsers;
    private final List<Number> newSessions;
    private final List<Number> orders;
    private final List<Number> orderTarget;

    public ChartSampleData(
            List<String> doughnutLabels,
            List<BigDecimal> doughnutValues,
            List<String> pieLabels,
            List<BigDecimal> pieValues,
            List<String> monthLabels,
            List<Number> salesCurrentYear,
            List<Number> salesPreviousYear,
            List<Number> activeUsers,
            List<Number> newSessions,
            List<Number> orders,
            List<Number> orderTarget) {
        this.doughnutLabels = List.copyOf(doughnutLabels);
        this.doughnutValues = List.copyOf(doughnutValues);
        this.pieLabels = List.copyOf(pieLabels);
        this.pieValues = List.copyOf(pieValues);
        this.monthLabels = List.copyOf(monthLabels);
        this.salesCurrentYear = List.copyOf(salesCurrentYear);
        this.salesPreviousYear = List.copyOf(salesPreviousYear);
        this.activeUsers = List.copyOf(activeUsers);
        this.newSessions = List.copyOf(newSessions);
        this.orders = List.copyOf(orders);
        this.orderTarget = List.copyOf(orderTarget);
    }

    public List<String> getDoughnutLabels() {
        return doughnutLabels;
    }

    public List<BigDecimal> getDoughnutValues() {
        return doughnutValues;
    }

    public List<String> getPieLabels() {
        return pieLabels;
    }

    public List<BigDecimal> getPieValues() {
        return pieValues;
    }

    public List<String> getMonthLabels() {
        return monthLabels;
    }

    public List<Number> getSalesCurrentYear() {
        return salesCurrentYear;
    }

    public List<Number> getSalesPreviousYear() {
        return salesPreviousYear;
    }

    public List<Number> getActiveUsers() {
        return activeUsers;
    }

    public List<Number> getNewSessions() {
        return newSessions;
    }

    public List<Number> getOrders() {
        return orders;
    }

    public List<Number> getOrderTarget() {
        return orderTarget;
    }
}
