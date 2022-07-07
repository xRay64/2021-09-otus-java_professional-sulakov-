package ru.otus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.ClientService;

import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/")
    public String getClients(Model model) {
        model.addAttribute("clients", clientService.getAll());

        return "index";
    }

    @PostMapping("/client/add")
    public String addClient(
            @RequestParam("name") String name,
            @RequestParam("address") String address,
            @RequestParam("phones") String phones
    ) {
        Client newClient = new Client(
                name,
                new Address(address),
                Arrays.stream(phones.split(",")).map(Phone::new).collect(Collectors.toSet())
        );
        clientService.saveClient(newClient);

        return "redirect:/";
    }
}
