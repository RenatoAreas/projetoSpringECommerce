package br.com.cotiinformatica.controllers;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.cotiinformatica.dtos.clientes.ClientePostDTO;
import br.com.cotiinformatica.dtos.clientes.ClientePutDTO;
import br.com.cotiinformatica.entities.Cliente;
import br.com.cotiinformatica.entities.Endereco;
import br.com.cotiinformatica.interfaces.IClienteRepository;
import br.com.cotiinformatica.security.Cryptography;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Controller
@Transactional
public class ClientesController {

	private static final String ENDPOINT = "/api/clientes";

	@Autowired
	private IClienteRepository clienteRepository;

	@CrossOrigin
	@RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> post(@RequestBody ClientePostDTO dto) {

		try {

			// verificar se o email já encontra-se cadastrado..
			if (clienteRepository.findByEmail(dto.getEmail()) != null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("O email informado já encontra-se cadastrado.");
			}

			// capturando os dados do cliente
			Cliente cliente = new Cliente();
			cliente.setNome(dto.getNome());
			cliente.setEmail(dto.getEmail());
			cliente.setCpf(dto.getCpf());
			cliente.setTelefone(dto.getTelefone());
			cliente.setSenha(Cryptography.criptografar(dto.getSenha()));

			// capturando os dados do endereço do cliente
			cliente.setEndereco(new Endereco());
			cliente.getEndereco().setLogradouro(dto.getLogradouro());
			cliente.getEndereco().setNumero(dto.getNumero());
			cliente.getEndereco().setComplemento(dto.getComplemento());
			cliente.getEndereco().setBairro(dto.getBairro());
			cliente.getEndereco().setCidade(dto.getCidade());
			cliente.getEndereco().setEstado(dto.getEstado());
			cliente.getEndereco().setCep(dto.getCep());

			// gravando o cliente..
			clienteRepository.save(cliente);

			return ResponseEntity.status(HttpStatus.OK).body("Cliente cadastrado com sucesso.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
		}
	}

	@CrossOrigin
	@RequestMapping(value = ENDPOINT, method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<String> put(@RequestBody ClientePutDTO dto) {

		try {

			// buscar o cliente atraves do id..
			Optional<Cliente> result = clienteRepository.findById(dto.getIdCliente());

			if (result == null || result.isPresent()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cliente não encontrado.");
			}

			// modificando os dados do cliente
			Cliente cliente = result.get();
			cliente.setNome(dto.getNome());
			cliente.setTelefone(dto.getTelefone());
			cliente.setSenha(Cryptography.criptografar(dto.getSenha()));

			// modificando os dados do endereço
			cliente.getEndereco().setLogradouro(dto.getLogradouro());
			cliente.getEndereco().setNumero(dto.getNumero());
			cliente.getEndereco().setComplemento(dto.getComplemento());
			cliente.getEndereco().setBairro(dto.getBairro());
			cliente.getEndereco().setCidade(dto.getCidade());
			cliente.getEndereco().setEstado(dto.getEstado());
			cliente.getEndereco().setCep(dto.getCep());

			// atualizando o cliente..
			clienteRepository.save(cliente);

			return ResponseEntity.status(HttpStatus.OK).body("Cliente atualizado com sucesso.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
		}
	}

	@CrossOrigin
	@RequestMapping(value = ENDPOINT + "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<String> delete(@PathVariable("id") Integer id) {

		try {

			// buscar o cliente atraves do id..
			Optional<Cliente> result = clienteRepository.findById(id);

			if (result == null || result.isPresent()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cliente não encontrado.");
			}

			// excluindo o cliente..
			clienteRepository.delete(result.get());

			return ResponseEntity.status(HttpStatus.OK).body("Cliente excluido com sucesso.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
		}
	}

	@CrossOrigin
	@RequestMapping(value = ENDPOINT + "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> getById(@PathVariable("id") Integer id) {
		return null;
	}
}
