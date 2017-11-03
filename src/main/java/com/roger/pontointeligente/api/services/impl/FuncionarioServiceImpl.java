package com.roger.pontointeligente.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roger.pontointeligente.api.entities.Funcionario;
import com.roger.pontointeligente.api.repositories.FuncionarioRepository;
import com.roger.pontointeligente.api.services.FuncionarioService;
import org.slf4j.LoggerFactory;

@Service
public class FuncionarioServiceImpl implements FuncionarioService{
	
	//Criando log para passar informação para o console
	private static final Logger log = LoggerFactory.getLogger(FuncionarioServiceImpl.class);

	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Override
	public Funcionario persistir(Funcionario funcionario) {
		log.info("Persistindo funcionário: {}", funcionario);
		return this.funcionarioRepository.save(funcionario);
		
	}

	@Override
	public Optional<Funcionario> buscaPorCPf(String cpf) {
		log.info("Buscando funcionário pelo CPF: {}", cpf);
		return Optional.ofNullable(this.funcionarioRepository.findByCpf(cpf));
	}

	@Override
	public Optional<Funcionario> buscaPorEmail(String email) {
		log.info("Buscando funcionário pelo E-mail: {}", email);
		return Optional.ofNullable(this.funcionarioRepository.findByEmail(email));
	}

	@Override
	public Optional<Funcionario> buscaPorId(Long id) {
		log.info("Buscando funcionário pelo ID: {}", id);
		return Optional.ofNullable(this.funcionarioRepository.findOne(id));
	}

}
