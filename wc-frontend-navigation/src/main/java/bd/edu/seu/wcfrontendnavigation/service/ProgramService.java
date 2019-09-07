package bd.edu.seu.wcfrontendnavigation.service;

import bd.edu.seu.wcfrontendnavigation.model.Program;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProgramService {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${programUrl}/programs")
    private String programUrl;

    public Program insertProgram(Program program) {
        HttpEntity<Program> request = new HttpEntity<>(program);
        ResponseEntity<Program> response = restTemplate
                .exchange(programUrl, HttpMethod.POST, request, Program.class);

        return response.getBody();
    }

    public Program getProgram(String id) {
        ResponseEntity<Program> response = restTemplate.exchange(
                programUrl + "/" + id,
                HttpMethod.GET,
                null,
                Program.class);
        Program program = response.getBody();
        return program;
    }

    public Program updateProgram(String id, Program program) {
        String resourceUrl =
                programUrl + "/" + id;
        HttpEntity<Program> requestUpdate = new HttpEntity<>(program);
        restTemplate.exchange(resourceUrl, HttpMethod.PUT, requestUpdate, Program.class);
        return requestUpdate.getBody();
    }

    public List<Program> findAll() {
        ResponseEntity<List<Program>> response = restTemplate.exchange(
                programUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Program>>() {
                });
        List<Program> programList = response.getBody();
        return programList;
    }
}
