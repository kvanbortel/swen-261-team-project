package com.ufund.api.ufundapi.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Profile;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class for testing the ProfileInfo class
 *
 * @author Kayla Van Bortel
 */
@Tag("Model-tier")
public class ProfileInfoTest {
    private static final String TEST_PROFILE_PIC = "ProfilePic";
    private static final String TEST_PRONOUNS = "she/her";
    private static final String TEST_ALIAS = "my-alias";
    private static final String TEST_BIO = "my-bio";
    private static final Region TEST_REGION = Region.CAPITAL_DISTRICT;
    private static final String TEST_PHONE = "(123) 123-1233";
    private static final String TEST_EMAIL = "hello@me.com";
    private static final String TEST_SSN = "123-12-1234";

    private ProfileInfo profileInfo;
    private ProfileInfo profileInfo2;
    private ProfileInfo profileInfoWithNull;
    private ProfileInfo profileInfoDiffPronouns;
    private ProfileInfo profileInfoDiffBio;
    private ProfileInfo profileInfoDiffRegion;
    private ProfileInfo profileInfoDiffPhone;
    private ProfileInfo profileInfoDiffEmail;
    private ProfileInfo profileInfoDiffSsn;

    @BeforeEach
    public void setUp() {
        profileInfo = new ProfileInfo(TEST_PROFILE_PIC, TEST_PRONOUNS, TEST_ALIAS, TEST_BIO, TEST_REGION, TEST_PHONE, TEST_EMAIL, TEST_SSN);
        profileInfo2 = new ProfileInfo(TEST_PROFILE_PIC, TEST_PRONOUNS, "new-name", "new-bio", TEST_REGION, TEST_PHONE, TEST_EMAIL, TEST_SSN);
        profileInfoWithNull = new ProfileInfo(null, TEST_PRONOUNS, TEST_ALIAS, TEST_BIO, TEST_REGION, TEST_PHONE, TEST_EMAIL, TEST_SSN);
        profileInfoDiffPronouns =  new ProfileInfo(TEST_PROFILE_PIC, "diff-pronouns", TEST_ALIAS, TEST_BIO, TEST_REGION, TEST_PHONE, TEST_EMAIL, TEST_SSN);
        profileInfoDiffBio =  new ProfileInfo(TEST_PROFILE_PIC, TEST_PRONOUNS, TEST_ALIAS, "diff-bio", TEST_REGION, TEST_PHONE, TEST_EMAIL, TEST_SSN);
        profileInfoDiffRegion =  new ProfileInfo(TEST_PROFILE_PIC, TEST_PRONOUNS, TEST_ALIAS, TEST_BIO, Region.NEW_YORK_CITY, TEST_PHONE, TEST_EMAIL, TEST_SSN);
        profileInfoDiffPhone =  new ProfileInfo(TEST_PROFILE_PIC, TEST_PRONOUNS, TEST_ALIAS, TEST_BIO, TEST_REGION, "(999) 999-9999", TEST_EMAIL, TEST_SSN);
        profileInfoDiffEmail =  new ProfileInfo(TEST_PROFILE_PIC, TEST_PRONOUNS, TEST_ALIAS, TEST_BIO, TEST_REGION, TEST_PHONE, "diff-email@email.com", TEST_SSN);
        profileInfoDiffSsn =  new ProfileInfo(TEST_PROFILE_PIC, TEST_PRONOUNS, TEST_ALIAS, TEST_BIO, TEST_REGION, TEST_PHONE, TEST_EMAIL, "999-99-9876");
    }

    // Test for constructing an empty ProfileInfo
    @Test
    public void testCreatEmptyProfileInfo() {
        ProfileInfo newProfileInfo = new ProfileInfo();
        assertNull(newProfileInfo.profilePic);
        assertEquals(newProfileInfo.pronouns, "");
        assertEquals(newProfileInfo.alias, "");
        assertEquals(newProfileInfo.bio, "");
        assertEquals(newProfileInfo.region, Region.NONE);
        assertEquals(newProfileInfo.phoneNumber, "");
        assertEquals(newProfileInfo.email, "");
        assertEquals(newProfileInfo.ssn, "");
    }

    // Test for getting the profilePic
    @Test
    public void testGetProfilePic() {
        assertEquals(profileInfo.getProfilePic(), TEST_PROFILE_PIC);
    }

    // Test for getting the pronouns
    @Test
    public void testGetPronouns() {
        assertEquals(profileInfo.getPronouns(), TEST_PRONOUNS);
    }

    // Test for getting the alias
    @Test
    public void testGetAlias() {
        assertEquals(profileInfo.getAlias(), TEST_ALIAS);
    }

    // Test for getting the bio
    @Test
    public void testGetBio() {
        assertEquals(profileInfo.getBio(), TEST_BIO);
    }

    // Test for getting the Region
    @Test
    public void testGetRegion() {
        assertEquals(profileInfo.getRegion(), TEST_REGION);
    }

    // Test for getting the phoneNumber
    @Test
    public void testGetPhone() {
        assertEquals(profileInfo.getPhoneNumber(), TEST_PHONE);
    }

    // Test for getting the email
    @Test
    public void testGetEmail() {
        assertEquals(profileInfo.getEmail(), TEST_EMAIL);
    }

    // Test for getting the SSN
    @Test
    public void testGetSsn() {
        assertEquals(profileInfo.getSsn(), TEST_SSN);
    }

    // When passed profileInfo is null, profileInfo doesn't update
    @Test
    public void testUpdateNullProfileInfo() {
        ProfileInfo nullInfo = null;
        ProfileInfo newProfileInfo = profileInfo;
        newProfileInfo.updateProfileInfo(nullInfo);
        assertTrue(newProfileInfo.equals(profileInfo));
    }

    // When passed profileInfo is not null profileInfo updates successfully
    @Test
    public void testUpdateProfileInfo() {
        ProfileInfo newProfileInfo = profileInfo;
        newProfileInfo.updateProfileInfo(profileInfo2);
        assertEquals(newProfileInfo.getAlias(), profileInfo2.getAlias());
    }

    // Equals method returns true when objects are the same
    @Test
    public void testEquals_SameObject() {
        assertTrue(profileInfo.equals(profileInfo));
    }

    // Equals method returns false when compared to null object
    @Test
    public void testEquals_NullObject() {
        assertFalse(profileInfo.equals(null));
    }

    // Equals method returns false when comparing to a different class
    @Test
    public void testEquals_DifferentClass() {
        String otherObject = "HelloWorld";
        assertFalse(profileInfo.equals(otherObject));
    }

    // Equals method returns true when comparing equal objects
    @Test
    public void testEquals_EqualObjects() {
        ProfileInfo a1 = profileInfo;
        ProfileInfo a2 = profileInfo;
        assertTrue(a1.equals(a2));
    }

    // Equals method returns false when comparing ProfileInfo with different attributes
    @Test
    public void testEquals_DifferentAttributes() {
        assertFalse(profileInfo.equals(profileInfo2));
    }

    // Equals object returns correct values when comparing with null attributes
    @Test
    public void testEquals_NullAttributes() {
        ProfileInfo a1 = profileInfoWithNull;
        ProfileInfo a2 = profileInfoWithNull;

        assertTrue(a1.equals(a2));
        assertFalse(a2.equals(profileInfo));
    }

    // Equals method returns false when comparing ProfileInfo with different pronoun attribute
    @Test
    public void testEquals_DiffPronounAttributes() {
        assertFalse(profileInfo.equals(profileInfoDiffPronouns));
    }

    // Equals method returns false when comparing ProfileInfo with different bio attribute
    @Test
    public void testEquals_DiffBioAttributes() {
        assertFalse(profileInfo.equals(profileInfoDiffBio));
    }

    // Equals method returns false when comparing ProfileInfo with different Region attribute
    @Test
    public void testEquals_DiffRegionAttributes() {
        assertFalse(profileInfo.equals(profileInfoDiffRegion));
    }

    // Equals method returns false when comparing ProfileInfo with different phoneNumber attribute
    @Test
    public void testEquals_DiffPhoneNumberAttributes() {
        assertFalse(profileInfo.equals(profileInfoDiffPhone));
    }

    // Equals method returns false when comparing ProfileInfo with different email attribute
    @Test
    public void testEquals_DiffEmailAttributes() {
        assertFalse(profileInfo.equals(profileInfoDiffEmail));
    }

    // Equals method returns false when comparing ProfileInfo with different SSN attribute
    @Test
    public void testEquals_DiffSsnAttributes() {
        assertFalse(profileInfo.equals(profileInfoDiffSsn));
    }

    // Test accessing restricted fields when updating ProfileInfo
    @Test
    public void testUpdateProfileInfo_IllegalAccessException() {
        ProfileInfo restrictedInfo = new ProfileInfo();

        // Attempt to make fields inaccessible temporarily
        Field[] fields = ProfileInfo.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(false); // Revoke access to simulate IllegalAccessException
        }
        assertDoesNotThrow(() -> restrictedInfo.updateProfileInfo(profileInfo));
    }
}
