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

			System.out.println("Limpou o preço e as promoções");
			
			// Calcula o preço unitário de cada lanche da compra
			List<CompraLanche> lanchesCompra = new ArrayList<>(compra.getLanchesCompra());
			System.out.println("Setou o lanchesCompra como sendo o getLanchesCompra()");
			System.out.println("Tamanho do array de lanches compra: "+lanchesCompra.size());
			for (int i = 0; i < lanchesCompra.size(); i++) {
				/*
				 * Incrementa o valor total da compra com o valor unitário de cada lanche da
				 * compra Também atualiza cada CompraLanche com o novo Lanche criado (caso não
				 * exista)
				 */
				lanchesCompra.get(i).setCompra(compra);
				System.out.println("Setou a compra do lanches compra");
				compra.incrementarPreco(this.valorCompraLanche(lanchesCompra.get(i)));
			}

			System.out.println("Setando o novo preço da compra");
			compra.setPreco(compra.getPreco());

			System.out.println("preço da compra: " + compra.getPreco());
			// Salva a compra

			compra = repository.save(compra);

			System.out.println("salvou a compra\n ID: "+compra.getId());

			// Salva os lanches da compra
			for (CompraLanche compraLanche : compra.getLanchesCompra()) {
				compraLanche.setCompra(compra);
				compraLancheService.novaCompraLanche(compraLanche);
			}
			
			System.out.println("Passou do compraLancheService: "+compra.getLanchesCompra().get(0).getCompra().getId());

			for(PromocaoCompra promocaoCompra : compra.getPromocoesCompra()) {
				System.out.println("Entrou no promocaoCompra");
				promocaoCompra.setCompra(compra);
				promocaoCompraService.novaPromocaoCompra(promocaoCompra);
			}
			System.out.println("Passou do promocaoCompraService: "+compra.getPromocoesCompra().size());

			return ResponseEntity.ok().body(repository.getOne(compra.getId()));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ErrorObject(false, e.toString()));
		}
	}

	public double valorCompraLanche(CompraLanche lancheCompra) {
		
		System.out.println("Chegou no valorCompraLanche");
		double valorFinal = 0;

		// Armazena todos os ingredientes em memória para não ficar procurando no banco
		// de dados.
		List<Ingrediente> ingredientes = ingredienteService.listarIngredientes();
		System.out.println("Listou os ingredientes "+ingredientes.size());
		Lanche lanche = lancheCompra.getLanche();
		System.out.println("pegou o lanche da compra: "+lanche.getNome());

		Long idLanche = lanche.getId();
		// Verifica se existe um lanche com o nome passado.
		if (idLanche == null) {
			System.out.println("Lanche não existe");
			System.out.println("Ingredientes do lanche: "+lanche.getIngredientes().size());
			// Caso exista, verifica se foram passados os ingredientes do lanche
			if (lanche.getIngredientes().size() > 0) {
				// Percorre todos os lanches da compra de acordo com a quantidade
				double valorLanche = 0;

				System.out.println("Não há um lanche com o nome");

				// Pega todos os ingredientes do lanche
				List<LancheIngrediente> ingredientesLanche = lanche.getIngredientes();
				System.out.println("ingredientes do lanche: "+ingredientesLanche.size());
				
				// Percorre todos os ingredientes do lanche
				for (LancheIngrediente ingredienteLanche : ingredientesLanche) {

					System.out.println("Ingrediente " + ingredienteLanche.getIngrediente().getNome() + " do lanche");
					// Incrementa no valor total o valor do ingrediente encontrado somado com sua
					// quantidade no lanche
					for (Ingrediente ingrediente : ingredientes) {
						if (ingrediente.getId() == ingredienteLanche.getIngrediente().getId()) {
							System.out.println("ID do ingrediente JPA: "+ingrediente.getId()+", ID IngredienteLanche "+ingredienteLanche.getIngrediente().getId());
							System.out.println("\nO ingrediente(JPA) "+ingrediente.getNome()+" está com o preço R$"+ingrediente.getPreco());
							valorLanche += ingrediente.getPreco() * ingredienteLanche.getQuantidade();
							ingredienteLanche.setIngrediente(ingrediente);
						}
					}
					System.out.println("Valor do lanche " + lanche.getNome() + "\n R$" + valorLanche);
				}

				// cria um novo lanche e adiciona no atributo lanche do objeto CompraLanche
				Lanche novoLanche = lancheService.novoLanche(new Lanche(lanche.getNome(), valorLanche));
				
				System.out.println("Novo lanche criado: "+novoLanche.getId());

				for (LancheIngrediente ingredienteLanche : ingredientesLanche) {
					ingredienteLanche.setLanche(novoLanche);
					lancheIngredienteService.novoLancheIngrediente(ingredienteLanche);
				}

				novoLanche.setIngredientes(ingredientesLanche);
				lancheCompra.setLanche(novoLanche);

				// Aplicar descontos de promoções
				valorLanche = valorLanche - aplicarPromocoes(lanche, ingredientes, valorLanche, lancheCompra.getCompra());
				System.out.println("Valor do lanche após promoções: R$" + valorLanche + "\n\n");
				
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
			System.out.println("Lanche já existe: " + lanche.getId());
			lancheCompra.setLanche(lanche);
			valorFinal += valorDoLanche(lanche, ingredientes, lancheCompra.getCompra()) * lancheCompra.getQuantidade();
		}

		System.out.println("\n\nValor final da CompraLanche:  R$" + valorFinal);

		return valorFinal;
	}

	public double valorDoLanche(Lanche lanche, List<Ingrediente> ingredientes, Compra compra) {
		return lanche.getPreco() - aplicarPromocoes(lanche, ingredientes, lanche.getPreco(), compra);
	}

	public double aplicarPromocoes(Lanche lanche, List<Ingrediente> ingredientes, double valorAtual, Compra compra) {

		double valorDesconto = 0;
		
		List<Promocao> promocoes = promocaoService.listarPromocoes();
		
		System.out.println("Listou as promoções: "+promocoes.size());

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
			System.out.println(
					"Iteração do LancheIngrediente das promoções: " + lancheIngrediente.getIngrediente().getNome() + "\nID: "+lancheIngrediente.getIngrediente().getId());

			if (lancheIngrediente.getIngrediente().getId() == ingredienteService.verIngredientePorNome("Alface")
					.getId())
				temAlface = true;

			if (lancheIngrediente.getIngrediente().getId() == ingredienteService.verIngredientePorNome("Bacon").getId())
				temBacon = true;

			if (lancheIngrediente.getIngrediente().getId() == ingredienteService
					.verIngredientePorNome("Hambúrguer de carne").getId()) {
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
			System.out.println("Descontou 10% do valor pela Promoção Light!");
			
			boolean incrementou = false;
			//Procura a promoção no array de promoções
			for(Promocao promocao : promocoes) {
				System.out.println("Listando a promoção "+promocao.getNome());
				//Caso ache, verifica se ela já foi aplicada à compra
				if(promocao.getNome().equals("Light")) {
					System.out.println("Está na promoção light");
					if(compra.getPromocoesCompra().size() > 0) {
						System.out.println("Tem promoçoes na compra");
						for(PromocaoCompra promocaoCompra : compra.getPromocoesCompra()) {
							//Se ja foi, só incrementa o valor que foi descontado 
							if(promocaoCompra.getPromocao().getNome().equals("Light")) {
								System.out.println("A promoção ja foi aplicada à compra");
								promocaoCompra.setDesconto(promocaoCompra.getDesconto() + valorDesconto);
								incrementou = true;
							}
							
						}
					}
					//Caso a promoção não tenha sido aplicada à compra ainda, aplica
					System.out.println("Chegou no incrementou == false");
					if(incrementou == false) {
						System.out.println("A promoção não foi aplicada à compra");
						PromocaoCompra promocaoCompra = new PromocaoCompra(promocao, compra, valorDesconto);
						compra.getPromocoesCompra().add(promocaoCompra);
						System.out.println("Promoção adicionada ao array de promocoes"+compra.getPromocoesCompra().get(0).getPromocao().getNome());
					}
				}
			}
		}

		// Muita carne
		if (porcoesCarneDescontadas > 0) {
			for (Ingrediente ingrediente : ingredientes) {
				if (ingrediente.getNome().equals("Hambúrguer de carne")) {
					valorDesconto += porcoesCarneDescontadas * ingrediente.getPreco();
					System.out.println("Descontou R$" + porcoesCarneDescontadas * ingrediente.getPreco()
							+ " pela promoção de Muita carne!");
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
					System.out.println("Descontou R$" + porcoesQueijoDescontadas * ingrediente.getPreco()
							+ " pela promoção de Muito queijo!");
					
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
