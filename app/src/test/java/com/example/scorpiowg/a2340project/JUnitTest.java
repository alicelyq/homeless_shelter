package com.example.scorpiowg.a2340project;

import com.example.scorpiowg.a2340project.model.Model;
import com.example.scorpiowg.a2340project.model.Shelter;
import com.example.scorpiowg.a2340project.model.ShelterEmployee;
import com.example.scorpiowg.a2340project.model.User;

import org.junit.Test;


import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by wangjingbo on 4/8/18.
 */

@SuppressWarnings("ALL")
public class JUnitTest {
    @Test
    public void testFilterByGender() {
        // Jingbo Wang
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

    @Test
    public void testFilterByAge() {
        // Michael Wang
        Shelter familyChildrenShelter = new Shelter("4", "Atlanta's Children Center", "40", "Families w/ Children under 5", "-84.384433", "33.770949", "607 Peachtree Street NE, Atlanta, GA 30308", "Children's programs, early childhood education", "(404) 892-3713");
        Shelter youngAdultsShelter = new Shelter("7", "Covenant House Georgia ", "80", "Childrens/Young adults ", "-84.437988", "33.78823", "1559 Johnson Road NW, Atlanta, GA 30318", "Crisis services/Career Preparation", "(404)-937-6957");
        String familyChildrenShelterConstraint = familyChildrenShelter.getRestriction();
        String youngAdultsShelterConstraint = youngAdultsShelter.getRestriction();

        assertTrue(Model.getInstance().filterByAge("Anyone", familyChildrenShelterConstraint));
        assertTrue(Model.getInstance().filterByAge("Young Adults", youngAdultsShelterConstraint));
        assertTrue(Model.getInstance().filterByAge("Families with newborns", familyChildrenShelterConstraint));
        assertFalse(Model.getInstance().filterByAge("Young Adults", familyChildrenShelterConstraint));
    }

    @Test
    public void testFilterByName() {
        // Konrad Wang
        Shelter shelter = new Shelter("2", "The Shepherd's Inn", "450", "Men", "-84.39265", "33.765162", "156 Mills Street, Atlanta, Georgia 30313", "Temporary, Residential Recovery", "(404) 367-2493");

        assertTrue(Model.getInstance().filterByName("Shepherd", shelter));
        assertFalse(Model.getInstance().filterByName("Shepherdd", shelter));
    }

    @Test
    public void testLoginUser() {
        //Ruinan Wang
        Map<String, User> userDatabase = new HashMap<>();
        User curUser = new ShelterEmployee("test", "testid", "test", true, "1");
        userDatabase.put("testid", curUser);
        assertTrue(Model.getInstance().loginUser(userDatabase, "testid", "test"));
        assertFalse(Model.getInstance().loginUser(userDatabase, "hahaha", "ha"));
    }

    @Test
    public void testRegisterUser() {
        //Yunqi Liu
        Map<String, User> userDatabase = new HashMap<>();
        User curUser = new ShelterEmployee("test", "testid", "test", true, "1");
        userDatabase.put("testid", curUser);
        //confirmed and exist
        assertFalse(Model.getInstance().registerUser(userDatabase, "testid", "test", "test", "test", true, "1"));
        //not confirmed and exist
        assertFalse(Model.getInstance().registerUser(userDatabase, "testid", "test", "wrongconfirm", "test", true, "1"));
        //not confirmed and not exist
        assertFalse(Model.getInstance().registerUser(userDatabase, "newUser", "test", "wrongconfirm", "newname", true, "1"));
        //confirmed and not exist
        assertTrue(Model.getInstance().registerUser(userDatabase, "newUser", "new", "new", "newname", true, "1"));
    }

}
