package com.roger.pontointeligente.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roger.pontointeligente.api.entities.Empresa;
import com.roger.pontointeligente.api.repositories.EmpresaRepository;
import com.roger.pontointeligente.api.services.EmpresaService;

@Service
public class EmpresaServiceImpl implements EmpresaService {

	private static final Logger log = LoggerFactory.getLogger(EmpresaServiceImpl.class);
	
	@Autowired //injeção de dependencia
	private EmpresaRepository EmpresaRepository;
	
	@Override
	public Optional<Empresa> buscaPorCnpj(String cnpj) {
		log.info("Buscando uma empesa pelo CNPJ {}", cnpj);
		return Optional.ofNullable(EmpresaRepository.findByCnpj(cnpj)); //recurso para evitar null pointer exception do java 8
	}

	@Override
	public Empresa persistir(Empresa empresa) {
		log.info("Pesistindo empresa: {}", empresa);
		return this.EmpresaRepository.save(empresa);
	}

}
