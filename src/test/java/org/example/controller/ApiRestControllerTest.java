package org.example.controller;

import org.example.TestUtil;
import org.example.model.Request;
import org.example.service.RequestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApiRestController.class)
public class ApiRestControllerTest {

    @MockBean
    private RequestService requestService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldCreateRequest() throws Exception {
        when(requestService.getRequestTypes()).thenReturn(List.of("Contract Adjustment", "Damage Case", "Complaint"));
        final String REQUEST_BODY = TestUtil.readFile("/org/example/dto/requests/Request_correct.json");

        this.mockMvc
                .perform(
                        post("/request")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(REQUEST_BODY))
                .andExpect(status().isOk());

        verify(requestService).saveRequest(any(Request.class));
    }

    @Test
    public void shouldRejectCreatingRequestWhenRequestTypeIsIncorrect() throws Exception {
        when(requestService.getRequestTypes()).thenReturn(List.of("Contract Adjustment", "Damage Case", "Complaint"));
        final String REQUEST_BODY = TestUtil.readFile("/org/example/dto/requests/RequestType_incorrect.json");

        this.mockMvc
                .perform(
                        post("/request")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(REQUEST_BODY))
                .andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Incorrect 'requestType'=requestType"));
    }

    @Test
    public void shouldRejectCreatingRequestWhenRequestIsIncorrect() throws Exception {
        when(requestService.getRequestTypes()).thenReturn(List.of("Contract Adjustment", "Damage Case", "Complaint"));
        final String REQUEST_BODY = TestUtil.readFile("/org/example/dto/requests/Request_incorrect.json");

        this.mockMvc
                .perform(
                        post("/request")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(REQUEST_BODY))
                .andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("message", containsString("'surname' must contain only letters")))
                .andExpect(MockMvcResultMatchers.jsonPath("message", containsString("'name' must contain only letters")));
    }
}