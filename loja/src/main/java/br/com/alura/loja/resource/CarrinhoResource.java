package br.com.alura.loja.resource;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;


@Path("carrinhos")
public class CarrinhoResource {

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Carrinho busca(@PathParam("id") long id) {
        CarrinhoDAO carrinhoDAO = new CarrinhoDAO();
        return carrinhoDAO.busca(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response adiciona(Carrinho carrinho) throws URISyntaxException {
        new CarrinhoDAO().adiciona(carrinho);
        URI uri = new URI("/carrinhos/" + carrinho.getId());
        return Response.created(uri).build();
    }

    @Path("{id}/produtos/{produtoId}")
    @DELETE
    public Response removeProduto(@PathParam("id") long id, @PathParam("produtoId") long produtoId) {
        Carrinho carrinho = new CarrinhoDAO().busca(id);
        carrinho.remove(produtoId);
        return Response.ok().build();
    }

    @Path("{id}/produtos/{produtoId}")
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public Response alteraProduto(@PathParam("id") long id, @PathParam("produtoId") long produtoId, Produto produto) {
        Carrinho carrinho = new CarrinhoDAO().busca(id);
        carrinho.troca(produto);
        return Response.ok().build();
    }

    @Path("{id}/produtos/{produtoId}/quantidade")
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public Response alteraQuantidadeDeProduto(@PathParam("id") long id, @PathParam("produtoId") long produtoId, Produto produto) {
        Carrinho carrinho = new CarrinhoDAO().busca(id);
        carrinho.trocaQuantidade(produto);
        return Response.ok().build();
    }

}
