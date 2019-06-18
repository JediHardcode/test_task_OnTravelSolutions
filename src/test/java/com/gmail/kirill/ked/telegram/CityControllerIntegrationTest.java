package com.gmail.kirill.ked.telegram;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.kirill.ked.telegram.service.model.attraction.AttractionDTO;
import com.gmail.kirill.ked.telegram.service.model.city.CityDTO;
import com.gmail.kirill.ked.telegram.service.model.city.UpdateCityDTO;
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

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class CityControllerIntegrationTest {
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
    public void shouldAddCity() throws Exception {
        CityDTO cityDTO = new CityDTO();
        cityDTO.setName("testName");
        mockMvc.perform(post("/api/v1/city")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(cityDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldAddCityWithAttractions() throws Exception {
        CityDTO cityDTO = new CityDTO();
        AttractionDTO attractionDTO = new AttractionDTO();
        attractionDTO.setName("testName");
        List<AttractionDTO> attractionDTOList = Collections.singletonList(attractionDTO);
        cityDTO.setName("testSecondName");
        cityDTO.setAttractions(attractionDTOList);
        mockMvc.perform(post("/api/v1/city")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(cityDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnBadRequestIfCityNotValid() throws Exception {
        CityDTO cityDTO = new CityDTO();
        mockMvc.perform(post("/api/v1/city")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(cityDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldUpdateCity() throws Exception {
        UpdateCityDTO updateCityDto = new UpdateCityDTO();
        updateCityDto.setId(1L);
        updateCityDto.setName("new name");
        mockMvc.perform(patch("/api/v1/city")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(updateCityDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnBadRequestIfUpdateCityDTONotValid() throws Exception {
        UpdateCityDTO updateCityDto = new UpdateCityDTO();
        updateCityDto.setName("new test name");
        mockMvc.perform(patch("/api/v1/city")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(updateCityDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldDeleteCity() throws Exception {
        mockMvc.perform(delete("/api/v1/city/2"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturn404IfCityDoesntExist() throws Exception {
        mockMvc.perform(delete("/api/v1/city/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldGetListOfCities() throws Exception {
        mockMvc.perform(get("/api/v1/cities"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnBadRequestIfAttractionsInCityAreNotValid() throws Exception {
        CityDTO cityDTO = new CityDTO();
        AttractionDTO attractionDTO = new AttractionDTO();
        List<AttractionDTO> attractionDTOList = Collections.singletonList(attractionDTO);
        cityDTO.setName("test");
        cityDTO.setAttractions(attractionDTOList);
        mockMvc.perform(post("/api/v1/city")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(cityDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldAddAttractionToExistCity() throws Exception {
        AttractionDTO attractionDTO = new AttractionDTO();
        attractionDTO.setName("tesk attractions");
        Long cityId = 1L;
        mockMvc.perform(post("/api/v1/city")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(attractionDTO))
                .requestAttr("cityId", cityId))
                .andExpect(status().isCreated());
    }
}