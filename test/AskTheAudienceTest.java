
import MillionaireGame.Project1.AskTheAudience;
import MillionaireGame.Project1.Question;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AskTheAudienceTest {

    private static AskTheAudience askTheAudience;
    private static Question question;

    @BeforeClass
    public static void setUpBeforeClass() {
        // This method is called once before any of the tests in this class are run.
        String questionText = "What is the capital of France?";
        String[] choices = {"Berlin", "Madrid", "Paris", "Rome"};
        String correctAnswer = "Paris";
        question = new Question(questionText, choices, correctAnswer);
        askTheAudience = new AskTheAudience();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        // This method is called once after all the tests in this class have run.
        askTheAudience = null;
        question = null;
    }

    @Test
    public void testGetAudienceResponse() {
        // Use the AskTheAudience lifeline
        String response = askTheAudience.getAudienceResponse(question);
        System.out.println("Audience Response: " + response);
        // Check that the response is not null or empty
        assertThat(response != null && !response.isEmpty(), is(true));

        // Check that the response includes the choices
        assertThat(response.contains("Berlin"), is(true));
        assertThat(response.contains("Madrid"), is(true));
        assertThat(response.contains("Paris"), is(true));
        assertThat(response.contains("Rome"), is(true));
    }

    @Test(expected = IllegalStateException.class)
    public void testUseAskTheAudienceTwice() {
        // Use the AskTheAudience lifeline for the first time
        askTheAudience.use(question);

        // Attempt to use AskTheAudience again, which should throw an exception
        askTheAudience.use(question); // This should throw an IllegalStateException
    }

    @Test
    public void testGetAudienceResponseWithNullQuestion() {
        // Pass a null question to getAudienceResponse
        String response = askTheAudience.getAudienceResponse(null);

        // Check that the response indicates an error
        assertThat(response, is("Error: Question cannot be null."));
    }
}