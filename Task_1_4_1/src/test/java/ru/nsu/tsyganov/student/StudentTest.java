package ru.nsu.tsyganov.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StudentTest {
    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student("John Doe", "23213", StudyForm.PAID);
    }

    @Test
    void testCalculategpa_NoGrades() {
        assertEquals(0.0, student.calculateGPA());
    }

    @Test
    void testCalculategpa_ValidGrades() {
        student.addGrade(Grade.EXCELLENT);
        student.addGrade(Grade.GOOD);
        student.addGrade(Grade.SATISFACTORY);
        student.addGrade(Grade.UNSATISFACTORY);

        assertEquals(3.5, student.calculateGPA());
    }

    @Test
    void testCanTransferToBudget_NotEnoughGrades() {
        assertFalse(student.canTransferToBudget());
    }

    @Test
    void testCanTransferToBudget_InvalidGrades() {
        student.addGrade(Grade.EXCELLENT);
        student.addGrade(Grade.GOOD);
        student.addGrade(Grade.SATISFACTORY);
        student.addGrade(Grade.UNSATISFACTORY);

        assertFalse(student.canTransferToBudget());
    }

    @Test
    void testCanTransferToBudget_ValidTransfer() {
        student.addGrade(Grade.EXCELLENT);
        student.addGrade(Grade.EXCELLENT);
        student.addGrade(Grade.EXCELLENT);
        student.addGrade(Grade.EXCELLENT);

        assertTrue(student.canTransferToBudget());
    }

    @Test
    void testCanGetRedDiploma_NoGrades() {
        assertFalse(student.canGetRedDiploma());
    }

    @Test
    void testCanGetRedDiploma_WithSatisfactory() {
        student.addGrade(Grade.EXCELLENT);
        student.addGrade(Grade.SATISFACTORY);

        assertFalse(student.canGetRedDiploma());
    }

    @Test
    void testCanGetRedDiploma_ValidConditions() {
        student.addGrade(Grade.EXCELLENT);
        student.addGrade(Grade.EXCELLENT);
        student.addGrade(Grade.EXCELLENT);
        student.setThesisGradeExcellent(true);

        assertTrue(student.canGetRedDiploma());
    }

    @Test
    void testCanGetIncreasedScholarship_LowGpa() {
        student.addGrade(Grade.GOOD);
        student.addGrade(Grade.GOOD);

        assertFalse(student.canGetIncreasedScholarship());
    }

    @Test
    void testCanGetIncreasedScholarship_HighGpa() {
        student.addGrade(Grade.EXCELLENT);
        student.addGrade(Grade.EXCELLENT);

        assertTrue(student.canGetIncreasedScholarship());
    }
}