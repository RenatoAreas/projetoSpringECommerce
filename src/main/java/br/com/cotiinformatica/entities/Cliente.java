package br.com.cotiinformatica.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer idCliente;
	
	@Column(length = 250, nullable = false)
	private String nome;
	
	@Column(length = 100, nullable = false, unique = true)
	private String email;
	
	@Column(length = 11, nullable = false, unique = true)
	private String cpf;
	
	@Column(length = 13, nullable = false)
	private String telefone;
	
	@Column(length = 50, nullable = false)
	private String senha;

	/*
	 	Neste relacionamento devemos colocar o nome do atributo
	 	da classe Endereco onde esta mapeado a chave estrangeira com Cliente	 	
	 	na classe Endereco este atributo é: [private Cliente cliente]	 	
	 	O mappedBy deve fazer referencia ao nome deste atributo
	 */
	@OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
	private Endereco endereco;
	
	@OneToMany(mappedBy = "cliente") //1 Cliente para Muitos Pedidos
	private List<Pedido> pedidos;
	
	public Cliente() {
		// TODO Auto-generated constructor stub
	}

	public Cliente(Integer idCliente, String nome, String email, String cpf, String telefone, String senha,
			Endereco endereco) {
		super();
		this.idCliente = idCliente;
		this.nome = nome;
		this.email = email;
		this.cpf = cpf;
		this.telefone = telefone;
		this.senha = senha;
		this.endereco = endereco;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	@Override
	public String toString() {
		return "Cliente [idCliente=" + idCliente + ", nome=" + nome + ", email=" + email + ", cpf=" + cpf
				+ ", telefone=" + telefone + ", senha=" + senha + "]";
	}

}
