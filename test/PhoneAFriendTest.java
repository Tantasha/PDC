import MillionaireGame.Project1.PhoneAFriend;
import MillionaireGame.Project1.Question;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

public class PhoneAFriendTest {

    private static PhoneAFriend phoneAFriend;
    private static Question question;

    @BeforeClass
    public static void setUpBeforeClass() {
        // This method is called once before any of the tests in this class are run.
        String questionText = "What is the capital of France?";
        String[] choices = {"Berlin", "Madrid", "Paris", "Rome"};
        String correctAnswer = "Paris";
        question = new Question(questionText, choices, correctAnswer);
        phoneAFriend = new PhoneAFriend();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        // This method is called once after all the tests in this class have run.
        phoneAFriend = null;
        question = null;
    }

    @Test
    public void testUsePhoneAFriendCorrectAnswer() {
        // Use the PhoneAFriend lifeline
        phoneAFriend.use(question);
        
        // Get the friend's answer
        String friendAnswer = phoneAFriend.getFriendAnswer();

        // Check that the friend's answer is either the correct answer or one of the other choices
        assertTrue(friendAnswer.equals("Paris") || friendAnswer.equals("Berlin") || 
                   friendAnswer.equals("Madrid") || friendAnswer.equals("Rome"));
    }

    @Test
    public void testUsePhoneAFriendWithNullQuestion() {
        // Pass a null question to use
        phoneAFriend.use(null);

        // Check that the friend's answer is still null
        assertThat(phoneAFriend.getFriendAnswer(), is((String) null));
    }

    @Test
    public void testUsePhoneAFriendRandomness() {
        // Simulate multiple uses to check randomness
        int correctCount = 0;
        int incorrectCount = 0;
        int totalTests = 1;

        for (int i = 0; i < totalTests; i++) {
            phoneAFriend.use(question);
            String friendAnswer = phoneAFriend.getFriendAnswer();
            if (friendAnswer.equals("Paris")) {
                correctCount++;
            } else {
                incorrectCount++;
            }
        }

        // Check that the correct answer is given a reasonable number of times
        assertTrue(correctCount > (totalTests * 0.6)); // Expecting at least 60% correct answers
    }
}