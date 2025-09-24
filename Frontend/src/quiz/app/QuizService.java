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

            return gson.fromJson(response.body(), new TypeToken<List<Question>>(){}.getType());

        } catch (Exception e) {
            System.err.println("Error fetching questions from the server: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}