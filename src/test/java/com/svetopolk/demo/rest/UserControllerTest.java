package com.svetopolk.demo.rest;

import com.svetopolk.demo.dto.Location;
import com.svetopolk.demo.dto.LocationWithDate;
import com.svetopolk.demo.dto.UserLocationRangeResponse;
import com.svetopolk.demo.dto.UserLocationResponse;
import com.svetopolk.demo.service.LocationService;
import com.svetopolk.demo.service.UserService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private LocationService locationService;


    @Test
    @SneakyThrows
    void putUser() {
        var request = MockMvcRequestBuilders
                .put("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "userId": "2e3b11b0-07a4-4873-8de5-d2ae2eab26b2",
                        "email": "alex.schmid@gmail.com",
                        "firstName": "Alex",
                        "secondName": "Schmid"
                        }
                        """);
        String response = mockMvc.perform(request)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(response, is("{\"status\":\"ok\"}"));
    }

    @Test
    void getLocation() throws Exception {
        UserLocationResponse value = new UserLocationResponse(
                "2e3b11b0-07a4-4873-8de5-d2ae2eab26b2",
                "Alex",
                "Schmid",
                new Location(52.25742342295784, 10.540583401747602));

        Mockito.when(locationService.getLocation(any())).thenReturn(value);

        mockMvc.perform(get("/users/2e3b11b0-07a4-4873-8de5-d2ae2eab26b2/location"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {"userId":"2e3b11b0-07a4-4873-8de5-d2ae2eab26b2",
                        "firstName":"Alex",
                        "secondName":"Schmid",
                        "location":{"latitude":52.25742342295784,"longitude":10.540583401747602}}
                        """));
    }

    @Test
    void getLocations() throws Exception {
        var value = new UserLocationRangeResponse(
                "2e3b11b0-07a4-4873-8de5-d2ae2eab26b2",
                List.of(new LocationWithDate(
                        LocalDateTime.of(2000, 12, 30, 15, 59, 0),
                        new Location(52.25742342295784, 10.540583401747602)))
        );

        Mockito.when(locationService.getLocations(any(), any(), any())).thenReturn(value);

        mockMvc.perform(get("/users/2e3b11b0-07a4-4873-8de5-d2ae2eab26b2/locations")
                        .queryParam("from", "2022-02-08T11:40:00")
                        .queryParam("to", "2022-02-08T11:44:00")
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                         {"userId":"2e3b11b0-07a4-4873-8de5-d2ae2eab26b2",
                         "locationWithDate":[{"createdOn":"2000-12-30T15:59:00",
                         "location":{"latitude":52.25742342295784,"longitude":10.540583401747602}}
                         ]}
                        """));
    }

    @Test
    void getLocationsWithoutFromParam() throws Exception {
        mockMvc.perform(get("/users/2e3b11b0-07a4-4873-8de5-d2ae2eab26b2/locations"))
                .andExpect(status().isBadRequest());
    }
}