package com.stormpath;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-config.xml")
public class PasswordValidationServiceUnitTest {

    @Autowired
    private PasswordValidationService passwordValidationService;

    @Test
    public void testValidPasswords() {
        String password = "abc123";
        assertTrue(passwordValidationService.validatePassword(password));
        password = "123abc";
        assertTrue(passwordValidationService.validatePassword(password));
        password = "123abc123";
        assertTrue(passwordValidationService.validatePassword(password));
        password = "abc123abc";
        assertTrue(passwordValidationService.validatePassword(password));
    }

    @Test
    public void testMinLength() {
        if (passwordValidationService.getMinLength() != null) {
            String password = "abc1";
            assertFalse(passwordValidationService.validatePassword(password));
        }
    }

    @Test
    public void testMaxLength() {
        if (passwordValidationService.getMaxLength() != null) {
            String password = "abc123abc1234";
            assertFalse(passwordValidationService.validatePassword(password));
        }
    }

    @Test
    public void testNoMixture() {
        if (passwordValidationService.getRegexExpressionMap() != null &&
                passwordValidationService.getRegexExpressionMap().get("(?:\\d+[a-z]|[a-z]+\\d)[a-z\\d]*") != null) {
            String password = "abcdef";
            assertFalse(passwordValidationService.validatePassword(password));
            password = "123456";
            assertFalse(passwordValidationService.validatePassword(password));
        }
    }

    @Test
    public void testRepeatingSequence() {
        if (passwordValidationService.getRegexExpressionMap() != null &&
                passwordValidationService.getRegexExpressionMap().get("(..+)\\1") != null) {
            String password = "abc123abc123";
            assertFalse(passwordValidationService.validatePassword(password));
            password = "ab12ab12";
            assertFalse(passwordValidationService.validatePassword(password));
            password = "123123a";
            assertFalse(passwordValidationService.validatePassword(password));
        }
    }
}
