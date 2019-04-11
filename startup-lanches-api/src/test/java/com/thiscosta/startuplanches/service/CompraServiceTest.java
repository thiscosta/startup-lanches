package com.thiscosta.startuplanches.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.ArgumentMatchers.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.thiscosta.startuplanches.model.Compra;
import com.thiscosta.startuplanches.model.CompraLanche;
import com.thiscosta.startuplanches.model.Ingrediente;
import com.thiscosta.startuplanches.model.Lanche;
import com.thiscosta.startuplanches.model.LancheIngrediente;
import com.thiscosta.startuplanches.model.Promocao;
import com.thiscosta.startuplanches.model.PromocaoCompra;
import com.thiscosta.startuplanches.repository.CompraRepository;

@RunWith(SpringRunner.class)
public class CompraServiceTest {
	
	@InjectMocks
	CompraService compraService;

	@Mock
	CompraRepository repository;

	@Mock
	IngredienteService ingredienteService;

	@Mock
	LancheService lancheService;

	@Mock
	LancheIngredienteService lancheIngredienteService;

	@Mock
	CompraLancheService compraLancheService;
	
	@Mock
	PromocaoService promocaoService;
	
	@Mock
	PromocaoCompraService promocaoCompraService;
	
	private static List<Promocao> promocoes;
	
	private static List<Ingrediente> ingredientes;

    @BeforeClass
    public static void setUpGlobal() throws Exception {
    	
    	//Cria as promoções
    	promocoes = new ArrayList<Promocao>();
    	promocoes.add(new Promocao(Long.valueOf(1), "Light","Se o lanche tem alface e não tem bacon, ganha 10% de desconto." ));
    	promocoes.add(new Promocao(Long.valueOf(2), "Muita carne","A cada 3 porções de carne o cliente só paga 2" ));
    	promocoes.add(new Promocao(Long.valueOf(3), "Muito queijo","A cada 3 porções de queijo o cliente só paga 2." ));
    	
    	//Cria os ingredientes
    	ingredientes = new ArrayList<Ingrediente>();
        
        Ingrediente alface = new Ingrediente(Long.valueOf(1),"Alface", 0.4);
        Ingrediente bacon = new Ingrediente(Long.valueOf(2),"Bacon", 2);
        Ingrediente hamburguer = new Ingrediente(Long.valueOf(3),"Hamburguer de carne", 3);
        Ingrediente queijo = new Ingrediente(Long.valueOf(4),"Queijo", 1.5);
        Ingrediente ovo = new Ingrediente(Long.valueOf(5), "Ovo", 0.8);
        
        ingredientes.add(alface);
        ingredientes.add(bacon);
        ingredientes.add(hamburguer);
        ingredientes.add(queijo);
        ingredientes.add(ovo);
    }
    
    @Before
    public void setUp() throws Exception {
    	MockitoAnnotations.initMocks(this);
    	
        compraService = new CompraService(
        		repository, ingredienteService, lancheService, 
        		lancheIngredienteService, compraLancheService, promocaoService,
        		promocaoCompraService
        );
        
        when(ingredienteService.listarIngredientes()).thenReturn(ingredientes);
        when(promocaoService.listarPromocoes()).thenReturn(promocoes);   
        
        when(ingredienteService.verIngredientePorNome("Alface")).thenReturn(new Ingrediente(Long.valueOf(1),"Alface", 0.4));
        when(ingredienteService.verIngredientePorNome("Bacon")).thenReturn(new Ingrediente(Long.valueOf(2),"Bacon", 2));
        when(ingredienteService.verIngredientePorNome("Hamburguer de carne")).thenReturn(new Ingrediente(Long.valueOf(3),"Hamburguer de carne", 3));
        when(ingredienteService.verIngredientePorNome("Queijo")).thenReturn(new Ingrediente(Long.valueOf(4),"Queijo", 1.5));
        when(ingredienteService.verIngredientePorNome("Ovo")).thenReturn(new Ingrediente(Long.valueOf(5),"Ovo", 0.8));

    }

    @Test
    public void testeXBacon() throws Exception {

        //Lanches
        Lanche lanche = new Lanche("X-Bacon", 6.5);
        
        List<LancheIngrediente> ingredientesLanche = new ArrayList<LancheIngrediente>();
        ingredientesLanche.add(new LancheIngrediente(lanche, ingredientes.get(1), 1));
        ingredientesLanche.add(new LancheIngrediente(lanche, ingredientes.get(2), 1));
        ingredientesLanche.add(new LancheIngrediente(lanche, ingredientes.get(3), 1));

        lanche.setIngredientes(ingredientesLanche);
        
        //Compra
        Compra compra = new Compra();
        
        compra.setPromocoesCompra(new ArrayList<PromocaoCompra>());

        //CompraLanche
        List<CompraLanche> lanchesCompra = new ArrayList<CompraLanche>();
        lanchesCompra.add(new CompraLanche(compra, lanche, 4));
        
        compra.setLanchesCompra(lanchesCompra);
        
        //Fake data
        when(lancheService.novoLanche(new Lanche(lanche.getNome(), lanche.getPreco()))).thenReturn(new Lanche(Long.valueOf(1), lanche.getNome(), lanche.getPreco())); 
        when(lancheIngredienteService.novoLancheIngrediente(
        		new LancheIngrediente(new Lanche(Long.valueOf(1), lanche.getNome(), lanche.getPreco()), ingredientes.get(1), 1)
        			)).thenReturn(new LancheIngrediente(Long.valueOf(1), new Lanche(Long.valueOf(1), lanche.getNome(), lanche.getPreco()), ingredientes.get(1), 1));
        
        when(lancheIngredienteService.novoLancheIngrediente(
        		new LancheIngrediente(new Lanche(Long.valueOf(1), lanche.getNome(), lanche.getPreco()), ingredientes.get(2), 1)
        			)).thenReturn(new LancheIngrediente(Long.valueOf(2), new Lanche(Long.valueOf(1), lanche.getNome(), lanche.getPreco()), ingredientes.get(2), 1));
        
        when(lancheIngredienteService.novoLancheIngrediente(
        		new LancheIngrediente(new Lanche(Long.valueOf(1), lanche.getNome(), lanche.getPreco()), ingredientes.get(3), 1)
        			)).thenReturn(new LancheIngrediente(Long.valueOf(3), new Lanche(Long.valueOf(1), lanche.getNome(), lanche.getPreco()), ingredientes.get(3), 1));
        
        when(repository.save(notNull())).thenReturn(new Compra(Long.valueOf(1), lanche.getPreco() * compra.getLanchesCompra().get(0).getQuantidade(), compra.getLanchesCompra(), new ArrayList<PromocaoCompra>()));
        when(compraLancheService.novaCompraLanche(notNull())).thenReturn(
        		new CompraLanche(
        				Long.valueOf(1),
        				new Compra(Long.valueOf(1), lanche.getPreco() * compra.getLanchesCompra().get(0).getQuantidade(), compra.getLanchesCompra()), 
        				new Lanche(Long.valueOf(1), lanche.getNome(), lanche.getPreco()), 
        				4
        		)
        );
        when(promocaoCompraService.novaPromocaoCompra(notNull())).thenReturn(
        		new PromocaoCompra(
        				Long.valueOf(1),
        				promocoes.get(0),
        				new Compra(Long.valueOf(1), lanche.getPreco() * compra.getLanchesCompra().get(0).getQuantidade(), compra.getLanchesCompra()),
        				0
        		)
        );     
        when(repository.getOne(notNull())).thenReturn(
        		new Compra(
        				Long.valueOf(1), 
        				lanche.getPreco() * compra.getLanchesCompra().get(0).getQuantidade(), 
        				compra.getLanchesCompra(), 
        				new ArrayList<PromocaoCompra>()
        		)
        );
        
        ResponseEntity response = compraService.novaCompra(compra);
        Compra novaCompra = (Compra) response.getBody();
        
        //PREÇO = Queijo (R$1,50) + Hamburguer(R$3,00) + Bacon(R$2,00) = R$6,50
        assertEquals(novaCompra.getLanchesCompra().get(0).getLanche().getPreco(), lanche.getPreco(), 0.1 );
        
        //PREÇO DO PEDIDO = PREÇO DO LANCHE (R$6,50) * QUANTIDADE NA COMPRA(4) = 26.0
        assertEquals(
        		novaCompra.getLanchesCompra().get(0).getLanche().getPreco() * novaCompra.getLanchesCompra().get(0).getQuantidade(),
        		lanche.getPreco() * compra.getLanchesCompra().get(0).getQuantidade(),
        		0.1
        );
    }
    
    @Test
    public void testeXBurguer() throws Exception {

    	//Lanches
        Lanche lanche = new Lanche("X-Burguer", 4.5);
        
        List<LancheIngrediente> ingredientesLanche = new ArrayList<LancheIngrediente>();
        ingredientesLanche.add(new LancheIngrediente(lanche, ingredientes.get(2), 1));
        ingredientesLanche.add(new LancheIngrediente(lanche, ingredientes.get(3), 1));

        lanche.setIngredientes(ingredientesLanche);
        
        //Compra
        Compra compra = new Compra();
        
        compra.setPromocoesCompra(new ArrayList<PromocaoCompra>());

        //CompraLanche
        List<CompraLanche> lanchesCompra = new ArrayList<CompraLanche>();
        lanchesCompra.add(new CompraLanche(compra, lanche, 3));
        
        compra.setLanchesCompra(lanchesCompra);
        
        //Fake data
        when(lancheService.novoLanche(new Lanche(lanche.getNome(), lanche.getPreco()))).thenReturn(new Lanche(Long.valueOf(1), lanche.getNome(), lanche.getPreco())); 
        when(lancheIngredienteService.novoLancheIngrediente(
        		new LancheIngrediente(new Lanche(Long.valueOf(1), lanche.getNome(), lanche.getPreco()), ingredientes.get(2), 1)
        			)).thenReturn(new LancheIngrediente(Long.valueOf(1), new Lanche(Long.valueOf(1), lanche.getNome(), lanche.getPreco()), ingredientes.get(2), 1));
        
        when(lancheIngredienteService.novoLancheIngrediente(
        		new LancheIngrediente(new Lanche(Long.valueOf(1), lanche.getNome(), lanche.getPreco()), ingredientes.get(3), 1)
        			)).thenReturn(new LancheIngrediente(Long.valueOf(2), new Lanche(Long.valueOf(1), lanche.getNome(), lanche.getPreco()), ingredientes.get(3), 1));
        
        when(repository.save(notNull())).thenReturn(new Compra(Long.valueOf(1), lanche.getPreco() * compra.getLanchesCompra().get(0).getQuantidade(), compra.getLanchesCompra(), new ArrayList<PromocaoCompra>()));
        when(compraLancheService.novaCompraLanche(notNull())).thenReturn(
        		new CompraLanche(
        				Long.valueOf(1),
        				new Compra(Long.valueOf(1), lanche.getPreco() * compra.getLanchesCompra().get(0).getQuantidade(), compra.getLanchesCompra()), 
        				new Lanche(Long.valueOf(1), lanche.getNome(), lanche.getPreco()), 
        				3
        		)
        );
        when(promocaoCompraService.novaPromocaoCompra(notNull())).thenReturn(
        		new PromocaoCompra(
        				Long.valueOf(1),
        				promocoes.get(0),
        				new Compra(Long.valueOf(1), lanche.getPreco() * compra.getLanchesCompra().get(0).getQuantidade(), compra.getLanchesCompra()),
        				0
        		)
        );     
        when(repository.getOne(notNull())).thenReturn(
        		new Compra(
        				Long.valueOf(1), 
        				lanche.getPreco() * compra.getLanchesCompra().get(0).getQuantidade(), 
        				compra.getLanchesCompra(), 
        				new ArrayList<PromocaoCompra>()
        		)
        );
        
        ResponseEntity response = compraService.novaCompra(compra);
        Compra novaCompra = (Compra) response.getBody();
        
        //PREÇO = Queijo (R$1,50) + Hamburguer(R$3,00) = R$4,50
        assertEquals(novaCompra.getLanchesCompra().get(0).getLanche().getPreco(), lanche.getPreco(), 0.1 );
        
        //PREÇO DO PEDIDO = PREÇO DO LANCHE (R$4,50) * QUANTIDADE NA COMPRA(3) = R$13,50
        assertEquals(
        		novaCompra.getLanchesCompra().get(0).getLanche().getPreco() * novaCompra.getLanchesCompra().get(0).getQuantidade(),
        		lanche.getPreco() * compra.getLanchesCompra().get(0).getQuantidade(),
        		0.1
        );
    }
    
    @Test
    public void testeXEgg() throws Exception {

        //Lanches
        Lanche lanche = new Lanche("X-Egg", 5.3);
        
        List<LancheIngrediente> ingredientesLanche = new ArrayList<LancheIngrediente>();
        ingredientesLanche.add(new LancheIngrediente(lanche, ingredientes.get(2), 1));
        ingredientesLanche.add(new LancheIngrediente(lanche, ingredientes.get(3), 1));
        ingredientesLanche.add(new LancheIngrediente(lanche, ingredientes.get(4), 1));

        lanche.setIngredientes(ingredientesLanche);
        
        //Compra
        Compra compra = new Compra();
        
        compra.setPromocoesCompra(new ArrayList<PromocaoCompra>());

        //CompraLanche
        List<CompraLanche> lanchesCompra = new ArrayList<CompraLanche>();
        lanchesCompra.add(new CompraLanche(compra, lanche, 2));
        
        compra.setLanchesCompra(lanchesCompra);
        
        //Fake data
        when(lancheService.novoLanche(new Lanche(lanche.getNome(), lanche.getPreco()))).thenReturn(new Lanche(Long.valueOf(1), lanche.getNome(), lanche.getPreco())); 
        
        when(lancheIngredienteService.novoLancheIngrediente(
        		new LancheIngrediente(new Lanche(Long.valueOf(1), lanche.getNome(), lanche.getPreco()), ingredientes.get(2), 1)
        			)).thenReturn(new LancheIngrediente(Long.valueOf(1), new Lanche(Long.valueOf(1), lanche.getNome(), lanche.getPreco()), ingredientes.get(2), 1));
        
        when(lancheIngredienteService.novoLancheIngrediente(
        		new LancheIngrediente(new Lanche(Long.valueOf(1), lanche.getNome(), lanche.getPreco()), ingredientes.get(3), 1)
        			)).thenReturn(new LancheIngrediente(Long.valueOf(2), new Lanche(Long.valueOf(1), lanche.getNome(), lanche.getPreco()), ingredientes.get(3), 1));
        
        when(lancheIngredienteService.novoLancheIngrediente(
        		new LancheIngrediente(new Lanche(Long.valueOf(1), lanche.getNome(), lanche.getPreco()), ingredientes.get(4), 1)
        			)).thenReturn(new LancheIngrediente(Long.valueOf(2), new Lanche(Long.valueOf(1), lanche.getNome(), lanche.getPreco()), ingredientes.get(4), 1));
        
        when(repository.save(notNull())).thenReturn(new Compra(Long.valueOf(1), lanche.getPreco() * compra.getLanchesCompra().get(0).getQuantidade(), compra.getLanchesCompra(), new ArrayList<PromocaoCompra>()));
        when(compraLancheService.novaCompraLanche(notNull())).thenReturn(
        		new CompraLanche(
        				Long.valueOf(1),
        				new Compra(Long.valueOf(1), lanche.getPreco() * compra.getLanchesCompra().get(0).getQuantidade(), compra.getLanchesCompra()), 
        				new Lanche(Long.valueOf(1), lanche.getNome(), lanche.getPreco()), 
        				2
        		)
        );
        when(promocaoCompraService.novaPromocaoCompra(notNull())).thenReturn(
        		new PromocaoCompra(
        				Long.valueOf(1),
        				promocoes.get(0),
        				new Compra(Long.valueOf(1), lanche.getPreco() * compra.getLanchesCompra().get(0).getQuantidade(), compra.getLanchesCompra()),
        				0
        		)
        );     
        when(repository.getOne(notNull())).thenReturn(
        		new Compra(
        				Long.valueOf(1), 
        				lanche.getPreco() * compra.getLanchesCompra().get(0).getQuantidade(), 
        				compra.getLanchesCompra(), 
        				new ArrayList<PromocaoCompra>()
        		)
        );
        
        ResponseEntity response = compraService.novaCompra(compra);
        Compra novaCompra = (Compra) response.getBody();
        
        //PREÇO = Queijo (R$1,50) + Hamburguer(R$3,00) + Ovo(R$0.80) = R$5,30
        assertEquals(novaCompra.getLanchesCompra().get(0).getLanche().getPreco(), lanche.getPreco(), 0.1 );
        
        //PREÇO DO PEDIDO = PREÇO DO LANCHE (R$5,30) * QUANTIDADE NA COMPRA(2) = R$10,60
        assertEquals(
        		novaCompra.getLanchesCompra().get(0).getLanche().getPreco() * novaCompra.getLanchesCompra().get(0).getQuantidade(),
        		lanche.getPreco() * compra.getLanchesCompra().get(0).getQuantidade(),
        		0.1
        );
    }

    @Test
    public void testeXEggBacon() throws Exception {

        //Lanches
        Lanche lanche = new Lanche("X-Egg Bacon", 7.3);
        
        List<LancheIngrediente> ingredientesLanche = new ArrayList<LancheIngrediente>();
        ingredientesLanche.add(new LancheIngrediente(lanche, ingredientes.get(1), 1));
        ingredientesLanche.add(new LancheIngrediente(lanche, ingredientes.get(2), 1));
        ingredientesLanche.add(new LancheIngrediente(lanche, ingredientes.get(3), 1));
        ingredientesLanche.add(new LancheIngrediente(lanche, ingredientes.get(4), 1));

        lanche.setIngredientes(ingredientesLanche);
        
        //Compra
        Compra compra = new Compra();
        
        compra.setPromocoesCompra(new ArrayList<PromocaoCompra>());

        //CompraLanche
        List<CompraLanche> lanchesCompra = new ArrayList<CompraLanche>();
        lanchesCompra.add(new CompraLanche(compra, lanche, 5));
        
        compra.setLanchesCompra(lanchesCompra);
        
        //Fake data
        when(lancheService.novoLanche(new Lanche(lanche.getNome(), lanche.getPreco()))).thenReturn(new Lanche(Long.valueOf(1), lanche.getNome(), lanche.getPreco())); 
        
        when(lancheIngredienteService.novoLancheIngrediente(
        		new LancheIngrediente(new Lanche(Long.valueOf(1), lanche.getNome(), lanche.getPreco()), ingredientes.get(1), 1)
        			)).thenReturn(new LancheIngrediente(Long.valueOf(2), new Lanche(Long.valueOf(1), lanche.getNome(), lanche.getPreco()), ingredientes.get(1), 1));
       
        when(lancheIngredienteService.novoLancheIngrediente(
        		new LancheIngrediente(new Lanche(Long.valueOf(1), lanche.getNome(), lanche.getPreco()), ingredientes.get(2), 1)
        			)).thenReturn(new LancheIngrediente(Long.valueOf(1), new Lanche(Long.valueOf(1), lanche.getNome(), lanche.getPreco()), ingredientes.get(2), 1));
        
        when(lancheIngredienteService.novoLancheIngrediente(
        		new LancheIngrediente(new Lanche(Long.valueOf(1), lanche.getNome(), lanche.getPreco()), ingredientes.get(3), 1)
        			)).thenReturn(new LancheIngrediente(Long.valueOf(2), new Lanche(Long.valueOf(1), lanche.getNome(), lanche.getPreco()), ingredientes.get(3), 1));
        
        when(lancheIngredienteService.novoLancheIngrediente(
        		new LancheIngrediente(new Lanche(Long.valueOf(1), lanche.getNome(), lanche.getPreco()), ingredientes.get(4), 1)
        			)).thenReturn(new LancheIngrediente(Long.valueOf(2), new Lanche(Long.valueOf(1), lanche.getNome(), lanche.getPreco()), ingredientes.get(4), 1));
        
         
        when(repository.save(notNull())).thenReturn(new Compra(Long.valueOf(1), lanche.getPreco() * compra.getLanchesCompra().get(0).getQuantidade(), compra.getLanchesCompra(), new ArrayList<PromocaoCompra>()));
        when(compraLancheService.novaCompraLanche(notNull())).thenReturn(
        		new CompraLanche(
        				Long.valueOf(1),
        				new Compra(Long.valueOf(1), lanche.getPreco() * compra.getLanchesCompra().get(0).getQuantidade(), compra.getLanchesCompra()), 
        				new Lanche(Long.valueOf(1), lanche.getNome(), lanche.getPreco()), 
        				5
        		)
        );
        when(promocaoCompraService.novaPromocaoCompra(notNull())).thenReturn(
        		new PromocaoCompra(
        				Long.valueOf(1),
        				promocoes.get(0),
        				new Compra(Long.valueOf(1), lanche.getPreco() * compra.getLanchesCompra().get(0).getQuantidade(), compra.getLanchesCompra()),
        				0
        		)
        );     
        when(repository.getOne(notNull())).thenReturn(
        		new Compra(
        				Long.valueOf(1), 
        				lanche.getPreco() * compra.getLanchesCompra().get(0).getQuantidade(), 
        				compra.getLanchesCompra(), 
        				new ArrayList<PromocaoCompra>()
        		)
        );
        
        ResponseEntity response = compraService.novaCompra(compra);
        Compra novaCompra = (Compra) response.getBody();
        
        //PREÇO = Queijo (R$1,50) + Hamburguer(R$3,00) + Ovo(R$0.80) + Bacon (R$2,00) = R$7,30
        assertEquals(novaCompra.getLanchesCompra().get(0).getLanche().getPreco(), lanche.getPreco(), 0.1 );
        
        //PREÇO DO PEDIDO = PREÇO DO LANCHE (R$7,30) * QUANTIDADE NA COMPRA(5) = R$36,50
        assertEquals(
        		novaCompra.getLanchesCompra().get(0).getLanche().getPreco() * novaCompra.getLanchesCompra().get(0).getQuantidade(),
        		lanche.getPreco() * compra.getLanchesCompra().get(0).getQuantidade(),
        		0.1
        );
    }
    
    @Test
    public void testePromocaoLight() throws Exception {

        //Lanches
        Lanche lanche = new Lanche("X-Promocao Light", 0.4);
        
        List<LancheIngrediente> ingredientesLanche = new ArrayList<LancheIngrediente>();
        ingredientesLanche.add(new LancheIngrediente(lanche, ingredientes.get(0), 1));

        lanche.setIngredientes(ingredientesLanche);
        
        //Compra
        Compra compra = new Compra();
        
        compra.setPromocoesCompra(new ArrayList<PromocaoCompra>());

        //CompraLanche
        List<CompraLanche> lanchesCompra = new ArrayList<CompraLanche>();
        lanchesCompra.add(new CompraLanche(compra, lanche, 2));
      
        
        
        compra.setLanchesCompra(lanchesCompra);
        
        //Fake data
        when(lancheService.novoLanche(new Lanche(lanche.getNome(), lanche.getPreco()))).thenReturn(new Lanche(Long.valueOf(1), lanche.getNome(), lanche.getPreco())); 
        
        when(lancheIngredienteService.novoLancheIngrediente(
        		new LancheIngrediente(new Lanche(Long.valueOf(1), lanche.getNome(), lanche.getPreco()), ingredientes.get(0), 1)
        			)).thenReturn(new LancheIngrediente(Long.valueOf(2), new Lanche(Long.valueOf(1), lanche.getNome(), lanche.getPreco()), ingredientes.get(0), 1));
       
        
        List<PromocaoCompra> promocoesCompra = new ArrayList<PromocaoCompra>();
        
        promocoesCompra.add(new PromocaoCompra(
        		Long.valueOf(1), 
        		promocoes.get(0),
        		new Compra(Long.valueOf(1), lanche.getPreco() * lanchesCompra.get(0).getQuantidade(), compra.getLanchesCompra()), 
        		(new Lanche(Long.valueOf(1), lanche.getNome(), lanche.getPreco()).getPreco() / 10) * lanchesCompra.get(0).getQuantidade()
        ));
        
        
        
        when(repository.save(notNull())).thenReturn(new Compra(Long.valueOf(1), lanche.getPreco() * 2, compra.getLanchesCompra(), promocoesCompra));
        
        when(compraLancheService.novaCompraLanche(notNull())).thenReturn(
        		new CompraLanche(
        				Long.valueOf(1),
        				new Compra(Long.valueOf(1), lanche.getPreco() * lanchesCompra.get(0).getQuantidade(), compra.getLanchesCompra()), 
        				new Lanche(Long.valueOf(1), lanche.getNome(), lanche.getPreco()), 
        				lanchesCompra.get(0).getQuantidade()
        		)
        );
        when(promocaoCompraService.novaPromocaoCompra(notNull())).thenReturn(
        		new PromocaoCompra(
        				Long.valueOf(1),
        				promocoes.get(0),
        				new Compra(Long.valueOf(1), lanche.getPreco() * 2, compra.getLanchesCompra()),
        				(new Lanche(Long.valueOf(1), lanche.getNome(), lanche.getPreco()).getPreco() / 10) * lanchesCompra.get(0).getQuantidade()
        		)
        );  
        
        System.out.println((new Lanche(Long.valueOf(1), lanche.getNome(), lanche.getPreco()).getPreco() / 10));
        
        when(repository.getOne(notNull())).thenReturn(
        		new Compra(
        				Long.valueOf(1), 
        				lanche.getPreco() * lanchesCompra.get(0).getQuantidade(), 
        				compra.getLanchesCompra(), 
        				promocoesCompra
        		)
        );
        
        ResponseEntity response = compraService.novaCompra(compra);
        Compra novaCompra = (Compra) response.getBody();
        
        //PREÇO = Alface (R$0,40) = R$0,40
        assertEquals(novaCompra.getLanchesCompra().get(0).getLanche().getPreco(), lanche.getPreco(), 0.1 );
        
        //PREÇO DO PEDIDO = PREÇO DO LANCHE (R$0,4) * QUANTIDADE NA COMPRA(2) = R$0,80
        assertEquals(
        		novaCompra.getLanchesCompra().get(0).getLanche().getPreco() * novaCompra.getLanchesCompra().get(0).getQuantidade(),
        		lanche.getPreco() * lanchesCompra.get(0).getQuantidade(),
        		0.01
        );
        
        //VERIFICA SE A PROMOCÃO FOI APLICADA À COMPRA
        assertEquals(
        		novaCompra.getPromocoesCompra().get(0).getPromocao().getNome(),
        		promocoes.get(0).getNome()
        );
        
        //VALOR FINAL DO PEDIDO COM DESCONTOS = R$0,36 * 2 = R$0,72
        assertEquals(
        	novaCompra.getPreco() - novaCompra.getPromocoesCompra().get(0).getDesconto(),
        	(lanche.getPreco() * lanchesCompra.get(0).getQuantidade()) - compra.getPromocoesCompra().get(0).getDesconto() * lanchesCompra.get(0).getQuantidade(),
        	0.01	
        );
    }    

    @Test
    public void testePromocaoMuitaCarne() throws Exception {

        //Lanches
        Lanche lanche = new Lanche("X-Muita carne", ingredientes.get(2).getPreco() * 6);
        
        List<LancheIngrediente> ingredientesLanche = new ArrayList<LancheIngrediente>();
        ingredientesLanche.add(new LancheIngrediente(lanche, ingredientes.get(2), 6));

        lanche.setIngredientes(ingredientesLanche);
        
        //Compra
        Compra compra = new Compra();
        
        compra.setPromocoesCompra(new ArrayList<PromocaoCompra>());

        //CompraLanche
        List<CompraLanche> lanchesCompra = new ArrayList<CompraLanche>();
        lanchesCompra.add(new CompraLanche(compra, lanche, 1));
      
        
        
        compra.setLanchesCompra(lanchesCompra);
        
        //Fake data
        when(lancheService.novoLanche(new Lanche(lanche.getNome(), ingredientes.get(2).getPreco() * 6))).thenReturn(new Lanche(Long.valueOf(1), lanche.getNome(), ingredientes.get(2).getPreco() * 6)); 
        
        when(lancheIngredienteService.novoLancheIngrediente(
        		new LancheIngrediente(new Lanche(Long.valueOf(1), lanche.getNome(), ingredientes.get(2).getPreco() * 6), ingredientes.get(2), 6)
        			)).thenReturn(new LancheIngrediente(Long.valueOf(1), new Lanche(Long.valueOf(1), lanche.getNome(), ingredientes.get(2).getPreco() * 6), ingredientes.get(2), 6));
       
        
        List<PromocaoCompra> promocoesCompra = new ArrayList<PromocaoCompra>();
        
        promocoesCompra.add(new PromocaoCompra(
        		Long.valueOf(1), 
        		promocoes.get(1),
        		new Compra(Long.valueOf(1), lanche.getPreco() * lanchesCompra.get(0).getQuantidade(), compra.getLanchesCompra()), 
        		ingredientes.get(2).getPreco() * 2
        ));
        
        
        
        when(repository.save(notNull())).thenReturn(new Compra(Long.valueOf(1), lanche.getPreco() * lanchesCompra.get(0).getQuantidade(), compra.getLanchesCompra(), promocoesCompra));
        
        when(compraLancheService.novaCompraLanche(notNull())).thenReturn(
        		new CompraLanche(
        				Long.valueOf(1),
        				new Compra(Long.valueOf(1), lanche.getPreco() * 1, compra.getLanchesCompra()), 
        				new Lanche(Long.valueOf(1), lanche.getNome(), ingredientes.get(2).getPreco() * 6), 
        				lanchesCompra.get(0).getQuantidade()
        		)
        );
        when(promocaoCompraService.novaPromocaoCompra(notNull())).thenReturn(
        		new PromocaoCompra(
        				Long.valueOf(1),
        				promocoes.get(1),
        				new Compra(Long.valueOf(1), lanche.getPreco() * lanchesCompra.get(0).getQuantidade(), compra.getLanchesCompra()),
        				ingredientes.get(2).getPreco() * 2
        		)
        );  
                
        when(repository.getOne(notNull())).thenReturn(
        		new Compra(
        				Long.valueOf(1), 
        				lanche.getPreco() * lanchesCompra.get(0).getQuantidade(), 
        				compra.getLanchesCompra(), 
        				promocoesCompra
        		)
        );
        
        ResponseEntity response = compraService.novaCompra(compra);
        Compra novaCompra = (Compra) response.getBody();
        
        //PREÇO = 6 Hamburguers de carnes (R$3,00) = R$18,00
        assertEquals(novaCompra.getLanchesCompra().get(0).getLanche().getPreco(), 18, 0.1 );
        
        //PREÇO DO PEDIDO = PREÇO DO LANCHE (R$18,00) * QUANTIDADE NA COMPRA(1) = R$18,00
        assertEquals(
        		novaCompra.getLanchesCompra().get(0).getLanche().getPreco() * novaCompra.getLanchesCompra().get(0).getQuantidade(),
        		lanche.getPreco() * compra.getLanchesCompra().get(0).getQuantidade(),
        		0.01
        );
        
        //VERIFICA SE A PROMOCÃO FOI APLICADA À COMPRA
        assertEquals(
        		novaCompra.getPromocoesCompra().get(0).getPromocao().getNome(),
        		promocoes.get(1).getNome()
        );
        
        //VALOR FINAL DO PEDIDO COM DESCONTOS = R$12,00 * 1 = R$12,00
        assertEquals(
        	novaCompra.getPreco() - novaCompra.getPromocoesCompra().get(0).getDesconto(),
        	(lanche.getPreco() * compra.getLanchesCompra().get(0).getQuantidade()) - (ingredientes.get(2).getPreco() * 2),
        	0.01	
        );
    }
    
    @Test
    public void testePromocaoMuitaQueijo() throws Exception {

        //Lanches
        Lanche lanche = new Lanche("X-Muito queijo", ingredientes.get(3).getPreco() * 6);
        
        List<LancheIngrediente> ingredientesLanche = new ArrayList<LancheIngrediente>();
        ingredientesLanche.add(new LancheIngrediente(lanche, ingredientes.get(3), 6));

        lanche.setIngredientes(ingredientesLanche);
        
        //Compra
        Compra compra = new Compra();
        
        compra.setPromocoesCompra(new ArrayList<PromocaoCompra>());

        //CompraLanche
        List<CompraLanche> lanchesCompra = new ArrayList<CompraLanche>();
        lanchesCompra.add(new CompraLanche(compra, lanche, 1));
      
        
        
        compra.setLanchesCompra(lanchesCompra);
        
        //Fake data
        when(lancheService.novoLanche(new Lanche(lanche.getNome(), ingredientes.get(3).getPreco() * 6))).thenReturn(new Lanche(Long.valueOf(1), lanche.getNome(), ingredientes.get(3).getPreco() * 6)); 
        
        when(lancheIngredienteService.novoLancheIngrediente(
        		new LancheIngrediente(new Lanche(Long.valueOf(1), lanche.getNome(), ingredientes.get(3).getPreco() * 6), ingredientes.get(3), 6)
        			)).thenReturn(new LancheIngrediente(Long.valueOf(1), new Lanche(Long.valueOf(1), lanche.getNome(), ingredientes.get(3).getPreco() * 6), ingredientes.get(3), 6));
       
        
        List<PromocaoCompra> promocoesCompra = new ArrayList<PromocaoCompra>();
        
        promocoesCompra.add(new PromocaoCompra(
        		Long.valueOf(1), 
        		promocoes.get(2),
        		new Compra(Long.valueOf(1), lanche.getPreco() * lanchesCompra.get(0).getQuantidade(), compra.getLanchesCompra()), 
        		ingredientes.get(3).getPreco() * 2
        ));
        
        
        
        when(repository.save(notNull())).thenReturn(new Compra(Long.valueOf(1), lanche.getPreco() * lanchesCompra.get(0).getQuantidade(), compra.getLanchesCompra(), promocoesCompra));
        
        when(compraLancheService.novaCompraLanche(notNull())).thenReturn(
        		new CompraLanche(
        				Long.valueOf(1),
        				new Compra(Long.valueOf(1), lanche.getPreco() * 1, compra.getLanchesCompra()), 
        				new Lanche(Long.valueOf(1), lanche.getNome(), ingredientes.get(3).getPreco() * 6), 
        				lanchesCompra.get(0).getQuantidade()
        		)
        );
        when(promocaoCompraService.novaPromocaoCompra(notNull())).thenReturn(
        		new PromocaoCompra(
        				Long.valueOf(1),
        				promocoes.get(2),
        				new Compra(Long.valueOf(1), lanche.getPreco() * lanchesCompra.get(0).getQuantidade(), compra.getLanchesCompra()),
        				ingredientes.get(3).getPreco() * 2
        		)
        );  
                
        when(repository.getOne(notNull())).thenReturn(
        		new Compra(
        				Long.valueOf(1), 
        				lanche.getPreco() * lanchesCompra.get(0).getQuantidade(), 
        				compra.getLanchesCompra(), 
        				promocoesCompra
        		)
        );
        
        ResponseEntity response = compraService.novaCompra(compra);
        Compra novaCompra = (Compra) response.getBody();
        
        //PREÇO = 6 Queijos (R$1,50) = R$9,00
        assertEquals(
        		novaCompra.getLanchesCompra().get(0).getLanche().getPreco(), 
        		ingredientes.get(3).getPreco() * 6, 
        		0.1 
        );
        
        //PREÇO DO PEDIDO = PREÇO DO LANCHE (R$9,00) * QUANTIDADE NA COMPRA(1) = R$9,00
        assertEquals(
        		novaCompra.getLanchesCompra().get(0).getLanche().getPreco() * novaCompra.getLanchesCompra().get(0).getQuantidade(),
        		(ingredientes.get(3).getPreco() * 6) * compra.getLanchesCompra().get(0).getQuantidade(),
        		0.01
        );
        
        //VERIFICA SE A PROMOCÃO FOI APLICADA À COMPRA
        assertEquals(
        		novaCompra.getPromocoesCompra().get(0).getPromocao().getNome(),
        		promocoes.get(2).getNome()
        );
        
        //VALOR FINAL DO PEDIDO COM DESCONTOS = R$12,00 * 1 = R$12,00
        assertEquals(
        	novaCompra.getPreco() - novaCompra.getPromocoesCompra().get(0).getDesconto(),
        	(lanche.getPreco() * compra.getLanchesCompra().get(0).getQuantidade()) - (ingredientes.get(3).getPreco() * 2),
        	0.01	
        );
    }
    
}