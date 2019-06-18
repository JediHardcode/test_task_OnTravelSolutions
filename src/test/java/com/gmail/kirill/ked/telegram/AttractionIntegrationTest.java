package com.gmail.kirill.ked.telegram;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.kirill.ked.telegram.service.model.attraction.UpdateAttractionDTO;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.telegram.telegrambots.ApiContextInitializer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class AttractionIntegrationTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeClass
    public static void init() {
        ApiContextInitializer.init();
    }

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    public void shouldDeletedAttraction() throws Exception {
        mockMvc.perform(delete("/api/v1/attraction/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturn404IfAttractionDoesntExist() throws Exception {
        mockMvc.perform(delete("/api/v1/attraction/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldUpdateAttraction() throws Exception {
        UpdateAttractionDTO updateAttractionDTO = new UpdateAttractionDTO();
        updateAttractionDTO.setId(2L);
        updateAttractionDTO.setName("new name");
        mockMvc.perform(patch("/api/v1/attraction")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(updateAttractionDTO)))
                .andExpect(status().isOk());
    }
}