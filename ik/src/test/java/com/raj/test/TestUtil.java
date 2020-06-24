package com.raj.test;

import com.raj.Util;
import org.junit.Assert;
import org.junit.Test;

public class TestUtil {

    @Test
    public void ispalin_test() {
        Assert.assertTrue(Util.isPalin("abba"));
    }
}
