package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.service.SparkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class SparkController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SparkService sparkService;

    @Value("${api.url}")
    private String apiUrl;

    @Value("${file.path}")
    private String filePath;

    @Operation(summary = "Process user data from DB using Spark")
    @GetMapping("/process-user-data-spark-v1")
    public ResponseEntity<String> processUserData() throws Exception {
        return ResponseEntity.ok(sparkService.processDataFromDB());
    }

    @Operation(summary = "Process user data from API using Spark")
    @GetMapping("/process-user-data-spark-v2")
    public ResponseEntity<String> processUserDataFromAPI(@RequestParam int numberOfSamples) {
        String url = apiUrl + numberOfSamples;
        String response = restTemplate.getForObject(url, String.class);
        return ResponseEntity.ok(sparkService.processDataFromApi(response));
    }

    @Operation(summary = "Process user data from file using Spark")
    @GetMapping("/process-user-data-spark-v3")
    public ResponseEntity<String> processUserDataFromFile(@RequestParam String fileName) {
//        String url = apiUrl + numberOfSamples;
//        String response = restTemplate.getForObject(url, String.class);
//        String result;
//        try {
//            Path path = Paths.get(filePath, fileName);
//            Files.write(path, response.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
//            result = sparkService.processDataFromFile(path.toString());
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error writing to file: " + e.getMessage());
//        }
        return ResponseEntity.ok(sparkService.processDataFromFile(filePath + fileName));
    }
}
