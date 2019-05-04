package com.depromeet.deprocheck.deprocheckapi.utils;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class DistanceUtilsTest {
    @Test
    public void test() {
        double lat1 = 37.598048;
        double lon1 = 127.014708;
        double lat2 = 37.592939;
        double lon2 = 127.016467;

        double result = DistanceUtils.getDistance(lat1, lon1, lat2, lon2);

        assertTrue(result > 500.0);
    }
}