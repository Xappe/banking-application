package resources;

import model.entities.Account;
import model.entities.Client;
import util.IDGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClientResource {
    private final List<Client> clients = new ArrayList<>();


    public Client createClient(String name, String email, String cpf) {
        Client newClient = new Client(name, email, cpf);
        newClient.setId(IDGenerator.generateID());
        clients.add(newClient);
        return newClient;
    }

    public void addClient(Client client) {
        clients.add(client);
        System.out.println("Client add successfully: " + client);
    }

    public Client getClientById(UUID id) {
        for (Client client : clients) {
            if (client.getId().toString().equals(id.toString())) {
                return client;
            }
        }
        System.out.println("Client not found with ID: " + id);
        return null;
    }

    public List<Client> getAllClients() {
        return clients;
    }

    public void removeClientById(UUID id) {
        clients.removeIf(client -> client.getId().toString().equals(id.toString()));
        System.out.println("Client successfully remove with ID: " + id);
    }
}
