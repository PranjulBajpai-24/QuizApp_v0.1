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

    public List<Question> getQuizQuestions() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/questions"))
                    .header("Accept", "application/json")
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return gson.fromJson(response.body(), new TypeToken<List<Question>>(){}.getType());
        } catch (Exception e) {
            System.err.println("Error fetching questions: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public void saveScore(String userName, String rollNo, int score) {
        try {
            QuizResult result = new QuizResult(userName, rollNo, score);
            String jsonPayload = gson.toJson(result);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/scores"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> System.out.println("Final score saved, status: " + response.statusCode()));
        } catch (Exception e) {
            System.err.println("Error saving score: " + e.getMessage());
        }
    }

    public void submitAnswer(UserResponse response) {
        try {
            String jsonPayload = gson.toJson(response);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/responses"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(res -> System.out.println("Answer submitted, status: " + res.statusCode()));
        } catch (Exception e) {
            System.err.println("Error submitting answer: " + e.getMessage());
        }
    }
}