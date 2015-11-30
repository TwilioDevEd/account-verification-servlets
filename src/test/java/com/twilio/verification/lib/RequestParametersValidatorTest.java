package com.twilio.verification.lib;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class RequestParametersValidatorTest {

    @Mock
    HttpServletRequest request;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void validatesReturnsTrueWhenAllTheParametersAreValid() {

        when(request.getParameter("name")).thenReturn("Bob");
        when(request.getParameter("email")).thenReturn("bob@example.com");

        RequestParametersValidator validator = new RequestParametersValidator(request);
        boolean result = validator.validatePresence("name", "email");

        assertThat(result, is(true));
        verify(request, never()).setAttribute(anyString(), anyObject());
    }

    @Test
    public void validatesReturnsFalseAndAddsErrorsToRequestWhenParametersAreInvalid() {

        when(request.getParameter("name")).thenReturn("");
        when(request.getParameter("countryCode")).thenReturn("");

        RequestParametersValidator validator = new RequestParametersValidator(request);
        boolean result = validator.validatePresence("name", "countryCode");

        assertThat(result, is(false));
        verify(request, times(1)).setAttribute("nameError", "Name can't be blank");
        verify(request, times(1)).setAttribute("countryCodeError", "Country Code can't be blank");
    }

    @Test
    public void validateEmailReturnsTrueWhenEmailIsValid() {

        when(request.getParameter("email")).thenReturn("bob@example.com");

        RequestParametersValidator validator = new RequestParametersValidator(request);
        boolean result = validator.validateEmail("email");

        assertTrue(result);
        verify(request, never()).setAttribute(anyString(), anyObject());
    }

    @Test
    public void validateEmailReturnsFalseWhenEmailIsValid() {

        when(request.getParameter("email")).thenReturn("bob@example");

        RequestParametersValidator validator = new RequestParametersValidator(request);
        boolean result = validator.validateEmail("email");

        assertFalse(result);
        verify(request, times(1)).setAttribute("emailInvalidError", "Email is invalid");
    }
}

