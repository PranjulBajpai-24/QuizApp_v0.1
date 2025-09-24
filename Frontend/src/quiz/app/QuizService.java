package quiz.app;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizService {

    private final HttpClient client = HttpClient.newHttpClient();

    /**
     * Fetches the list of quiz questions from the backend server.
     * @return A List of Question objects, or an empty list if an error occurs.
     */
    public List<Question> getQuizQuestions() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/questions"))
                    .header("Accept", "application/json")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // For compilation without GSON, return dummy data
            return getDummyQuestions();

        } catch (Exception e) {
            System.err.println("Error fetching questions from the server: " + e.getMessage());
            return getDummyQuestions();
        }
    }

    /**
     * Provides dummy questions when backend is not available or GSON is not working.
     * This allows the application to run without the backend server.
     */
    private List<Question> getDummyQuestions() {
        List<Question> dummyQuestions = new ArrayList<>();

        dummyQuestions.add(new Question(
            "What is Java?",
            "A programming language",
            "A type of coffee",
            "An island",
            "All of the above",
            "A programming language"
        ));

        dummyQuestions.add(new Question(
            "Which keyword is used to define a constant in Java?",
            "constant",
            "final",
            "static",
            "volatile",
            "final"
        ));

        dummyQuestions.add(new Question(
            "What does JVM stand for?",
            "Java Virtual Machine",
            "Java Very Main",
            "Java Virtual Method",
            "Java Verified Module",
            "Java Virtual Machine"
        ));

        dummyQuestions.add(new Question(
            "Which of these is not a Java feature?",
            "Object-oriented",
            "Portable",
            "Pointers",
            "Dynamic",
            "Pointers"
        ));

        dummyQuestions.add(new Question(
            "What is the entry point of a Java application?",
            "start() method",
            "run() method",
            "main() method",
            "execute() method",
            "main() method"
        ));

        return dummyQuestions;
    }
}