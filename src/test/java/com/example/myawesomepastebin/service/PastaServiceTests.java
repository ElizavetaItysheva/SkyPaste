package com.example.myawesomepastebin.service;

import com.example.myawesomepastebin.dto.CreatePastaDTO;
import com.example.myawesomepastebin.dto.PastaDTO;
import com.example.myawesomepastebin.dto.UrlDTO;
import com.example.myawesomepastebin.exception.PastaNotFoundException;
import com.example.myawesomepastebin.model.Pasta;
import com.example.myawesomepastebin.model.enums.ExpirationTime;
import com.example.myawesomepastebin.model.enums.Status;
import com.example.myawesomepastebin.model.hash.Hash;
import com.example.myawesomepastebin.repository.PastaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PastaServiceTests {

    @Mock
    private PastaRepository repositoryMock;
    @InjectMocks
    private PastaService pastaService;


    @Test
    public void createPasta_whenAllIsCorrect() {
        Pasta pasta = new Pasta();

        CreatePastaDTO createDTO = new CreatePastaDTO();
        createDTO.setExpirationTime(ExpirationTime.FOREVER);

        when(repositoryMock.save(ArgumentMatchers.any(Pasta.class))).thenReturn(pasta);

        UrlDTO url = pastaService.createPasta(createDTO);

        assertNotNull(url);
    }

    @Test
    public void getPastaById_whenAllIsCorrect() {
        Pasta pasta = new Pasta();
        String hash = Hash.getHash();
        String title = "Title";
        String body = "Body";
        pasta.setTitle(title);
        pasta.setPasta(body);
        pasta.setHash(hash);

        when(repositoryMock.findPastaByHashAndExpirationDateIsAfter(eq(hash),
                ArgumentMatchers.any(Instant.class))).thenReturn(Optional.of(pasta));

        PastaDTO pastaDTO = pastaService.getPastaById(hash);

        Assertions.assertEquals(title, pasta.getTitle());
        Assertions.assertEquals(body, pastaDTO.getPasta());
    }


    @Test
    public void getPastaById_whenThrowsException() {
        String hash = Hash.getHash();

        when(repositoryMock.findPastaByHashAndExpirationDateIsAfter(eq(hash),
                ArgumentMatchers.any(Instant.class))).thenReturn(Optional.empty());

        Assertions.assertThrows(PastaNotFoundException.class, () -> pastaService.getPastaById(hash));
    }


    @Test
    public void getTenLastPastas_whenAllIsCorrect() {
        List<Pasta> pastaList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Pasta pasta = new Pasta();
            if (i < 5) {
                pasta.setStatus(Status.PUBLIC);
            } else {
                pasta.setStatus(Status.UNLISTED);
            }
            pastaList.add(pasta);
        }

        when(repositoryMock.findTop10ByStatusAndExpirationDateAfterOrderByPublishedDateDesc(eq(Status.PUBLIC), ArgumentMatchers.any(Instant.class)))
                .thenReturn(pastaList.subList(0, 5));

        List<PastaDTO> pastaDTOList = pastaService.getTenLastPastas();

        Assertions.assertEquals(5, pastaDTOList.size());
    }

    @Test
    public void getLastPastas_whenAllIsCorrect() {
        String title = "Title";
        String body = "";

        Pasta pasta1 = new Pasta();
        pasta1.setTitle("Title1");
        pasta1.setPasta("Body1");
        pasta1.setStatus(Status.PUBLIC);
        pasta1.setHash(Hash.getHash());
        pasta1.setPublishedDate(Instant.now());
        pasta1.setExpirationDate(Instant.MAX);

        Pasta pasta2 = new Pasta();
        pasta2.setTitle("Title2");
        pasta2.setPasta("Body2");
        pasta2.setStatus(Status.PUBLIC);
        pasta2.setHash(Hash.getHash());
        pasta2.setPublishedDate(Instant.now());
        pasta2.setExpirationDate(Instant.MAX);

        List<Pasta> pastaList = new ArrayList<>();
        pastaList.add(pasta1);
        pastaList.add(pasta2);

        when(repositoryMock.findAllByTitleContainsOrBodyContains(Status.PUBLIC, title, body)).thenReturn(pastaList);

        List<PastaDTO> pastaDTOList = pastaService.getPastaByTitleOrPasta(title, body);

        Assertions.assertEquals(pastaList.size(), pastaDTOList.size());
        Assertions.assertEquals(pasta1.getTitle(), pastaDTOList.get(0).getTitle());
        Assertions.assertEquals(pasta1.getPasta(), pastaDTOList.get(0).getPasta());
        Assertions.assertEquals(pasta2.getTitle(), pastaDTOList.get(1).getTitle());
        Assertions.assertEquals(pasta2.getPasta(), pastaDTOList.get(1).getPasta());
    }
}
