package br.com.saulusribeiro.springbackend;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.saulusribeiro.springbackend.domain.Categoria;
import br.com.saulusribeiro.springbackend.domain.Cidade;
import br.com.saulusribeiro.springbackend.domain.Cliente;
import br.com.saulusribeiro.springbackend.domain.Endereco;
import br.com.saulusribeiro.springbackend.domain.Estado;
import br.com.saulusribeiro.springbackend.domain.ItemPedido;
import br.com.saulusribeiro.springbackend.domain.Pagamento;
import br.com.saulusribeiro.springbackend.domain.PagamentoComBoleto;
import br.com.saulusribeiro.springbackend.domain.PagamentoComCartao;
import br.com.saulusribeiro.springbackend.domain.Pedido;
import br.com.saulusribeiro.springbackend.domain.Produto;
import br.com.saulusribeiro.springbackend.domain.enums.EstadoPagamento;
import br.com.saulusribeiro.springbackend.domain.enums.TipoCliente;
import br.com.saulusribeiro.springbackend.repositories.CategoriaRepository;
import br.com.saulusribeiro.springbackend.repositories.CidadeRepository;
import br.com.saulusribeiro.springbackend.repositories.ClienteRepository;
import br.com.saulusribeiro.springbackend.repositories.EnderecoRepository;
import br.com.saulusribeiro.springbackend.repositories.EstadoRepository;
import br.com.saulusribeiro.springbackend.repositories.ItemPedidoRepository;
import br.com.saulusribeiro.springbackend.repositories.PagamentoRepository;
import br.com.saulusribeiro.springbackend.repositories.PedidoRepository;
import br.com.saulusribeiro.springbackend.repositories.ProdutoRepository;

@SpringBootApplication
public class SpringbackendApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringbackendApplication.class, args);
	}

	
	@Override
	public void run(String... args) throws Exception {

		}

}
