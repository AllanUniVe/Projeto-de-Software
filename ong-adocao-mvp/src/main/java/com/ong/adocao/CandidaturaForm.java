package com.ong.adocao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CandidaturaForm {

    @NotBlank(message = "Informe seu nome completo.")
    @Size(max = 120, message = "O nome deve ter no máximo 120 caracteres.")
    private String nome;

    @NotBlank(message = "Informe seu telefone.")
    @Pattern(regexp = "^[0-9+() .-]{8,30}$", message = "Informe um telefone válido.")
    private String telefone;

    @NotBlank(message = "Informe seu bairro e cidade.")
    @Size(max = 160, message = "O bairro e a cidade devem ter no máximo 160 caracteres.")
    private String bairro;

    @NotBlank(message = "Selecione o tipo de moradia.")
    @Pattern(regexp = "^(casa|apartamento)$", message = "Selecione uma opção válida.")
    private String moradia;

    @NotBlank(message = "Informe se possui telas ou muro.")
    @Pattern(regexp = "^(sim|nao)$", message = "Selecione uma opção válida.")
    private String telas;

    @NotBlank(message = "Informe se possui outros animais.")
    @Pattern(regexp = "^(sim|nao)$", message = "Selecione uma opção válida.")
    private String outrosAnimais;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getMoradia() {
        return moradia;
    }

    public void setMoradia(String moradia) {
        this.moradia = moradia;
    }

    public String getTelas() {
        return telas;
    }

    public void setTelas(String telas) {
        this.telas = telas;
    }

    public String getOutrosAnimais() {
        return outrosAnimais;
    }

    public void setOutrosAnimais(String outrosAnimais) {
        this.outrosAnimais = outrosAnimais;
    }
}
