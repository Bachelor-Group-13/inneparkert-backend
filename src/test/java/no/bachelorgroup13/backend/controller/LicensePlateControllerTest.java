package no.bachelorgroup13.backend.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import no.bachelorgroup13.backend.features.licenseplate.controller.LicensePlateController;
import no.bachelorgroup13.backend.features.licenseplate.service.LicensePlateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(LicensePlateController.class)
class LicensePlateControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockitoBean private LicensePlateService computerVisionService;

    @Test
    void testRecognizePlate_success() throws Exception {
        // when(computerVisionService.getLicensePlates(any()))
        // .thenReturn(Arrays.asList("AB12345", "SD34567"));

        MockMultipartFile mockFile =
                new MockMultipartFile(
                        "image",
                        "test.jpg",
                        MediaType.IMAGE_JPEG_VALUE,
                        "fake-image-content".getBytes());

        mockMvc.perform(multipart("/license-plate").file(mockFile))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.license_plates[0]").value("AB12345"))
                .andExpect(jsonPath("$.license_plates[1]").value("SD34567"));

        verify(computerVisionService, times(1)).getLicensePlates(any());
    }

    @Test
    void testRecognizePlate_error() throws Exception {
        when(computerVisionService.getLicensePlates(any()))
                .thenThrow(new RuntimeException("Test error"));

        MockMultipartFile mockFile =
                new MockMultipartFile(
                        "image",
                        "test.jpg",
                        MediaType.IMAGE_JPEG_VALUE,
                        "fake-image-content".getBytes());

        mockMvc.perform(multipart("/license-plate").file(mockFile))
                .andExpect(status().is5xxServerError());

        verify(computerVisionService, times(1)).getLicensePlates(any());
    }
}
