package br.com.alura.loja;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Projeto;
import com.thoughtworks.xstream.XStream;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import static br.com.alura.loja.Servidor.inicializaServidor;
import static org.junit.Assert.assertEquals;

public class ClienteTest {

    private HttpServer server;

    @Before
    public void setUp() {
        server = inicializaServidor();
    }

    @Test
    public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081");
        String conteudo = target.path("/carrinhos/1").request().get(String.class);

        Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
        assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
    }

    @Test
    public void testaQueBuscarUmProjetoTrazOProjetoEsperado() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081");
        String conteudo = target.path("/projetos/1").request().get(String.class);

        Projeto projeto = (Projeto) new XStream().fromXML(conteudo);
        assertEquals("Minha loja", projeto.getNome());
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
