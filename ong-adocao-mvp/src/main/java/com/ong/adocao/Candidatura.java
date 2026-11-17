package com.ong.adocao;

public record Candidatura(
        Long protocolo,
        Animal animal,
        String nome,
        String telefone,
        String bairro,
        String moradia,
        String telas,
        String outrosAnimais) {
}
