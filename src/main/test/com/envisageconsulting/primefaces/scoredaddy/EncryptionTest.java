package com.envisageconsulting.primefaces.scoredaddy;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.Assert.*;

public class EncryptionTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testPasswordValidation_Success() throws InvalidKeySpecException, NoSuchAlgorithmException {

        Encryption encryption = new Encryption();

        String password = "password";
        String passwordHash = encryption.generateStringPasswordHash(password);
        assertTrue(encryption.validatePassword("password", passwordHash));
    }

    @Test
    public void testPasswordValidation_Failure() throws InvalidKeySpecException, NoSuchAlgorithmException {

        Encryption encryption = new Encryption();

        String password = "password";
        String passwordHash = encryption.generateStringPasswordHash(password);
        assertFalse(encryption.validatePassword("passwordbad", passwordHash));
    }

}