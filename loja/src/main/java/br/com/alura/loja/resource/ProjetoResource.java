package br.com.alura.loja.resource;

import br.com.alura.loja.dao.ProjetoDAO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("projetos")
public class ProjetoResource {

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String busca(@PathParam("id") long id) {
        ProjetoDAO projetoDAO = new ProjetoDAO();
        return projetoDAO.busca(id).toXml();
    }

}
