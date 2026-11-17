package com.ong.adocao;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void filtraAnimaisPorEspecie() throws Exception {
        mockMvc.perform(get("/vitrine").param("especie", "gato"))
                .andExpect(status().isOk())
                .andExpect(view().name("vitrine"))
                .andExpect(content().string(containsString("Mel")))
                .andExpect(content().string(not(containsString("Thor"))));
    }

    @Test
    void enviaCandidaturaEExibeConfirmacao() throws Exception {
        MvcResult envio = mockMvc.perform(post("/adotar/1/candidaturas")
                        .param("nome", "Ana Silva")
                        .param("telefone", "(83) 99999-8888")
                        .param("bairro", "Tambaú, João Pessoa")
                        .param("moradia", "casa")
                        .param("telas", "sim")
                        .param("outrosAnimais", "nao"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        mockMvc.perform(get(envio.getResponse().getRedirectedUrl()))
                .andExpect(status().isOk())
                .andExpect(view().name("confirmacao"))
                .andExpect(content().string(containsString("Thor")))
                .andExpect(content().string(containsString("Candidatura Enviada com Sucesso")));
    }

    @Test
    void devolveFormularioComErrosQuandoOsDadosSaoInvalidos() throws Exception {
        mockMvc.perform(post("/adotar/1/candidaturas")
                        .param("nome", "")
                        .param("telefone", "telefone-invalido"))
                .andExpect(status().isOk())
                .andExpect(view().name("formulario"))
                .andExpect(content().string(containsString("Informe seu nome completo.")))
                .andExpect(content().string(containsString("Informe um telefone válido.")));
    }

    @Test
    void retornaNaoEncontradoParaAnimalInexistente() throws Exception {
        mockMvc.perform(get("/adotar/999"))
                .andExpect(status().isNotFound());
    }
}
