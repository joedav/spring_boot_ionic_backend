package com.joedav.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.joedav.cursomc.domain.Cliente;
import com.joedav.cursomc.domain.enums.TipoCliente;

public class ClienteDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	// propriedades
	private Integer id;
	
	@NotEmpty(message = "Preenchimento do nome é obrigatório!")
	@Length(min = 5, max = 200, message = "O tamanho do nome deve ser entre 5 e 200 caracteres!")
	private String nome;
	@NotEmpty(message = "Preenchimento do email é obrigatório!")
	@Email(message = "Email inválido!")
	private String email;
	private String cpfOuCnpj;

	// construtores
	public ClienteDTO() {
	}

	public ClienteDTO(Cliente obj) {
		this.id = obj.getId();
		this.nome= obj.getNome();
		this.email=obj.getEmail();
		this.cpfOuCnpj=obj.getCpfOuCnpj();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	// getters e setters	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}
	
}
