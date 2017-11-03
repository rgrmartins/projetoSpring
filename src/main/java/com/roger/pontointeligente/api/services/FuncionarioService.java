package com.roger.pontointeligente.api.services;

import java.util.Optional;

import com.roger.pontointeligente.api.entities.Funcionario;

public interface FuncionarioService {
	
	/**
	 * Persiste um Funcionário na base de dados
	 * @param funcionario
	 * @return Funcionário
	 */
	Funcionario persistir(Funcionario funcionario);

	/**
	 * Busca e retorna um funcionário dado um CPF
	 * @param cpf
	 * @return Optional<Funcionario> 
	 */
	Optional<Funcionario> buscaPorCPf(String cpf);
	
	/**
	 * Busca e retorna um funcionário dado um email
	 * @param email
	 * @return Optional<Funcionario> 
	 */ 
	Optional<Funcionario> buscaPorEmail(String email);
	
	/**
	 * Busca e retorna um funcionário dado um ID
	 * @param ID
	 * @return Optional<Funcionario> 
	 */ 
	Optional<Funcionario> buscaPorId(Long id);
	
}
