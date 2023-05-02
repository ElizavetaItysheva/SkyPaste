package com.example.myawesomepastebin.service;

import com.example.myawesomepastebin.dto.CreatePastaDTO;
import com.example.myawesomepastebin.dto.PastaDTO;
import com.example.myawesomepastebin.dto.UrlDTO;
import com.example.myawesomepastebin.exception.PastaNotFoundException;
import com.example.myawesomepastebin.model.Pasta;
import com.example.myawesomepastebin.model.enums.Status;
import com.example.myawesomepastebin.model.hash.Hash;
import com.example.myawesomepastebin.repository.PastaRepository;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class PastaService {
    private final PastaRepository pastaRepository;
    public PastaService( PastaRepository pastaRepository) {
        this.pastaRepository = pastaRepository;
    }
    public UrlDTO createPasta(CreatePastaDTO createPastaDTO){
        Pasta newPasta = createPastaDTO.toModel();

        newPasta.setPublishedDate(Instant.now());
        newPasta.setExpirationDate(newPasta.getPublishedDate()
                .plus(createPastaDTO.getExpirationTime().getTime(),
                        createPastaDTO.getExpirationTime().getChronoUnit()));

        newPasta.setHash(Hash.getHash());
        pastaRepository.save(newPasta);
        return UrlDTO.createUrl(newPasta);
    }
    public PastaDTO getPastaById(String hash){
        Pasta pasta = pastaRepository.findPastaByHashAndExpirationDateIsAfter(hash, Instant.now())
                .orElseThrow(PastaNotFoundException::new);
        return PastaDTO.fromModel(pasta);
    }
    public List<PastaDTO> getTenLastPastas(){
        List<PastaDTO> founded =  pastaRepository.findTop10ByStatusAndExpirationDateAfterOrderByPublishedDateDesc(Status.PUBLIC, Instant.now())
                .stream()
                .map(PastaDTO::fromModel)
                .collect(Collectors.toList());
        if(founded.isEmpty()){
            throw new PastaNotFoundException();
        }
        return founded;
    }
    public List<PastaDTO> getPastaByTitleOrPasta( String title, String pasta ){
        return pastaRepository.findAllByTitleContainsOrBodyContains(Status.PUBLIC, title, pasta)
                .stream()
                .map(PastaDTO::fromModel)
                .collect(Collectors.toList());
    }
}
