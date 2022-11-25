/*
    Classe faz mapeamento entre objetos da classe e banco de dados
 */

package com.desafio.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
//import java.util.Date;
import  java.sql.Date;

//import static javax.persistence.GenerationType.IDENTITY;
//import static javax.persistence.TemporalType.DATE;

@Entity  //nome da entidade da tabela a ser usada no código
@Table (name = " \"Enotas\" ")  //nome idêntico ao da tabela no banco de dados
@NamedQueries({
        @NamedQuery( //Mapeamento a partir da classe que configura conexão com o banco de dados
                name = "NotasEntityDAO.findAll",
                query = "SELECT a FROM NotasEntity a"
        )
})

public class NotasEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;  //tipo de dado idêntico ao do banco

    @Column (name = "titulo")  //nome idêntico ao da coluna no banco de dados
    private String titulo;

    @Column (name = "texto")
    private String texto;

    @Column (name = "data")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "pt-BR", timezone = "Brazil/East")
    private Date data;

    public NotasEntity() {

    }


    public NotasEntity(String texto) {
        this.texto = texto;
    }


    @JsonProperty
    public Long getId() {
        return id;
    }

    @JsonProperty
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty
    public String getTitulo() {
        return titulo;
    }

    @JsonProperty
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @JsonProperty
    public String getTexto() {
        return texto;
    }

    @JsonProperty
    public void setTexto(String texto) {
        this.texto = texto;
    }

    @JsonProperty
    public Date getData() {

        return data;
    }

    @JsonProperty
    public void setData(Date data) {

        this.data = data;
    }
}
