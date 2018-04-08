package com.example.scorpiowg.a2340project;

import com.example.scorpiowg.a2340project.model.Model;
import com.example.scorpiowg.a2340project.model.Shelter;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by wangjingbo on 4/8/18.
 */

public class JUnitTest {
    @Test
    public void testFilterByGender() {
        Shelter maleShelter = new Shelter("2", "The Shepherd's Inn", "450", "Men", "-84.39265", "33.765162", "156 Mills Street, Atlanta, Georgia 30313", "Temporary, Residential Recovery", "(404) 367-2493");
        Shelter femaleShelter = new Shelter("0", "My Sister's House", "264", "Women/Children", "-84.410142", "33.780174", "921 Howell Mill Road, Atlanta, Georgia 30318", "Temporary, Emergency, Residential Recovery", "(404) 367-2465");
        String maleShelterConstraint = maleShelter.getRestriction();
        String femaleShelterConstraint = femaleShelter.getRestriction();

        assertTrue(Model.getInstance().filterByGender("Any", maleShelterConstraint));
        assertTrue(Model.getInstance().filterByGender("Men", maleShelterConstraint));
        assertTrue(Model.getInstance().filterByGender("Women", femaleShelterConstraint));
        assertFalse(Model.getInstance().filterByGender("Men", femaleShelterConstraint));
        assertFalse(Model.getInstance().filterByGender("Women", maleShelterConstraint));
    }
}
