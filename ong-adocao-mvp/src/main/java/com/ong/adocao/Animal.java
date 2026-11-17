package com.ong.adocao;

public record Animal(
        Long id,
        String nome,
        String especie,
        String descricao,
        String imagemUrl) {
}
