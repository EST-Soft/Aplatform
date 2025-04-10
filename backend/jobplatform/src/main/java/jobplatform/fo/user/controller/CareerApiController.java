package jobplatform.fo.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/career-api")
public class CareerApiController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/school")
    public ResponseEntity<String> getSchoolData(
            @RequestParam String gubun,
            @RequestParam String searchSchulNm,
            @RequestParam(defaultValue = "1") int thisPage) {

        String apiUrl = "https://www.career.go.kr/cnet/openapi/getOpenApi" +
                "?apiKey=b5e83526a03f37b8349141b21fa2f6e7" +
                "&svcType=api" +
                "&svcCode=SCHOOL" +
                "&contentType=json" +
                "&perPage=5" +
                "&gubun=" + gubun +
                "&searchSchulNm=" + searchSchulNm +
                "&thisPage=" + thisPage;

        String response = restTemplate.getForObject(apiUrl, String.class);
        return ResponseEntity.ok(response);
    }
}
