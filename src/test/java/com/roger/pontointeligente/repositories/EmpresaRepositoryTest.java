package com.roger.pontointeligente.repositories;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.roger.pontointeligente.api.entities.Empresa;
import com.roger.pontointeligente.api.repositories.EmpresaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("teste")
public class EmpresaRepositoryTest {

	@Autowired //anotação de injeção de dependência
	private EmpresaRepository empresaRepository;
	
	private static final String CNPJ = "51463645000100";
	
	@Before
	public void setUp() throws Exception {
		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("Empresa de Exemplo");
		empresa.setCnpj(CNPJ);
		this.empresaRepository.save(empresa);
	}
	
	@After
	public final void tearDown() {
		this.empresaRepository.deleteAll();
	}
	
	@Test
	public void testBuscaPorCnpj() {
		Empresa empresa = this.empresaRepository.findByCnpj(CNPJ);
		
		//comando de asserts para validar o teste
		assertEquals(CNPJ,	empresa.getCnpj());
	}
	
	
}
