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

import br.com.alura.loja.dao.ProjetoDAO;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.modelo.Projeto;

public class ProjetoTest {
	
	HttpServer server;
	WebTarget target;
	Client client;
	
	@Before
	public void setUp() {
		server = Servidor.inicializaServidor();
		ClientConfig config = new ClientConfig();
		config.register(new LoggingFilter());
		this.client = ClientBuilder	.newClient(config);
		this.target = client.target("http://localhost:8081");
	}
	
	@After
	public void setDown() {
		server.stop();
	}

	@Test
	public void testaQueAConexaoComOServidorFuncionaNoPathDeProjetos() {
		
		String conteudo = target.path("/projetos/1").request().get(String.class);
		Projeto projeto = (Projeto) new XStream().fromXML(conteudo);
		Assert.assertEquals("Minha Loja", projeto.getNome());
	}
	
	@Test
	public void testaORecursoDeAdicionarProdutoNoCarrinho() {
		
		Projeto projeto = new Projeto();
		projeto.setId(323L);
		projeto.setNome("POST");
		projeto.setAnoDeInicio(1999);
		
		String xml = projeto.toXML();
		
		Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);
		
		Response response = target.path("/projetos").request().post(entity);
		
		Assert.assertEquals(201, response.getStatus());
		
		String location = response.getHeaderString("Location");
		String conteudo = client.target(location).request().get(String.class);
		Assert.assertTrue(conteudo.contains("POST"));
	}
}

















