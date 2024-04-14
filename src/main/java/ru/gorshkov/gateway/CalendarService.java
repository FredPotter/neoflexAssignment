package ru.gorshkov.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class CalendarService {
    @Value("${prod-calendar.token}")
    private String token;

    private final WebClient webClient;

    public CalendarService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://production-calendar.ru").build();
    }

    public JsonNode getCalendarDataJson(LocalDate start, LocalDate end) throws JsonProcessingException {
        String url = getPeriodUrl(start, end);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(
        webClient.get()
                .uri(uriBuilder -> uriBuilder.path(url).build())
                .retrieve()
                .bodyToMono(String.class)
                .block());
    }

    private String getPeriodUrl(LocalDate start, LocalDate end) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return "/get-period/" + token + "/ru/" + start.format(formatter) + "-" + end.format(formatter) + "/json";
    }
}
