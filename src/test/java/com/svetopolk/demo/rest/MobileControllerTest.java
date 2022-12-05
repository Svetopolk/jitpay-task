package com.svetopolk.demo.rest;

import com.svetopolk.demo.service.LocationService;
import com.svetopolk.demo.service.UserService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class MobileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Mock
    private LocationService locationService;

    @Test
    void postLocation() throws Exception {
        var request = MockMvcRequestBuilders
                .post("/v2/mobile/locations")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "userId": "2e3b11b0-07a4-4873-8de5-d2ae2eab26b2",
                        "createdOn": "2022-02-08T11:44:00.524",
                        "location": {"latitude": 52.25742342295784, "longitude": 10.540583401747602 }
                        }
                        """);
        mockMvc.perform(request)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {"status":"ok"}
                        """));
    }

    @Test
    void postLocationAbsentUserId() throws Exception {
        var request = MockMvcRequestBuilders
                .post("/v2/mobile/locations")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "userIdIsAbsent": "2e3b11b0-07a4-4873-8de5-d2ae2eab26b2",
                        "createdOn": "2022-02-08T11:44:00.524",
                        "location": {"latitude": 52.25742342295784, "longitude": 10.540583401747602 }
                        }
                        """);
        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    void postLocationNotJson() throws Exception {
        var request = MockMvcRequestBuilders
                .post("/v2/mobile/locations")
                .contentType(MediaType.APPLICATION_JSON)
                .content("not json");
        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }

}