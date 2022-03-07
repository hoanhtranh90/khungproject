package com.dgtt.business.utils;

import java.util.List;

public class StandardDeviationSample {

    private List<Double> statistics;
    private double stDevSample = -1;
    private double length = 0;
    private double mean = 0;
    private double total = 0;
    private double tempTotal = 0;
//    private double max = Double.MIN_VALUE;
    private double max = -9999;
    private double min = 9999;

    public StandardDeviationSample(List<Double> statistics) {
        this.statistics = statistics;

        length = statistics.size();


        this.statistics.forEach(x -> {
            total += x;

            if (min > x) {
                min = x;
            }

            if (max < x) {
                max = x;
            }

        });

        mean = total / length;
    }

    public double getStDevSample() throws ArithmeticException {

        if (length < 2) {
            //throw exception
            throw new ArithmeticException();
        }

        this.statistics.forEach(x -> {
            tempTotal += Math.pow(x - mean, 2) / (length - 1);
        });


        stDevSample = Math.sqrt(tempTotal);
        return stDevSample;
    }

    public double getLength() {
        return length;
    }

    public static int MODE_EQUAL = 0;
    public static int MODE_LARGER = 1;
    /**
     * @param mode:        0 - equal, 1 - larger, any else ...
     * @param valueCompare
     * @return
     */
    public int countIf(int mode, double valueCompare) {

        int COUNT = 0;
        for (Double statistic : statistics) {

            if (mode == 0) {
                if (statistic == valueCompare) {
                    COUNT++;
                }
            } else if (mode == 1) {
                if (statistic >= valueCompare) {
                    COUNT++;
                }
            } else {
                if (statistic <= valueCompare) {
                    COUNT++;
                }
            }
        }
        return COUNT;
    }

    public double getMean() {
        return mean;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }
}
