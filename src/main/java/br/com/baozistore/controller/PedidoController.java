package br.com.baozistore.controller;

import br.com.baozistore.model.Pedido;
import br.com.baozistore.repository.ClienteRepository;
import br.com.baozistore.repository.PedidoRepository;
import br.com.baozistore.repository.ProdutoRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    private final PedidoRepository repository;
    private final ClienteRepository clientes;
    private final ProdutoRepository produtos;

    public PedidoController(PedidoRepository repository, ClienteRepository clientes, ProdutoRepository produtos) {
        this.repository = repository;
        this.clientes = clientes;
        this.produtos = produtos;
    }

    @PostMapping
    public ResponseEntity<PedidoResposta> criar(@Valid @RequestBody PedidoEntrada dados) {
        var cliente = clientes.findById(dados.clienteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
        var produto = produtos.findById(dados.produtoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
        Pedido salvo = repository.save(new Pedido(cliente, produto, dados.quantidade()));
        return ResponseEntity.created(URI.create("/api/pedidos/" + salvo.getId()))
                .body(PedidoResposta.de(salvo));
    }

    @GetMapping
    public List<PedidoResposta> listar() {
        return repository.findAll(Sort.by("id")).stream().map(PedidoResposta::de).toList();
    }

    @GetMapping("/{id}")
    public PedidoResposta consultar(@PathVariable Long id) {
        return PedidoResposta.de(buscar(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        repository.delete(buscar(id));
    }

    private Pedido buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
    }

    public record PedidoEntrada(
            @NotNull @Positive Long clienteId,
            @NotNull @Positive Long produtoId,
            @NotNull @Positive Integer quantidade
    ) {
    }

    public record PedidoResposta(Long id, Long clienteId, Long produtoId, Integer quantidade) {
        static PedidoResposta de(Pedido pedido) {
            return new PedidoResposta(pedido.getId(), pedido.getCliente().getId(),
                    pedido.getProduto().getId(), pedido.getQuantidade());
        }
    }
}
