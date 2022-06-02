package br.com.saulusribeiro.springbackend.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.saulusribeiro.springbackend.domain.ItemPedido;
import br.com.saulusribeiro.springbackend.domain.PagamentoComBoleto;
import br.com.saulusribeiro.springbackend.domain.Pedido;
import br.com.saulusribeiro.springbackend.domain.enums.EstadoPagamento;
import br.com.saulusribeiro.springbackend.repositories.ClienteRepository;
import br.com.saulusribeiro.springbackend.repositories.ItemPedidoRepository;
import br.com.saulusribeiro.springbackend.repositories.PagamentoRepository;
import br.com.saulusribeiro.springbackend.repositories.PedidoRepository;
import br.com.saulusribeiro.springbackend.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService    boletoService;  // Injeção do boletoService
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;
	
	public Pedido find(Integer id) {
		
		Optional<Pedido> obj = repo.findById(id);
		
		// Utilizando uma função Lambda do Java 8
		// Para retornar o objeto ou a exceção para ser tratada por um Handler
		
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
		}
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));;
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			// numa situação real faz a chamada ao webservice do cartão
			// para preencher data de vencimento do boleto. Esta é apenas
			// uma simulação via serviço
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		
		for(ItemPedido ip :  obj.getItens()) {
			
			ip.setDesconto(0.00);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
		    ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		
		itemPedidoRepository.saveAll(obj.getItens());
		emailService.sendOrderConfirmation(obj);
		return obj;
	}
	
	
}
