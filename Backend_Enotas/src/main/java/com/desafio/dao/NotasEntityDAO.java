/*
    Classe viabiliza troca de informações com SGBD
 */

package com.desafio.dao;

import com.desafio.entity.NotasEntity;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;


public class NotasEntityDAO extends AbstractDAO<NotasEntity> {

    //construtor da classe atual e faz chamada do contrutor da superclasse
    public NotasEntityDAO (SessionFactory sessionFactory){
        super(sessionFactory);
    }

    @Override
    public NotasEntity persist(NotasEntity notasEntity){

        return super.persist(notasEntity);
    }

    //método para buscar registro no banco de dados através do id
    public Optional<NotasEntity> findById(Long id) {
        return Optional.ofNullable(get(id));
    }

    // método para buscar tudo o que está registrado no banco de dados
    public List<NotasEntity> findAll() {
        return list(namedQuery("NotasEntityDAO.findAll"));
    }

    //método para salvar no banco de dados
    public NotasEntity save(NotasEntity entity){

        //método prepara entidade para posterior inserção no banco de dados
        return persist(entity);
    }

    //método para deletar no banco de dados
    public void deletar(Long id){

        Optional<NotasEntity> notasEntityOptional = findById(id);
        notasEntityOptional.ifPresent(nota -> currentSession().delete(nota));

    }

    //método para atualizar (editar) no banco de dados
    public NotasEntity atualizar(Long id, NotasEntity notasEntity){

        NotasEntity notasEntityAtual = this.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("Id: " + id +" não encontrado.")
        );

        notasEntityAtual.setTitulo(notasEntity.getTitulo());
        notasEntityAtual.setTexto(notasEntity.getTexto());
        notasEntityAtual.setData(notasEntity.getData());

        return persist(notasEntityAtual);

    }

}
