/*
    Classe disponibiliza os recursos da aplicação
 */

package com.desafio.resources;

import com.desafio.dao.NotasEntityDAO;
import com.desafio.entity.NotasEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.hibernate.UnitOfWork;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("/enotas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)  //conteúdo que a classe produzirá
/*
Se um dos métodos precisar produzir outro tipo de conteúdo basta sobrescrever o (@Produces) acima
do método desejado, os demais métodos atenderão ao definido no padrão acima do nome da classe
neste caso @Produces(MediaType.APPLICATION_JSON)
 */

public class ENotasResource {


    private final NotasEntityDAO notasEntityDAO;

    public ENotasResource(NotasEntityDAO notasEntityDAO) {
        this.notasEntityDAO = notasEntityDAO;
    }

    //método para consultar usando requisição http tipo get (retorna o registro total)
    @GET
    @Path("/consultar")
    @UnitOfWork //para método poder acessar banco de dados
    public List<NotasEntity> enotasConsultar(){
        //return "{\"get\": \" " + "Ping received at " + new Date() + "\"}";
        return notasEntityDAO.findAll();
    }


    //método para publicar usando requisição http tipo post
    @POST
    @Path("/publicar")
    @UnitOfWork
    @JsonProperty
    public NotasEntity enotasPublicar (@Valid NotasEntity notasEntity) {


        //return "{\"id\": \" " + "Sucesso a nota foi inserida no banco de dados! Está no id: " + "" + notasEntityDAO.save(notasEntity).getId() + " \"}";
        return notasEntityDAO.save(notasEntity);
    }

    //método para deletar usando requisição http tipo delete
    @DELETE
    @Path("/deletar/{id}")
    @UnitOfWork
    public Long enotasDeletar(@PathParam("id") Long id) {

        notasEntityDAO.deletar(id);

        return id;
    }

    //método para atualizar usando requisição http tipo put
    @PUT
    @Path("/editar/{id}")
    @UnitOfWork
    @JsonProperty
    public NotasEntity enotasEditar(@PathParam("id") Long id, @Valid NotasEntity notasEntity) {

        return notasEntityDAO.atualizar(id,notasEntity );

    }



}
