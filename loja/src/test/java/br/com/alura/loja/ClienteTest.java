package br.com.alura.loja;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.modelo.Projeto;
import com.thoughtworks.xstream.XStream;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static br.com.alura.loja.Servidor.inicializaServidor;
import static javax.ws.rs.core.MediaType.*;
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
        String conteudo = target.path("/carrinhos/1").request().get(String.class);

        Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
        assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
    }

    @Test
    public void testaQueBuscarUmProjetoTrazOProjetoEsperado() {
        String conteudo = target.path("/projetos/1").request().get(String.class);

        Projeto projeto = (Projeto) new XStream().fromXML(conteudo);
        assertEquals("Minha loja", projeto.getNome());
    }

    @Test
    public void testaMetodoPostDoCarrinho() throws Exception {
        Carrinho carrinho = new Carrinho();
        carrinho.adiciona(new Produto(314L, "Tablet", 999, 1));
        carrinho.setRua("Rua Vergueiro");
        carrinho.setCidade("Sao Paulo");
        String xml = carrinho.toXml();

        Entity<String> entity = Entity.entity(xml, APPLICATION_XML);

        Response response = target.path("/carrinhos").request().post(entity);
        Assert.assertEquals(201, response.getStatus());

        String location = response.getHeaderString("Location");
        String conteudo = client.target(location).request().get(String.class);
        Assert.assertTrue(conteudo.contains("Microfone"));
    }

    @Test
    public void testaMetodoPostDoProjeto() throws Exception {
        Projeto projeto = new Projeto(2, "web service", 2017);
        String xml = projeto.toXml();

        Entity<String> entity = Entity.entity(xml, APPLICATION_XML);

        Response response = target.path("/projetos").request().post(entity);
        Assert.assertEquals("<adicionado>true</adicionado>", response.readEntity(String.class));
    }

    @After
    public void tearDown() {
        server.stop();
    }
    
    

//    @Test
//    public void testaQueAConexaoComOServidorFunciona() {
//        Client client = ClientBuilder.newClient();
//
//        WebTarget target = client.target("http://www.mocky.io");
//        String conteudo = target.path("/v2/52aaf5deee7ba8c70329fb7d").request().get(String.class);
//
//
//    }

//    @Test
//    public void testaQueAConexaoComOServidorDoProjetos() {
//        Client client = ClientBuilder.newClient();
//
//        WebTarget target = client.target("http://localhost:8081");
//        String conteudo = target.path("/projetos").request().get(String.class);
//
//        assertTrue(conteudo.contains("<nome>Minha loja</nome>"));
//    }

}
