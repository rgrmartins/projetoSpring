package com.roger.pontointeligente.api.controllers;

import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roger.pontointeligente.api.dtos.CadastroPJDto;
import com.roger.pontointeligente.api.entities.Empresa;
import com.roger.pontointeligente.api.entities.Funcionario;
import com.roger.pontointeligente.api.enums.PerfilEnum;
import com.roger.pontointeligente.api.response.Response;
import com.roger.pontointeligente.api.services.EmpresaService;
import com.roger.pontointeligente.api.services.FuncionarioService;
import com.roger.pontointeligente.api.utils.PasswordUtils;

@RestController //endpoint
@RequestMapping("/api/cadastrar-pj") //mapeamento
@CrossOrigin(origins = "*") //permitir que as requisições venha de diferentes lugares
//é bom permitir que só tenha acesso a api urls "nossa" para que evite ataques
public class CadastroPJController {

	private static final Logger log = LoggerFactory.getLogger(CadastroPJController.class);
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	@Autowired
	private EmpresaService empresaService;

	public CadastroPJController() {
	}

	/**
	 * Cadastra uma pessoa jurídica no sistema
	 * 
	 * @param cadastroPJDto
	 * @param result
	 * @return ResponseEntity<Response<CadastroPJDto>>
	 * @throws NoSuchAlgorithmException
	 */
	@PostMapping
	public ResponseEntity<Response<CadastroPJDto>> cadastrar(@Valid @RequestBody CadastroPJDto cadastroPJDto, BindingResult result) throws NoSuchAlgorithmException{
		log.info("Cadastrando PJ {}", cadastroPJDto.toString());
		Response<CadastroPJDto> response = new Response<CadastroPJDto>();
		
		validarDadosExistentes(cadastroPJDto, result);
		Empresa empresa = this.converterDtoParaEmpresa(cadastroPJDto);
		Funcionario funcionario = this.converterDtoParaFuncionario(cadastroPJDto, result);
		
		//verifica se existe erros de validação
		if (result.hasErrors()) {
			log.error("Erro validando dados de Cadastro de PJ: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response); // retorna o erro 400 no http
		}
		
		this.empresaService.persistir(empresa);
		funcionario.setEmpresa(empresa);
		this.funcionarioService.persistir(funcionario);
		
		//Convertendo em DTO para responder ao cliente
		response.setData(this.converterCadastroPJDto(funcionario));
		return ResponseEntity.ok(response);
		
	}
	
	/**
	 * Verifica se a empresa ou funcionario ja existem na base de dados
	 * 
	 * @param cadastroPJDto
	 * @param result
	 */
	private void validarDadosExistentes(CadastroPJDto cadastroPJDto, BindingResult result) {
		this.empresaService.buscaPorCnpj(cadastroPJDto.getCnpj()).ifPresent(emp -> result.addError(new ObjectError("empresa", "Empresa ja existente!")));
		
		this.funcionarioService.buscaPorCPf(cadastroPJDto.getCpf()).ifPresent(func -> result.addError(new ObjectError("funcionario", "CPF já existente!")));
		
		this.funcionarioService.buscaPorEmail(cadastroPJDto.getEmail()).ifPresent(func -> result.addError(new ObjectError("funcionario", "Email ja existente!")));
	}
	
	/**
	 * Converte os dados do DTO para empresa
	 * 
	 * @param cadastroPJDto
	 * @return Empresa
	 */
	private Empresa converterDtoParaEmpresa(CadastroPJDto cadastroPJDto) {
		Empresa empresa = new Empresa();
		empresa.setCnpj(cadastroPJDto.getCnpj());
		empresa.setRazaoSocial(cadastroPJDto.getRazaoSocial());
		
		return empresa;
	}
	
	/**
	 * Converte os dados da DTO para funcionario
	 * 
	 * @param cadastroPJDto
	 * @param result
	 * @return Funcionario
	 * @throws NoSuchAlgorithmException
	 */
	private Funcionario converterDtoParaFuncionario(CadastroPJDto cadastroPJDto, BindingResult result) throws NoSuchAlgorithmException{
		Funcionario funcionario = new Funcionario();
		funcionario.setNome(cadastroPJDto.getNome());
		funcionario.setEmail(cadastroPJDto.getEmail());
		funcionario.setCpf(cadastroPJDto.getCpf());
		funcionario.setPerfil(PerfilEnum.ROLE_ADMIN); // como é pessoa Jurídica portanto proprietário da empresa
		funcionario.setSenha(PasswordUtils.gerarBCrypt(cadastroPJDto.getSenha()));
		
		return funcionario;
	}
	
	/**
	 * Popula o DTO de cadastro com os dados do funcionário e empresa
	 * 	
	 * @param funcionario
	 * @return CadastroPJDto
	 */
	private CadastroPJDto converterCadastroPJDto(Funcionario funcionario) {
		CadastroPJDto cadastroPJDto = new CadastroPJDto();
		cadastroPJDto.setId(funcionario.getId());
		cadastroPJDto.setNome(funcionario.getNome());
		cadastroPJDto.setEmail(funcionario.getEmail());
		cadastroPJDto.setCpf(funcionario.getCpf());
		cadastroPJDto.setRazaoSocial(funcionario.getEmpresa().getRazaoSocial());
		cadastroPJDto.setCnpj(funcionario.getEmpresa().getCnpj());
		
		return cadastroPJDto;
	}
	
}
