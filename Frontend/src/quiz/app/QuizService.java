package quiz.app;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

public class QuizService {

    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

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
            String jsonBody = response.body();

            // CORRECTED: Parse the real JSON from the server instead of returning dummy data.
            return gson.fromJson(jsonBody, new TypeToken<List<Question>>(){}.getType());

        } catch (Exception e) {
            System.err.println("Error fetching questions from the server: " + e.getMessage());
            // Return an empty list if the backend is down.
            return Collections.emptyList();
        }
    }

    /**
     * Sends the user's name, roll number, and score to the backend to be saved.
     */
    public void saveScore(String userName, String rollNo, int score) {
        try {
            // Create a simple object to hold the data
            QuizResult result = new QuizResult(userName, rollNo, score);
            // Convert the object to a JSON string
            String jsonPayload = gson.toJson(result);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/scores"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            // Send the request asynchronously (doesn't block the UI)
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> System.out.println("Score saved response: " + response.statusCode()));

        } catch (Exception e) {
            System.err.println("Error saving score: " + e.getMessage());
        }
    }
}