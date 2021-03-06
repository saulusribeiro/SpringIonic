package br.com.saulusribeiro.springbackend.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.saulusribeiro.springbackend.domain.Cidade;
import br.com.saulusribeiro.springbackend.domain.Cliente;
import br.com.saulusribeiro.springbackend.domain.Endereco;
import br.com.saulusribeiro.springbackend.domain.enums.Perfil;
import br.com.saulusribeiro.springbackend.domain.enums.TipoCliente;
import br.com.saulusribeiro.springbackend.dto.ClienteDTO;
import br.com.saulusribeiro.springbackend.dto.ClienteNewDTO;
import br.com.saulusribeiro.springbackend.repositories.CidadeRepository;
import br.com.saulusribeiro.springbackend.repositories.ClienteRepository;
import br.com.saulusribeiro.springbackend.repositories.EnderecoRepository;
import br.com.saulusribeiro.springbackend.security.UserSS;
import br.com.saulusribeiro.springbackend.services.exceptions.AuthorizationException;
import br.com.saulusribeiro.springbackend.services.exceptions.DataIntegrityException;
import br.com.saulusribeiro.springbackend.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private ClienteRepository repo;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private CidadeRepository   cidadeRepo;

	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private ImageService imageService;
		
	@Value("${img.prefix.client.profile}")
	private String prefix;
	
	@Value("${img.profile.size}")
	private Integer size;
	public Cliente find(Integer id) {
		
		UserSS user = UserService.authenticated();
		
		if (user==null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso Negado");
		}

		Optional<Cliente> obj = repo.findById(id);

		// Utilizando uma fun????o Lambda do Java 8
		// Para retornar o objeto ou a exce????o para ser tratada por um Handler

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto n??o encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		// O obj.setId(null) vai garantir que a opera????o ser?? de inser????o e n??o de
		// update
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;

	}

	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());

		// atualize os dados de newObj com base no argumento obj
		updateData(newObj, obj);
		// o mesmo metodo save usado no insert, quando o id ?? diferente de nulo ele
		// atualiza o objeto
		return repo.save(obj);

	}

	public void delete(Integer id) {
		find(id);

		// se o id n??o existir o find dispara uma exce????o antes

		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("N??o ?? poss??vel excluir porque h?? entidades relacionadas");
		}
	}

	public List<Cliente> findAll() {

		return repo.findAll();
	}
	public Cliente findByEmail(String email) {
		UserSS user = UserService.authenticated();
		if (user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso negado");
		}
	
		Cliente obj = repo.findByEmail(email);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto n??o encontrado! Id: " + user.getId() + ", Tipo: " + Cliente.class.getName());
		}
		return obj;
	}
	// vamos utilizar a classe Page do SpringData para paginar a listagem de
	// categorias

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);

	}

	private void updateData(Cliente newObj, Cliente obj) {

		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	 	
	// Metodo auxiliar para instanciar uma Cliente a partir de um objeto DTO

  public Cliente fromDTO(ClienteDTO objDTO) {

		// o cpf e o tipo do cliente sao nulos pois nao existem no DTO

		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null, null);
	}

	// Sobrecarga de M??todos na Inser????o de Dados transacional
	public Cliente fromDTO(ClienteNewDTO objDTO) {
		Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfcnpj(),
				TipoCliente.toEnum(objDTO.getTipo()), pe.encode(objDTO.getSenha()));
		Cidade cid = new Cidade(objDTO.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(),
				objDTO.getBairro(), objDTO.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDTO.getTelefone1());
		if (objDTO.getTelefone2() != null) {
			cli.getTelefones().add(objDTO.getTelefone2());
			}
		if (objDTO.getTelefone3() != null) {
			cli.getTelefones().add(objDTO.getTelefone3()); 
			}

		return cli;

	}
	public URI uploadProfilePicture(MultipartFile multipartFile) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		} 
		
		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
	
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);
		
		String fileName = prefix + user.getId() + ".jpg";

		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
	}
}
