
import MillionaireGame.Project1.Question;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class QuestionTest {

    @Test
    public void testQuestionInitialization() {
        String questionText = "What is the capital of France?";
        String[] choices = {"Berlin", "Madrid", "Paris", "Rome"};
        String correctAnswer = "Paris";

        Question question = new Question(questionText, choices, correctAnswer);

        // Verify that the question text is set correctly
        assertThat(question.getQuestionText(), is(questionText));

        // Verify that the choices are set correctly
        assertThat(question.getChoices(), is(choices));

        // Verify that the correct answer is set correctly
        assertThat(question.getCorrectAnswer(), is(correctAnswer));
    }

    @Test
    public void testGetChoices() {
        String questionText = "Which planet is known as the Red Planet?";
        String[] choices = {"Earth", "Mars", "Jupiter", "Saturn"};
        String correctAnswer = "Mars";

        Question question = new Question(questionText, choices, correctAnswer);

        // Verify that the choices are returned correctly
        assertThat(question.getChoices(), is(choices));
    }

    @Test
    public void testCorrectAnswer() {
        String questionText = "What is 2 + 2?";
        String[] choices = {"3", "4", "5", "6"};
        String correctAnswer = "4";

        Question question = new Question(questionText, choices, correctAnswer);

        // Verify that the correct answer is returned correctly
        assertThat(question.getCorrectAnswer(), is(correctAnswer));
    }
}
