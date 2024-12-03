package ru.nsu.tsyganov.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StudentTest {
    private Student recordBook;

    @BeforeEach
    void setUp() {
        recordBook = new Student("Alexander Tsyganov", 2, "23213", StudyForm.BUDGET);
    }

    @Test
    void testCalculateAverage_NoGrades() {
        assertEquals(0.0, recordBook.calculateAverage());
    }

    @Test
    void testCalculateAverage_OnlySatisfactory() {
        recordBook.addGrade(new Grade(GradeType.EXAM, GradeValue.SATISFACTORY));
        assertEquals(0.0, recordBook.calculateAverage());
    }

    @Test
    void testCalculateAverage_MixedGrades() {
        recordBook.addGrade(new Grade(GradeType.EXAM, GradeValue.EXCELLENT));
        recordBook.addGrade(new Grade(GradeType.EXAM, GradeValue.GOOD));
        recordBook.addGrade(new Grade(GradeType.EXAM, GradeValue.SATISFACTORY));

        // Средний балл считается только по "отлично" и "хорошо"
        assertEquals(4.5, recordBook.calculateAverage());
    }

    @Test
    void testCanTransferToBudget_NoSatisfactoryGrades() {
        recordBook.addGrade(new Grade(GradeType.EXAM, GradeValue.EXCELLENT));
        recordBook.addGrade(new Grade(GradeType.EXAM, GradeValue.GOOD));

        assertTrue(recordBook.canTransferToBudget());
    }

    @Test
    void testCanTransferToBudget_WithSatisfactoryGrades() {
        recordBook.addGrade(new Grade(GradeType.EXAM, GradeValue.SATISFACTORY));
        recordBook.addGrade(new Grade(GradeType.EXAM, GradeValue.EXCELLENT));

        assertFalse(recordBook.canTransferToBudget());
    }

    @Test
    void testCanReceiveRedDiploma_AllExcellent() {
        recordBook.addGrade(new Grade(GradeType.EXAM, GradeValue.EXCELLENT));
        recordBook.addGrade(new Grade(GradeType.DIFF_CREDIT, GradeValue.EXCELLENT));
        recordBook.setQualificationWorkGrade(GradeValue.EXCELLENT);

        assertTrue(recordBook.canGetRedDiploma());
    }

    @Test
    void testCanReceiveRedDiploma_WithSatisfactory() {
        recordBook.addGrade(new Grade(GradeType.EXAM, GradeValue.SATISFACTORY));
        recordBook.addGrade(new Grade(GradeType.DIFF_CREDIT, GradeValue.EXCELLENT));
        recordBook.setQualificationWorkGrade(GradeValue.EXCELLENT);

        assertFalse(recordBook.canGetRedDiploma());
    }

    @Test
    void testCanReceiveIncreasedScholarship_HighAverage() {
        recordBook.addGrade(new Grade(GradeType.EXAM, GradeValue.EXCELLENT));
        recordBook.addGrade(new Grade(GradeType.EXAM, GradeValue.GOOD));

        assertTrue(recordBook.canGetIncreasedScholarship());
    }

    @Test
    void testCanReceiveIncreasedScholarship_LowAverage() {
        recordBook.addGrade(new Grade(GradeType.EXAM, GradeValue.SATISFACTORY));

        assertFalse(recordBook.canGetIncreasedScholarship());
    }
}