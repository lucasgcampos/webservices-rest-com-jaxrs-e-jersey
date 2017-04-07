package br.com.alura.loja.resource;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.dao.ProjetoDAO;
import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Projeto;
import com.thoughtworks.xstream.XStream;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

@Path("projetos")
public class ProjetoResource {

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String busca(@PathParam("id") long id) {
        ProjetoDAO projetoDAO = new ProjetoDAO();
        return projetoDAO.busca(id).toXml();
    }

    @POST
    @Produces(value = MediaType.APPLICATION_XML)
    public Response adiciona(String conteudo) throws URISyntaxException {
        Projeto projeto = (Projeto) new XStream().fromXML(conteudo);

        new ProjetoDAO().adiciona(projeto);

        return Response.created(new URI("/projetos/" + projeto.getId())).build();
    }

    @Path("{id}")
    @DELETE
    public Response removeProjeto(@PathParam("id") long id) {
        new ProjetoDAO().remove(id);
        return Response.ok().build();
    }

}
