package com.thiscosta.startuplanches.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.thiscosta.startuplanches.model.Compra;
import com.thiscosta.startuplanches.model.CompraLanche;
import com.thiscosta.startuplanches.model.ErrorObject;
import com.thiscosta.startuplanches.model.Ingrediente;
import com.thiscosta.startuplanches.model.Lanche;
import com.thiscosta.startuplanches.model.LancheIngrediente;
import com.thiscosta.startuplanches.model.Promocao;
import com.thiscosta.startuplanches.model.PromocaoCompra;
import com.thiscosta.startuplanches.repository.CompraRepository;

@Service
public class CompraService {

	public CompraService() {
	}

	public CompraService(
			CompraRepository repository, IngredienteService ingredienteService, LancheService lancheService,
			LancheIngredienteService lancheIngredienteService, CompraLancheService compraLancheService,
			PromocaoService promocaoService, PromocaoCompraService promocaoCompraService
	) {
		this.repository = repository;
		this.ingredienteService = ingredienteService;
		this.lancheService = lancheService;
		this.lancheIngredienteService = lancheIngredienteService;
		this.compraLancheService = compraLancheService;
		this.promocaoService = promocaoService;
		this.promocaoCompraService = promocaoCompraService;
	}
	

	@Autowired
	CompraRepository repository;

	@Autowired
	IngredienteService ingredienteService;

	@Autowired
	LancheService lancheService;

	@Autowired
	LancheIngredienteService lancheIngredienteService;

	@Autowired
	CompraLancheService compraLancheService;
	
	@Autowired
	PromocaoService promocaoService;
	
	@Autowired
	PromocaoCompraService promocaoCompraService;

	public List<Compra> listarCompras() {
		return repository.findAll();
	}

	public Compra verCompra(Long id) {
		return repository.getOne(id);
	}

	public Compra atualizarCompra(Long id, Compra compra) {
		Compra novaCompra = repository.getOne(id);

		if (novaCompra == null) {
			return new Compra();
		}

		BeanUtils.copyProperties(compra, novaCompra, "id");

		return novaCompra;
	}

	public void excluirCompra(Long id) {
		repository.deleteById(id);
	}

	public ResponseEntity novaCompra(Compra compra) {
		try {

			// Zera o preço da compra caso o usuário tenha enviado uma compra com valor
			compra.setPreco(0);
			compra.setPromocoesCompra(new ArrayList<PromocaoCompra>());

			// Calcula o preço unitário de cada lanche da compra
			List<CompraLanche> lanchesCompra = new ArrayList<>(compra.getLanchesCompra());
			for (int i = 0; i < lanchesCompra.size(); i++) {
				/*
				 * Incrementa o valor total da compra com o valor unitário de cada lanche da
				 * compra Também atualiza cada CompraLanche com o novo Lanche criado (caso não
				 * exista)
				 */
				lanchesCompra.get(i).setCompra(compra);
				compra.incrementarPreco(this.valorCompraLanche(lanchesCompra.get(i)));
			}

			compra.setPreco(compra.getPreco());

			// Salva a compra

			compra = repository.save(compra);

			// Salva os lanches da compra
			for (CompraLanche compraLanche : compra.getLanchesCompra()) {
				compraLanche.setCompra(compra);
				compraLancheService.novaCompraLanche(compraLanche);
			}
			
			for(PromocaoCompra promocaoCompra : compra.getPromocoesCompra()) {
				promocaoCompra.setCompra(compra);
				promocaoCompraService.novaPromocaoCompra(promocaoCompra);
			}
			
			return ResponseEntity.ok().body(repository.getOne(compra.getId()));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ErrorObject(false, e.getMessage()));
		}
	}

	public double valorCompraLanche(CompraLanche lancheCompra) {
		
		double valorFinal = 0;

		// Armazena todos os ingredientes em memória para não ficar procurando no banco
		// de dados.
		List<Ingrediente> ingredientes = ingredienteService.listarIngredientes();
		Lanche lanche = lancheCompra.getLanche();
		
		Long idLanche = lanche.getId();
		// Verifica se existe um lanche com o nome passado.
		if (idLanche == null) {
			// Caso exista, verifica se foram passados os ingredientes do lanche
			if (lanche.getIngredientes().size() > 0) {
				// Percorre todos os lanches da compra de acordo com a quantidade
				double valorLanche = 0;

				// Pega todos os ingredientes do lanche
				List<LancheIngrediente> ingredientesLanche = lanche.getIngredientes();
				
				// Percorre todos os ingredientes do lanche
				for (LancheIngrediente ingredienteLanche : ingredientesLanche) {

					// Incrementa no valor total o valor do ingrediente encontrado somado com sua
					// quantidade no lanche
					for (Ingrediente ingrediente : ingredientes) {
						if (ingrediente.getId() == ingredienteLanche.getIngrediente().getId()) {
							valorLanche += ingrediente.getPreco() * ingredienteLanche.getQuantidade();
							ingredienteLanche.setIngrediente(ingrediente);
						}
					}
				}

				// cria um novo lanche e adiciona no atributo lanche do objeto CompraLanche
				Lanche novoLanche = lancheService.novoLanche(new Lanche(lanche.getNome(), valorLanche));
				
				for (LancheIngrediente ingredienteLanche : ingredientesLanche) {
					ingredienteLanche.setLanche(novoLanche);
					lancheIngredienteService.novoLancheIngrediente(ingredienteLanche);
				}

				novoLanche.setIngredientes(ingredientesLanche);
				lancheCompra.setLanche(novoLanche);

				// Aplicar descontos de promoções
				valorLanche = valorLanche - aplicarPromocoes(lanche, ingredientes, valorLanche, lancheCompra.getCompra());
				
				// Incrementar o valor final do CompraLanche
				valorFinal += valorLanche * lancheCompra.getQuantidade();

			} else {
				// A lista de ingredientes é obrigatória caso não haja um lanche com o ID
				// passado.
				throw new RuntimeException("Caso o lanche não exista, a lista de ingredientes é obrigatória.");
			}
		} else {
			// Lanche já existe
			lanche = lancheService.verLanche(lanche.getId());
			if (lanche == null)
				throw new RuntimeException("Não foi encontrado um lanche com o ID fornecido.");
			lancheCompra.setLanche(lanche);
			valorFinal += valorDoLanche(lanche, ingredientes, lancheCompra.getCompra()) * lancheCompra.getQuantidade();
		}

		
		return valorFinal;
	}

	public double valorDoLanche(Lanche lanche, List<Ingrediente> ingredientes, Compra compra) {
		return lanche.getPreco() - aplicarPromocoes(lanche, ingredientes, lanche.getPreco(), compra);
	}

	public double aplicarPromocoes(Lanche lanche, List<Ingrediente> ingredientes, double valorAtual, Compra compra) {

		double valorDesconto = 0;
		
		List<Promocao> promocoes = promocaoService.listarPromocoes();

		// Promoção light
		boolean temAlface = false;
		boolean temBacon = false;

		// Muita carne
		int qtdPorcoesCarne = 0;
		int porcoesCarneDescontadas = 0;

		// Muito queijo
		int qtdPorcoesQueijo = 0;
		int porcoesQueijoDescontadas = 0;

		for (LancheIngrediente lancheIngrediente : lanche.getIngredientes()) {
			
			if (lancheIngrediente.getIngrediente().getId() == ingredienteService.verIngredientePorNome("Alface")
					.getId())
				temAlface = true;

			if (lancheIngrediente.getIngrediente().getId() == ingredienteService.verIngredientePorNome("Bacon").getId())
				temBacon = true;

			if (lancheIngrediente.getIngrediente().getId() == ingredienteService
					.verIngredientePorNome("Hamburguer de carne").getId()) {
				qtdPorcoesCarne += lancheIngrediente.getQuantidade();
				while (qtdPorcoesCarne >= 3) {
					porcoesCarneDescontadas++;
					qtdPorcoesCarne -= 3;
				}
			}

			if (lancheIngrediente.getIngrediente().getId() == ingredienteService.verIngredientePorNome("Queijo")
					.getId()) {
				qtdPorcoesQueijo += lancheIngrediente.getQuantidade();
				while (qtdPorcoesQueijo >= 3) {
					porcoesQueijoDescontadas++;
					qtdPorcoesQueijo -= 3;
				}
			}

		}

		// Promoção light
		if (temAlface && !temBacon) {
			valorDesconto += valorAtual / 10;
			
			boolean incrementou = false;
			//Procura a promoção no array de promoções
			for(Promocao promocao : promocoes) {
				//Caso ache, verifica se ela já foi aplicada à compra
				if(promocao.getNome().equals("Light")) {
					if(compra.getPromocoesCompra().size() > 0) {
						for(PromocaoCompra promocaoCompra : compra.getPromocoesCompra()) {
							//Se ja foi, só incrementa o valor que foi descontado 
							if(promocaoCompra.getPromocao().getNome().equals("Light")) {
								promocaoCompra.setDesconto(promocaoCompra.getDesconto() + valorDesconto);
								incrementou = true;
							}
							
						}
					}
					//Caso a promoção não tenha sido aplicada à compra ainda, aplica
					if(incrementou == false) {
						PromocaoCompra promocaoCompra = new PromocaoCompra(promocao, compra, valorDesconto);
						compra.getPromocoesCompra().add(promocaoCompra);
					}
				}
			}
		}

		// Muita carne
		if (porcoesCarneDescontadas > 0) {
			for (Ingrediente ingrediente : ingredientes) {
				if (ingrediente.getNome().equals("Hamburguer de carne")) {
					valorDesconto += porcoesCarneDescontadas * ingrediente.getPreco();
					
					boolean incrementou = false;
					
					//Procura a promoção no array de promoções
					for(Promocao promocao : promocoes) {
						//Caso ache, verifica se ela já foi aplicada à compra
						if(promocao.getNome().equals("Muita carne")) {
							for(PromocaoCompra promocaoCompra : compra.getPromocoesCompra()) {
								//Se ja foi, só incrementa o valor que foi descontado 
								if(promocaoCompra.getPromocao().getNome().equals("Muita carne")) {
									promocaoCompra.setDesconto(promocaoCompra.getDesconto() + valorDesconto);
									incrementou = true;
								}
								
							}
							//Caso a promoção não tenha sido aplicada à compra ainda, aplica
							if(!incrementou) {
								PromocaoCompra promocaoCompra = new PromocaoCompra(promocao, compra, porcoesCarneDescontadas * ingrediente.getPreco());
								compra.getPromocoesCompra().add(promocaoCompra);
							}
						}
					}
				}
			}
		}

		// Muito queijo
		if (porcoesQueijoDescontadas > 0) {
			for (Ingrediente ingrediente : ingredientes) {
				if (ingrediente.getNome().equals("Queijo")) {
					valorDesconto += porcoesQueijoDescontadas * ingrediente.getPreco();
					
					boolean incrementou = false;
					
					//Procura a promoção no array de promoções
					for(Promocao promocao : promocoes) {
						//Caso ache, verifica se ela já foi aplicada à compra
						if(promocao.getNome().equals("Muito queijo")) {
							for(PromocaoCompra promocaoCompra : compra.getPromocoesCompra()) {
								//Se ja foi, só incrementa o valor que foi descontado 
								if(promocaoCompra.getPromocao().getNome().equals("Muito queijo")) {
									promocaoCompra.setDesconto(promocaoCompra.getDesconto() + valorDesconto);
									incrementou = true;
								}
								
							}
							//Caso a promoção não tenha sido aplicada à compra ainda, aplica
							if(!incrementou) {
								PromocaoCompra promocaoCompra = new PromocaoCompra(promocao, compra, porcoesQueijoDescontadas * ingrediente.getPreco());
								compra.getPromocoesCompra().add(promocaoCompra);
							}
						}
				}
				}
			}
		}

		return valorDesconto;

	}
}
