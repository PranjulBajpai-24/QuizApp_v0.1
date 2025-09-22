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

    // 1. Create an HttpClient to send requests and a Gson object to parse JSON responses.
    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    /**
     * Fetches the list of quiz questions from the backend server.
     * @return A List of Question objects, or an empty list if an error occurs.
     */
    public List<Question> getQuizQuestions() {
        try {
            // 2. Build an HTTP GET request pointing to your backend's API endpoint.
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/questions"))
                    .header("Accept", "application/json")
                    .build();

            // 3. Send the request and receive the response as a String.
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 4. Use Gson to convert the JSON string into a usable List of Question objects.
            return gson.fromJson(response.body(), new TypeToken<List<Question>>(){}.getType());

        } catch (Exception e) {
            // If anything goes wrong (e.g., the server is not running), print an error and return an empty list.
            System.err.println("Error fetching questions from the server: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}