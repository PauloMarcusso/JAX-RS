package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;

public class CarrinhoTest {

	HttpServer server;
	WebTarget target;
	Client client;

	@Before
	public void setUp() {
		server = Servidor.inicializaServidor();
		ClientConfig config = new ClientConfig();		
		config.register(new LoggingFilter());
		this.client = ClientBuilder.newClient(config);
		this.target = client.target("http://localhost:8081");
	}

	@After
	public void setDown() {
		server.stop();
	}

	@Test
	public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {

		String conteudo = target.path("/carrinhos/1").request().get(String.class);
		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
		Assert.assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
	}

	@Test
	public void testaORecursoDeAdicionarProdutoNoCarrinho() {

		Carrinho carrinho = new Carrinho();
		carrinho.adiciona(new Produto(312L, "Oculos de sol", 12.2, 3));
		carrinho.setCidade("Guarulhos");
		carrinho.setRua("Qualquer uma");

		String xml = carrinho.toXML();

		Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);

		Response response = target.path("/carrinhos").request().post(entity);
		
		String location = response.getHeaderString("Location");
		String conteudo = client.target(location).request().get(String.class);
		
		Assert.assertTrue(conteudo.contains("Oculos de sol"));
		Assert.assertEquals(201, response.getStatus());

	}

}
