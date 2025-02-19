package model.entities;

import java.util.UUID;

public class Client {
    private UUID id;
    private String name;
    private String email;
    private String cpf;

    public Client(String name, String email, String cpf){
        super();
        this.name = name;
        this.email = email;
        this.cpf = cpf;
    }

    // Getters e Setters
    public UUID getId(){
        return id;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getCpf(){
        return cpf;
    }

    public void setCpf(String cpf){
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", cpf='" + cpf + '\'' +
                '}';
    }
}
