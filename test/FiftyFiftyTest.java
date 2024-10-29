import MillionaireGame.Project1.FiftyFifty;
import MillionaireGame.Project1.Question;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class FiftyFiftyTest {

    private static FiftyFifty fiftyFifty;
    private static Question question;

    @BeforeClass
    public static void setUpBeforeClass() {
        // This method is called once before any of the tests in this class are run.
        // Set up a sample question and a FiftyFifty instance
        String questionText = "What is the capital of France?";
        String[] choices = {"Berlin", "Madrid", "Paris", "Rome"};
        String correctAnswer = "Paris";
        question = new Question(questionText, choices, correctAnswer);
        fiftyFifty = new FiftyFifty();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        // This method is called once after all the tests in this class have run.
        // Clean up resources if necessary (not strictly needed here, but included for completeness)
        fiftyFifty = null;
        question = null;
    }

    @Test
    public void testUseFiftyFifty() {
        // Use the FiftyFifty lifeline
        fiftyFifty.use(question);

        // Check that FiftyFifty has been used
        assertThat(fiftyFifty.isUsed(), is(true));

        // Get remaining choices
        List<Integer> remainingChoices = fiftyFifty.getRemainingChoices();

        // Check that there are exactly 2 remaining choices
        assertThat(remainingChoices.size(), is(2));

        // Check that one of the remaining choices is the correct answer
        assertThat(remainingChoices.contains(2), is(true)); // Assuming "Paris" is at index 2
    }

  
}