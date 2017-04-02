package br.com.alura.loja.resource;

import br.com.alura.loja.dao.CarrinhoDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("carrinhos")
public class CarrinhoResource {

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String busca(@PathParam("id") long id) {
        CarrinhoDAO carrinhoDAO = new CarrinhoDAO();
        return carrinhoDAO.busca(id).toXml();
    }

}
