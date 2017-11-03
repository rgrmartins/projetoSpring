package com.roger.pontointeligente.api.services;

import java.util.Optional;

import com.roger.pontointeligente.api.entities.Empresa;

public interface EmpresaService {

	
	//Retorna uma empresa dado um CNPJ.
	Optional<Empresa> buscaPorCnpj(String cnpj);
	
	
	//Cadastra uma nova empresa na Base de Dados;
	Empresa persistir(Empresa empresa);
	
}
