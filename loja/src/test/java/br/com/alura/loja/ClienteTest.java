package br.com.alura.loja;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.modelo.Projeto;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import static br.com.alura.loja.Servidor.inicializaServidor;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static org.junit.Assert.assertEquals;

public class ClienteTest {

    private HttpServer server;
    private WebTarget target;
    private Client client;

    @Before
    public void setUp() {
        server = inicializaServidor();
        ClientConfig config = new ClientConfig();
        config.register(new LoggingFilter());
        client = ClientBuilder.newClient(config);
        target = client.target("http://localhost:8081");
    }

    @Test
    public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {
        Carrinho carrinho = target.path("/carrinhos/1").request().get(Carrinho.class);
        assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
    }

    @Test
    public void testaQueBuscarUmProjetoTrazOProjetoEsperado() {
        Projeto projeto = target.path("/projetos/1").request().get(Projeto.class);
        assertEquals("Minha loja", projeto.getNome());
    }

    @Test
    public void testaMetodoPostDoCarrinho() throws Exception {
        Carrinho carrinho = new Carrinho();
        carrinho.adiciona(new Produto(314L, "Tablet", 999, 1));
        carrinho.setRua("Rua Vergueiro");
        carrinho.setCidade("Sao Paulo");


        Entity<Carrinho> entity = Entity.entity(carrinho, APPLICATION_XML);

        Response response = target.path("/carrinhos").request().post(entity);
        Assert.assertEquals(201, response.getStatus());

        String location = response.getHeaderString("Location");
        Carrinho carrinho1 = client.target(location).request().get(Carrinho.class);
        Assert.assertTrue(carrinho1.getProdutos().get(0).getNome().equals("Tablet"));
    }

    @Test
    public void testaMetodoPostDoProjeto() throws Exception {
        Projeto projeto = new Projeto(2, "web service", 2017);

        Entity<Projeto> entity = Entity.entity(projeto, APPLICATION_XML);

        Response response = target.path("/projetos").request().post(entity);
        Assert.assertEquals(201, response.getStatus());
    }

    @After
    public void tearDown() {
        server.stop();
    }

}
