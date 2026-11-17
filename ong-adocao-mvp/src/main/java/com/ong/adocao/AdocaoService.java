package com.ong.adocao;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;

@Service
public class AdocaoService {

    private final List<Animal> animais = List.of(
            new Animal(
                    1L,
                    "Thor",
                    "cao",
                    "Macho, Porte Médio, 2 anos",
                    "https://images.unsplash.com/photo-1543466835-00a7907e9de1"),
            new Animal(
                    2L,
                    "Mel",
                    "gato",
                    "Fêmea, Porte Pequeno, 1 ano",
                    "https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba"));
    private final ConcurrentMap<Long, Candidatura> candidaturas = new ConcurrentHashMap<>();
    private final AtomicLong proximoProtocolo = new AtomicLong(1000);

    public List<Animal> listarAnimais(String especie) {
        if (especie == null) {
            return animais;
        }
        return animais.stream()
                .filter(animal -> animal.especie().equals(especie))
                .toList();
    }

    public Optional<Animal> buscarAnimal(Long id) {
        return animais.stream()
                .filter(animal -> animal.id().equals(id))
                .findFirst();
    }

    public Candidatura criarCandidatura(Animal animal, CandidaturaForm formulario) {
        long protocolo = proximoProtocolo.incrementAndGet();
        Candidatura candidatura = new Candidatura(
                protocolo,
                animal,
                formulario.getNome().trim(),
                formulario.getTelefone().trim(),
                formulario.getBairro().trim(),
                formulario.getMoradia(),
                formulario.getTelas(),
                formulario.getOutrosAnimais());
        candidaturas.put(protocolo, candidatura);
        return candidatura;
    }

    public Optional<Candidatura> buscarCandidatura(Long protocolo) {
        return Optional.ofNullable(candidaturas.get(protocolo));
    }
}
