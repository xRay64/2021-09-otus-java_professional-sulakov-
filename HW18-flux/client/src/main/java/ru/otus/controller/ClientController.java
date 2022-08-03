package ru.otus.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.otus.model.Client;
import ru.otus.model.ClientDTO;
import ru.otus.service.ClientService;

import java.util.List;

@RestController
@Slf4j
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(value = "/client/all", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<List<Client>> getClients(Model model) {
        return clientService.getAll();
    }

    @PostMapping("/client/add")
    public void addClient(
            @RequestBody ClientDTO client
    ) {
        log.info("Get POST into /client/add with params: {}", client.toString());
        clientService.saveClient(client);
    }
}
