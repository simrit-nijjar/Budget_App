package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {

    BudgetCategory bc1;
    BudgetCategory bc2;
    BudgetCategory bc3;
    BudgetCategory bc4;

    @BeforeEach
    public void setUp() {
        bc1 = new BudgetCategory("Category 1");

        bc2 = new BudgetCategory("Category 2");
        bc2.setHasGoalTrue(100);

        bc3 = new BudgetCategory("Category 3");
        bc3.setHasGoalTrue(100);
        bc3.addToCurrentAmount(100);

        bc4 = new BudgetCategory("Category 4");
        bc4.setHasGoalTrue(100);
        bc4.addToCurrentAmount(150);

    }

    @Test
    public void testConstructor() {
        assertFalse(bc1.hasGoal());
        assertEquals(bc1.getGoal(), 0);
    }

    @Test
    public void testEditName() {
        bc1.setName("Category 1 Edit");
        assertEquals(bc1.getName(), "Category 1 Edit");
    }

    @Test
    public void testEditGoal() {
        bc2.setGoal(25);
        assertEquals(bc2.getGoal(), 25);
    }

    @Test
    public void testSetHasGoalTrueNotTrue() {
        bc1.setHasGoalTrue(100);
        assertTrue(bc1.hasGoal());
        assertEquals(bc1.getGoal(), 100);
    }

    @Test
    public void testSetHasGoalTrueAlreadyTrue() {
        bc1.setHasGoalTrue(100);
        assertTrue(bc1.hasGoal());
        assertEquals(bc1.getGoal(), 100);
        bc1.setHasGoalTrue(50);
        assertTrue(bc1.hasGoal());
        assertEquals(bc1.getGoal(), 100);
    }

    @Test
    public void testSetHasGoalFalseNotFalse() {
        bc2.setHasGoalFalse();
        assertFalse(bc2.hasGoal());
        assertEquals(bc2.getGoal(), 0);
    }

    @Test
    public void testSetHasGoalFalseAlreadyFalse() {
        bc2.setHasGoalFalse();
        assertFalse(bc2.hasGoal());
        assertEquals(bc2.getGoal(), 0);
        bc2.setHasGoalFalse();
        assertFalse(bc2.hasGoal());
        assertEquals(bc2.getGoal(), 0);
    }

    @Test
    public void testAddToCurrentAmountNotOverGoal() {
        bc1.addToCurrentAmount(50);
        bc2.addToCurrentAmount(100);

        assertEquals(bc1.getCurrentAmount(), 50);
        assertEquals(bc2.getCurrentAmount(), 100);

        assertFalse(bc1.isOverGoal());
        assertFalse(bc2.isOverGoal());
    }

    @Test
    public void testAddToCurrentAmountOverGoal() {
        bc2.addToCurrentAmount(150);
        assertEquals(bc2.getCurrentAmount(), 150);
        assertTrue(bc2.isOverGoal());
    }

    @Test
    public void testSubtractFromCurrentAmountNotOverGoal() {
        bc3.subtractFromCurrentAmount(50);

        assertEquals(bc3.getCurrentAmount(), 50);
        assertFalse(bc3.isOverGoal());
    }

    @Test
    public void testSubtractFromCurrentAmountOverGoalToUnderGoal() {
        bc4.subtractFromCurrentAmount(50);

        assertEquals(bc4.getCurrentAmount(), 100);
        assertFalse(bc4.isOverGoal());
    }

    @Test
    public void testSubtractFromCurrentAmountOverGoalToOverGoal() {
        bc4.subtractFromCurrentAmount(49);

        assertEquals(bc4.getCurrentAmount(), 101);
        assertTrue(bc4.isOverGoal());
    }

    @Test
    public void testPrintDetailsHasGoal() {
        assertEquals(bc2.printDetails(), "Category 2 - $100.00");
    }

    @Test
    public void testPrintDetailsHasNoGoal() {
        assertEquals(bc1.printDetails(), "Category 1 - No goal added");
    }

    @Test
    public void testGetIsOverGoalTrue() {
        assertTrue(bc4.isOverGoal());
    }

    @Test
    public void testGetIsOverGoalFalse() {
        assertFalse(bc2.isOverGoal());
    }
}
