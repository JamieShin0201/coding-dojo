package me.whiteship.refactoring._06_mutable_data._23_change_reference_to_value;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TelephoneNumberTest {

    @Test
    void testEquals() {
        TelephoneNumber telephoneNumber1 = new TelephoneNumber("123", "456");
        TelephoneNumber telephoneNumber2 = new TelephoneNumber("123", "456");

        assertEquals(telephoneNumber1, telephoneNumber2);
    }
}
