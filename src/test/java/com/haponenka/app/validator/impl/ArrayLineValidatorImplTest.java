package com.haponenka.app.validator.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.haponenka.app.validator.ArrayLineValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArrayLineValidatorImplTest {

  private ArrayLineValidator validator;

  @BeforeEach
  void setUp() {
    validator = new ArrayLineValidatorImpl();
  }

  @Test
  void isValidShouldReturnTrueWhenNumbersAreCommaSeparated() {
    // when
    boolean actual = validator.isValid("1, 2, 3, 4");

    // then
    assertTrue(actual);
  }

  @Test
  void isValidShouldReturnTrueWhenNumbersAreSemicolonSeparated() {
    // when
    boolean actual = validator.isValid("10; -20; 30");

    // then
    assertTrue(actual);
  }

  @Test
  void isValidShouldReturnTrueWhenNumbersAreSpaceSeparated() {
    // when
    boolean actual = validator.isValid("5  12  -9");

    // then
    assertTrue(actual);
  }

  @Test
  void isValidShouldReturnTrueWhenLineHasSingleNegativeNumber() {
    // when
    boolean actual = validator.isValid("-7");

    // then
    assertTrue(actual);
  }

  @Test
  void isValidShouldReturnFalseWhenLineHasInvalidCharacters() {
    // when
    boolean actual = validator.isValid("1, 2, x3, 6");

    // then
    assertFalse(actual);
  }

  @Test
  void isValidShouldReturnFalseWhenDashIsUsedAsDelimiter() {
    // when
    boolean actual = validator.isValid("1-2");

    // then
    assertFalse(actual);
  }

  @Test
  void isValidShouldReturnFalseWhenDashIsSeparatedBySpaces() {
    // when
    boolean actual = validator.isValid("1 - 2");

    // then
    assertFalse(actual);
  }

  @Test
  void isValidShouldReturnFalseWhenLineIsBlank() {
    // when
    boolean actual = validator.isValid("   ");

    // then
    assertFalse(actual);
  }

  @Test
  void isValidShouldReturnFalseWhenLineIsNull() {
    // when
    boolean actual = validator.isValid(null);

    // then
    assertFalse(actual);
  }
}
