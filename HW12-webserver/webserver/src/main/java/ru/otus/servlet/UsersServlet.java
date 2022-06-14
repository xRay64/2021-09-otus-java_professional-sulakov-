package ru.otus.servlet;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.dao.ClientDao;
import ru.otus.exceptions.WebServerException;
import ru.otus.helpers.PasswordHelper;
import ru.otus.services.TemplateProcessor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UsersServlet extends HttpServlet {

    private static final String USERS_PAGE_TEMPLATE = "users.html";
    private static final String TEMPLATE_ATTR_CLIENTS = "clients";
    private static final MultipartConfigElement MULTI_PART_CONFIG = new MultipartConfigElement("./tmp");


    private final ClientDao clientDao;
    private final TemplateProcessor templateProcessor;

    public UsersServlet(TemplateProcessor templateProcessor, ClientDao clientDao) {
        this.templateProcessor = templateProcessor;
        this.clientDao = clientDao;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(TEMPLATE_ATTR_CLIENTS, clientDao.getAll());

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, paramsMap));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Client client = clientMapper(getFormParamsFromRequest(req));
        clientDao.saveClient(client);
        resp.sendRedirect("/users");
    }

    private Map<String, String> getFormParamsFromRequest(HttpServletRequest request) {
        Map<String, String> result = new HashMap<>();
        try {
            List<String> formParams = splitFormParams(request.getReader().readLine());
            formParams.forEach(s -> {
                String[] nameValuePair = s.split("=");
                result.put(nameValuePair[0], nameValuePair[1]);
            });
        } catch (IOException e) {
            throw new WebServerException(e);
        }
        return result;
    }

    private List<String> splitFormParams(String paramsLine) {
        String asciiFormatted;
        try {
            asciiFormatted = URLDecoder.decode(paramsLine, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new WebServerException(e);
        }

        if (!"".equals(asciiFormatted)) {
            return List.of(asciiFormatted.split("&"));
        } else {
            throw new WebServerException("Error read form params");
        }
    }

    private Client clientMapper(Map<String, String> params) {
        Client client = new Client();
        client.setName(params.get("name"));
        client.setAddress(new Address(null, params.get("address")));
        List<Phone> phones = Arrays.stream(params.get("phones").split(","))
                .map(s -> new Phone(null, s)).toList();
        client.setPhones(phones);
        client.setPasswordMD5(PasswordHelper.encodeStringToMD5(params.get("password")));
        return client;
    }
}
