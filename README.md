# Baozi Store Millene

API REST criada para a atividade prática de Desenvolvimento Web Back-End. A
loja cadastra clientes, produtos e pedidos. Cada pedido liga um cliente a um
produto e registra a quantidade comprada.

## Como executar

Requisitos: JDK 17 ou mais recente, Maven 3.9 e Postman para os testes manuais.

Na pasta que contém o arquivo `pom.xml`, execute:

```bash
mvn spring-boot:run
```

A URL base é `http://localhost:8080`. O Spring cria as tabelas no H2 local.
Os dados são gravados em `data/baozi-store-millene.mv.db` e permanecem entre execuções.

## Operações

| Entidade | Criar | Listar | Consultar | Excluir |
|---|---|---|---|---|
| Cliente | POST /api/clientes | GET /api/clientes | GET /api/clientes/{id} | DELETE /api/clientes/{id} |
| Produto | POST /api/produtos | GET /api/produtos | GET /api/produtos/{id} | DELETE /api/produtos/{id} |
| Pedido | POST /api/pedidos | GET /api/pedidos | GET /api/pedidos/{id} | DELETE /api/pedidos/{id} |

O POST recebe JSON e devolve HTTP 201 e o registro criado. O GET devolve HTTP
200. A exclusão bem-sucedida devolve HTTP 204 sem corpo. Ids ausentes devolvem
HTTP 404. Requisições com dados inválidos devolvem HTTP 400. A exclusão de
cliente ou produto ainda vinculado a um pedido devolve HTTP 409.

## Testes e capturas no Postman

1. Mantenha a API aberta e importe `Baozi_Store.postman_collection.json`.
2. Abra cada requisição na ordem da coleção e clique em **Send**. Os três
   POSTs guardam os IDs automaticamente para os GETs e DELETEs seguintes.
3. Capture a tela de cada requisição logo após enviá-la. Deixe visíveis o
   método, a URL, o status e o corpo de resposta. Nos POSTs, selecione também
   a aba **Body** do pedido enviado. Nos DELETEs, mostre o status **204 No Content**.
4. Exclua primeiro o pedido. Depois, exclua o produto e o cliente.
5. Publique a pasta do projeto em um repositório GitHub, copie o link e
   acrescente-o ao relatório. Os arquivos gerados pelo Maven e pelo banco
   local estão no `.gitignore`.

O H2 e a aplicação executam localmente, portanto selecione o Postman Desktop
ou o Desktop Agent ao acessar a API.

## Identificação da atividade

Millene Marcely Ferreira Lima — RU 5183679.

A coleção cadastra Baozi de Frango por R$ 9,50 e um pedido de quatro unidades.
Encerre a outra API na porta 8080 antes de iniciar esta cópia.
