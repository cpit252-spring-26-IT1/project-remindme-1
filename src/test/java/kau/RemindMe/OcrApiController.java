package kau.RemindMe.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OcrApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("API Controller endpoint should accept uploaded file data streams successfully")
    void testProcessImageEndpoint() throws Exception {
        // Arrange: Build a mock dummy image payload stream
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "sample_card.png",
                "image/png",
                "dummy image content bytes".getBytes()
        );

        // Act & Assert: Simulate sending this file to your controller endpoint over the web layer
        mockMvc.perform(multipart("/api/ocr/process").file(mockFile))
                .andExpect(status().isOk()); // Verifies the route receives the file safely
    }
}