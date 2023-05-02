package com.example.myawesomepastebin.controller;

import com.example.myawesomepastebin.dto.CreatePastaDTO;
import com.example.myawesomepastebin.dto.PastaDTO;
import com.example.myawesomepastebin.model.Pasta;
import com.example.myawesomepastebin.model.enums.ExpirationTime;
import com.example.myawesomepastebin.model.enums.Status;
import com.example.myawesomepastebin.repository.PastaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PastaServiceTests {
   @Autowired
   PastaRepository pastaRepository;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @BeforeEach
    void setUp(){
        Pasta pasta = new Pasta();
        pasta.setTitle("test");
        pasta.setPasta("tset");
        pasta.setHash("shguh346");
        pasta.setStatus(Status.PUBLIC);
        pasta.setPublishedDate(Instant.now());
        pasta.setExpirationDate(Instant.now().plus(10L, ChronoUnit.MINUTES));

        pastaRepository.save(pasta);
    }
    @AfterEach
    void cleanDataBase() {
        pastaRepository.deleteAll();
    }
    @Test
    void createPasta_whenAllIsCorrect() throws Exception {
        JSONObject jsonPasta = new JSONObject();
        jsonPasta.put("title", "test");
        jsonPasta.put("pasta","test");
        jsonPasta.put("status", "PUBLIC");
        jsonPasta.put("expirationTime", "TEN_MIN");

        CreatePastaDTO dto = new CreatePastaDTO();
        dto.setTitle(jsonPasta.getString("title"));
        dto.setPasta(jsonPasta.getString("pasta"));
        dto.setStatus(Status.PUBLIC);
        dto.setExpirationTime(ExpirationTime.TEN_MIN);

        mockMvc.perform(post("/my-awesome-pastebin.tld")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPasta.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").hasJsonPath());
    }
    @Test
    void createPasta_whenNoBody() throws Exception {
        String emptyBody = "";
        mockMvc.perform(post("/my-awesome-pastebin.tld")
                .contentType(MediaType.APPLICATION_JSON).content(emptyBody).param("expiration date", "TEN_MIN")
                        .param("status","PUBLIC"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotHaveJsonPath());
    }
    @Test
    void getPastaByHash_whenCorrect() throws Exception {
 mockMvc.perform(get("/my-awesome-pastebin.tld/shguh346"))
         .andExpect(status().isOk());
    }
    @Test
    void getPastaByHash_whenNotFound() throws Exception{
        mockMvc.perform(get("/my-awesome-pastebin.tld/shguh"))
                .andExpect(status().isNotFound());
    }
    @Test
    void getPastaByHash_whenExpiredDate() throws Exception{
        Pasta pasta = new Pasta();
        pasta.setTitle("test");
        pasta.setPasta("test");
        pasta.setHash("shghfj6");
        pasta.setStatus(Status.PUBLIC);
        pasta.setPublishedDate(Instant.now());
        pasta.setExpirationDate(Instant.now().minus(10L, ChronoUnit.MINUTES));

        pastaRepository.save(pasta);

        mockMvc.perform(get("/my-awesome-pastebin.tld/shghfj6"))
                .andExpect(status().isNotFound());
    }
    @Test
    void  getTenLastPastas_whenCorrect()throws Exception{
        pastaRepository.deleteAll();

        Pasta pasta = new Pasta();
        pasta.setTitle("test");
        pasta.setPasta("test");
        pasta.setHash("shghfj6");
        pasta.setStatus(Status.PUBLIC);
        pasta.setPublishedDate(Instant.now());
        pasta.setExpirationDate(Instant.now().plus(10L, ChronoUnit.MINUTES));
        pastaRepository.save(pasta);

        Pasta pasta2 = new Pasta();
        pasta2.setTitle("test2");
        pasta2.setPasta("test2");
        pasta2.setHash("shg");
        pasta2.setStatus(Status.PUBLIC);
        pasta2.setPublishedDate(Instant.now());
        pasta2.setExpirationDate(Instant.now().plus(10L, ChronoUnit.MINUTES));
        pastaRepository.save(pasta2);

        List<PastaDTO> founded = new ArrayList<>();
        founded.add(PastaDTO.fromModel(pasta));
        founded.add(PastaDTO.fromModel(pasta2));

        mockMvc.perform(get("/my-awesome-pastebin.tld/last"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(founded)));
    }
    @Test
    void getTenLastPastas_whenNull() throws Exception{
        pastaRepository.deleteAll();
        mockMvc.perform(get("/my-awesome-pastebin.tld/last"))
                .andExpect(status().isNotFound());
    }
    @Test
    void getPastaByTitleOrPasta_whenCorrect() throws Exception{
        Optional<Pasta> pasta = pastaRepository.findById("shguh346");


        mockMvc.perform(get("/my-awesome-pastebin.tld").param("title", "test"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(PastaDTO.fromModel(pasta.get())))));
    }
}
