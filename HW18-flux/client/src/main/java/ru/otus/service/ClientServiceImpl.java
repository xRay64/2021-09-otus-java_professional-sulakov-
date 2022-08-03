package ru.otus.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.otus.model.Client;
import ru.otus.model.ClientDTO;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ClientServiceImpl implements ClientService {
    private final WebClient webClient;

    public ClientServiceImpl() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8081")
                .build();
    }

    @Override
    public Mono<List<Client>> getAll() {
        return webClient.get().uri("/client/all")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Client>>() {
                })
                .doOnNext(clients -> System.out.println(clients.size()));
    }

    @Override
    public Mono<Optional<Client>> getById(Long id) {
        return webClient.get().uri(String.format("/client/%d", id))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Optional<Client>>() {
                });
    }

    @Override
    public void saveClient(ClientDTO client) {
        webClient.post().uri("/client/add")
                .body(BodyInserters.fromValue(client))
                .retrieve()
                .bodyToMono(Client.class)
                .block();
    }
}
