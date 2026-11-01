
package inpetu;

import inpetu.model.Agenda;
import inpetu.model.Contato;
import inpetu.model.Recurso;
import inpetu.repository.memory.AgendaMemoryRepository;
import inpetu.repository.memory.ContatoMemoryRepository;
import inpetu.repository.memory.RecursoMemoryRepository;
import inpetu.service.AgendaService;
import inpetu.service.ContatoService;
import inpetu.service.RecursoService;

import java.time.LocalDateTime;

public class Application {

    public static void main(String[] args) {

// Repositórios
        AgendaMemoryRepository agendaRepository =
                new AgendaMemoryRepository();

        ContatoMemoryRepository contatoRepository =
                new ContatoMemoryRepository();

        RecursoMemoryRepository recursoRepository =
                new RecursoMemoryRepository();

// Serviços
        AgendaService agendaService = new AgendaService(
                agendaRepository,
                contatoRepository,
                recursoRepository
        );

        ContatoService contatoService = new ContatoService(
                contatoRepository,
                agendaService
        );

        RecursoService recursoService = new RecursoService(
                recursoRepository,
                agendaService
        );

        // Dados de teste
        Contato contato1 = new Contato("Maria");
        contato1.setCnpj("12345678000190");
        contato1.setTelefone("48999990001");

        Contato contato2 = new Contato("João");
        contato2.setCnpj("98765432000110");
        contato2.setTelefone("48999990002");

        contatoService.gravar(contato1);
        contatoService.gravar(contato2);

        Recurso sala = new Recurso(
                1, "Sala de reuniões", "Sala com capacidade para 10 pessoas"
        );

        recursoService.gravar(sala);

        LocalDateTime inicio = LocalDateTime.of(2026, 11, 10, 14, 0);
        LocalDateTime fim = LocalDateTime.of(2026, 11, 10, 15, 0);

//=================================================================================================

        // TESTE 1: reserva válida
        System.out.println("\n--- TESTE 1: Reserva válida ---");

        Agenda reserva1 = new Agenda(
                1, contato1, sala, inicio, fim
        );

        agendaService.gravar(reserva1);
        System.out.println("Reserva cadastrada: " + reserva1);

//=================================================================================================

        // TESTE 2: conflito no mesmo horário
        System.out.println("\n--- TESTE 2: Mesmo horário ---");

        Agenda reserva2 = new Agenda(
                2, contato2, sala,
                LocalDateTime.of(2026, 11, 10, 14, 0),
                LocalDateTime.of(2026, 11, 10, 15, 0)
        );

        try {
            agendaService.gravar(reserva2);
            System.out.println("ERRO: o conflito não foi bloqueado.");
        } catch (IllegalArgumentException e) {
            System.out.println("Conflito bloqueado: " + e.getMessage());
        }

//=================================================================================================

        // TESTE 3: horário diferente
        System.out.println("\n--- TESTE 3: Horário diferente ---");

        Agenda reserva3 = new Agenda(
                3, contato2, sala,
                LocalDateTime.of(2026, 11, 10, 15, 0),
                LocalDateTime.of(2026, 11, 10, 16, 0)
        );

        agendaService.gravar(reserva3);
        System.out.println("Reserva cadastrada: " + reserva3);

//=================================================================================================

        // TESTE 4: sobreposição parcial
        System.out.println("\n--- TESTE 4: Sobreposição parcial ---");

        Agenda reserva4 = new Agenda(
                4, contato2, sala,
                LocalDateTime.of(2026, 11, 10, 14, 30),
                LocalDateTime.of(2026, 11, 10, 15, 30)
        );

        try {
            agendaService.gravar(reserva4);
            System.out.println("ERRO: a sobreposição não foi bloqueada.");
        } catch (IllegalArgumentException e) {
            System.out.println("Sobreposição bloqueada: " + e.getMessage());
        }

//=================================================================================================

        // TESTE 5: Cadastro, busca e remoção de contato
        System.out.println("\n--- TESTE 5: Contatos ---");

        Contato contatoTeste = new Contato("Carlos");
        contatoTeste.setCnpj("12345678000123");
        contatoTeste.setTelefone("48999990003");

        contatoService.gravar(contatoTeste);

        System.out.println("Contato cadastrado: " + contatoTeste);

        // Busca por ID
        System.out.println("Busca por ID: "
                + contatoService.buscarPorId(contatoTeste.getId()));

        // Busca por nome
        System.out.println("Busca por nome: "
                + contatoService.buscarPorNome("Carlos"));

        // Remoção
        contatoService.remover(contatoTeste);

        System.out.println("Após remoção: "
                + contatoService.buscarPorId(contatoTeste.getId()));

//=================================================================================================

        System.out.println("\n--- TESTE 6: Recursos ---");

        Recurso recursoTeste = new Recurso(
                10, "Projetor", "Projetor multimídia"
        );

        recursoService.gravar(recursoTeste);

        System.out.println("Recurso cadastrado: " + recursoTeste);

        System.out.println("Busca por ID: "
                + recursoService.buscarPorId(10));

        recursoService.remover(recursoTeste);

        System.out.println("Após remoção: "
                + recursoService.buscarPorId(10));

//=================================================================================================

        System.out.println("\n--- TESTE 7: Recursos diferentes ---");

        Recurso outraSala = new Recurso(
                2, "Sala de treinamento",
                "Sala com capacidade para 20 pessoas"
        );

        recursoService.gravar(outraSala);

        Agenda reservaOutraSala = new Agenda(
                5,
                contato1,
                outraSala,
                LocalDateTime.of(2026, 11, 10, 14, 0),
                LocalDateTime.of(2026, 11, 10, 15, 0)
        );

        try {
            agendaService.gravar(reservaOutraSala);
            System.out.println("SUCESSO: reserva em outro recurso permitida.");
        } catch (IllegalArgumentException e) {
            System.out.println("ERRO: " + e.getMessage());
        }

//=================================================================================================

        System.out.println("\n--- TESTE 8: Horário inválido ---");

        Agenda reservaInvalida = new Agenda(
                6,
                contato1,
                sala,
                LocalDateTime.of(2026, 11, 10, 17, 0),
                LocalDateTime.of(2026, 11, 10, 17, 0)
        );

        try {
            agendaService.gravar(reservaInvalida);
            System.out.println("ERRO: o horário inválido foi aceito.");
        } catch (IllegalArgumentException e) {
            System.out.println("SUCESSO: horário inválido bloqueado. "
                    + e.getMessage());
        }

//=================================================================================================

        System.out.println("\n--- TESTE 9: Atualizar reserva ---");

        Agenda agendaAtualizada = new Agenda(
                1,
                contato1,
                sala,
                LocalDateTime.of(2026, 11, 10, 16, 0),
                LocalDateTime.of(2026, 11, 10, 17, 0)
        );

        try {
            agendaService.atualizar(agendaAtualizada);
            System.out.println("SUCESSO: reserva atualizada.");
            System.out.println(agendaService.buscarPorId(1));
        } catch (IllegalArgumentException e) {
            System.out.println("ERRO: " + e.getMessage());
        }

//=================================================================================================

        System.out.println("\n--- TESTE 10: Atualização com conflito ---");

        Agenda reservaComConflito = new Agenda(
                1,
                contato1,
                sala,
                LocalDateTime.of(2026, 11, 10, 15, 0),
                LocalDateTime.of(2026, 11, 10, 16, 0)
        );

        try {
            agendaService.atualizar(reservaComConflito);
            System.out.println("ERRO: deveria bloquear o conflito.");
        } catch (IllegalArgumentException e) {
            System.out.println("SUCESSO: conflito bloqueado.");
            System.out.println(e.getMessage());
        }

//=================================================================================================

        System.out.println("\n--- TESTE 11: Verificar reserva original ---");

        System.out.println("Reserva após tentativa de atualização com conflito: "
                + agendaService.buscarPorId(1));

//=================================================================================================

        System.out.println("\n--- TESTE 12: Remover reserva ---");

        Agenda reservaParaRemover = agendaService.buscarPorId(5)
                .orElseThrow();

        agendaService.remover(reservaParaRemover);

        System.out.println("Reserva após remoção: "
                + agendaService.buscarPorId(5));

//=================================================================================================

        System.out.println("\n--- TESTE 13: Buscar reserva inexistente ---");

        System.out.println("Busca por ID 999: "
                + agendaService.buscarPorId(999));

//=================================================================================================

        System.out.println("\n--- TESTE 14: Reserva sem contato ---");

        Agenda reservaSemContato = new Agenda(
                7,
                null,
                sala,
                LocalDateTime.of(2026, 11, 10, 18, 0),
                LocalDateTime.of(2026, 11, 10, 19, 0)
        );

        try {
            agendaService.gravar(reservaSemContato);
            System.out.println("ERRO: reserva sem contato foi aceita.");
        } catch (IllegalArgumentException e) {
            System.out.println("SUCESSO: reserva inválida bloqueada.");
            System.out.println(e.getMessage());
        }

//=================================================================================================

        System.out.println("\n--- TESTE 15: Reservas consecutivas ---");

        Agenda reservaConsecutiva = new Agenda(
                8,
                contato2,
                sala,
                LocalDateTime.of(2026, 11, 10, 17, 0),
                LocalDateTime.of(2026, 11, 10, 18, 0)
        );

        try {
            agendaService.gravar(reservaConsecutiva);
            System.out.println("SUCESSO: reserva consecutiva permitida.");
        } catch (IllegalArgumentException e) {
            System.out.println("ERRO: " + e.getMessage());
        }


//=================================================================================================

        System.out.println("\n--- TESTE 16: Atualizar contato ---");

        Contato contatoAtualizar = new Contato("Pedro");
        contatoAtualizar.setCnpj("12345678000124");
        contatoAtualizar.setTelefone("48999990004");

        contatoService.gravar(contatoAtualizar);

// Criando uma versão atualizada do contato
        Contato contatoNovo = new Contato("Pedro Silva");
        contatoNovo.setCnpj("12345678000124");
        contatoNovo.setTelefone("48999990004");
        contatoNovo.setId(contatoAtualizar.getId());

        try {
            contatoService.atualizar(contatoNovo);

            System.out.println("SUCESSO: contato atualizado.");
            System.out.println("Contato atualizado: "
                    + contatoService.buscarPorId(contatoAtualizar.getId()));

        } catch (IllegalArgumentException e) {
            System.out.println("ERRO: " + e.getMessage());
        }

//=================================================================================================

        System.out.println("\n--- TESTE 17: Listar contatos ---");

        Contato contatoLista1 = new Contato("Ana");
        contatoLista1.setCnpj("12345678000125");
        contatoLista1.setTelefone("48999990005");

        Contato contatoLista2 = new Contato("Lucas");
        contatoLista2.setCnpj("12345678000126");
        contatoLista2.setTelefone("48999990006");

        contatoService.gravar(contatoLista1);
        contatoService.gravar(contatoLista2);

        System.out.println("Total de contatos cadastrados: "
                + contatoService.listarTodos().size());

        contatoService.listarTodos().forEach(System.out::println);

//=================================================================================================

        System.out.println("\n--- TESTE 18: Atualizar recurso ---");

        Recurso recursoAtualizar = new Recurso(
                20, "Projetor antigo", "Projetor básico"
        );

        recursoService.gravar(recursoAtualizar);

// Criando o recurso atualizado
        Recurso recursoNovo = new Recurso(
                20, "Projetor Epson", "Projetor multimídia Full HD"
        );

        try {
            recursoService.atualizar(recursoNovo);

            System.out.println("SUCESSO: recurso atualizado.");
            System.out.println("Recurso atualizado: "
                    + recursoService.buscarPorId(20));

        } catch (IllegalArgumentException e) {
            System.out.println("ERRO: " + e.getMessage());
        }

//=================================================================================================


        System.out.println("\n--- TESTE 19: Listar recursos ---");

        Recurso recursoLista1 = new Recurso(
                21, "Notebook", "Notebook para apresentações"
        );

        Recurso recursoLista2 = new Recurso(
                22, "Microfone", "Microfone sem fio"
        );

        recursoService.gravar(recursoLista1);
        recursoService.gravar(recursoLista2);

        System.out.println("Total de recursos cadastrados: "
                + recursoService.listarTodos().size());

        recursoService.listarTodos().forEach(System.out::println);

//=================================================================================================

        System.out.println("\n--- TESTE 20: Remover contato vinculado ---");

        try {
            Contato contatoVinculado = contatoService.buscarPorId(1).orElseThrow();
            contatoService.remover(contatoVinculado);

            System.out.println("ERRO: o sistema permitiu remover um contato vinculado.");
        } catch (IllegalArgumentException e) {
            System.out.println("SUCESSO: remoção bloqueada.");
            System.out.println(e.getMessage());
        }

//=================================================================================================

        System.out.println("\n--- TESTE 21: Remover recurso vinculado ---");

        try {
            Recurso recursoVinculado = recursoService.buscarPorId(1).orElseThrow();
            recursoService.remover(recursoVinculado);

            System.out.println("ERRO: o sistema permitiu remover um recurso vinculado.");
        } catch (IllegalArgumentException e) {
            System.out.println("SUCESSO: remoção bloqueada.");
            System.out.println(e.getMessage());
        }

//=================================================================================================

        System.out.println("\n--- TESTE 22: Contato inexistente ---");

        Contato contatoInexistente = new Contato("Contato Fantasma");
        contatoInexistente.setId(999);

        Agenda reservaContatoInexistente = new Agenda(
                30,
                contatoInexistente,
                sala,
                LocalDateTime.of(2026, 11, 11, 10, 0),
                LocalDateTime.of(2026, 11, 11, 11, 0)
        );

        try {
            agendaService.gravar(reservaContatoInexistente);
            System.out.println("ERRO: contato inexistente foi aceito.");
        } catch (IllegalArgumentException e) {
            System.out.println("SUCESSO: contato inexistente bloqueado.");
            System.out.println(e.getMessage());
        }

//=================================================================================================

        System.out.println("\n--- TESTE 23: Recurso inexistente ---");

        Recurso recursoInexistente = new Recurso(
                999, "Sala inexistente", "Recurso de teste"
        );

        Agenda reservaRecursoInexistente = new Agenda(
                31,
                contato1,
                recursoInexistente,
                LocalDateTime.of(2026, 11, 11, 10, 0),
                LocalDateTime.of(2026, 11, 11, 11, 0)
        );

        try {
            agendaService.gravar(reservaRecursoInexistente);
            System.out.println("ERRO: recurso inexistente foi aceito.");
        } catch (IllegalArgumentException e) {
            System.out.println("SUCESSO: recurso inexistente bloqueado.");
            System.out.println(e.getMessage());
        }

//=================================================================================================

        System.out.println("\n--- TESTE 24: Atualização com contato inexistente ---");

        Contato contatoInexistenteAtualizacao = new Contato("Contato Fantasma");
        contatoInexistenteAtualizacao.setId(999);

        Agenda atualizacaoContatoInexistente = new Agenda(
                1,
                contatoInexistenteAtualizacao,
                sala,
                LocalDateTime.of(2026, 11, 11, 10, 0),
                LocalDateTime.of(2026, 11, 11, 11, 0)
        );

        try {
            agendaService.atualizar(atualizacaoContatoInexistente);
            System.out.println("ERRO: atualização com contato inexistente foi aceita.");
        } catch (IllegalArgumentException e) {
            System.out.println("SUCESSO: atualização bloqueada.");
            System.out.println(e.getMessage());
        }

        System.out.println("Reserva original: " + agendaService.buscarPorId(1));

//=================================================================================================

        System.out.println("\n--- TESTE 25: Atualização com recurso inexistente ---");

        Agenda atualizacaoRecursoInexistente = new Agenda(
                1,
                contato1,
                recursoInexistente,
                LocalDateTime.of(2026, 11, 11, 10, 0),
                LocalDateTime.of(2026, 11, 11, 11, 0)
        );

        try {
            agendaService.atualizar(atualizacaoRecursoInexistente);
            System.out.println("ERRO: atualização com recurso inexistente foi aceita.");
        } catch (IllegalArgumentException e) {
            System.out.println("SUCESSO: atualização bloqueada.");
            System.out.println(e.getMessage());
        }

        System.out.println("Reserva original: " + agendaService.buscarPorId(1));

//=================================================================================================

        System.out.println("\n--- TESTE 26: Remover reserva com ID inválido ---");

        Agenda reservaIdInvalido = new Agenda(
                0,
                contato1,
                sala,
                inicio,
                fim
        );

        try {
            agendaService.remover(reservaIdInvalido);
            System.out.println("ERRO: reserva com ID inválido foi removida.");
        } catch (IllegalArgumentException e) {
            System.out.println("SUCESSO: remoção bloqueada.");
            System.out.println(e.getMessage());
        }

//=================================================================================================

        System.out.println("\n--- TESTE 27: Remover reserva com ID negativo ---");

        Agenda reservaIdNegativo = new Agenda(
                -1,
                contato1,
                sala,
                inicio,
                fim
        );

        try {
            agendaService.remover(reservaIdNegativo);
            System.out.println("ERRO: reserva com ID negativo foi removida.");
        } catch (IllegalArgumentException e) {
            System.out.println("SUCESSO: remoção bloqueada.");
            System.out.println(e.getMessage());
        }

//=================================================================================================

        System.out.println("\n--- TESTE 28: Remover reserva inexistente ---");

        Agenda reservaInexistente = new Agenda(
                999,
                contato1,
                sala,
                inicio,
                fim
        );

        try {
            agendaService.remover(reservaInexistente);
            System.out.println("ERRO: reserva inexistente foi removida.");
        } catch (IllegalArgumentException e) {
            System.out.println("SUCESSO: remoção bloqueada.");
            System.out.println(e.getMessage());
        }

//=================================================================================================

        System.out.println("\n--- TESTE 29: Remover reserva válida ---");

        Agenda reservaRemover = agendaService.buscarPorId(8)
                .orElseThrow();

        try {
            agendaService.remover(reservaRemover);

            System.out.println("SUCESSO: reserva removida.");
            System.out.println("Consulta após remoção: "
                    + agendaService.buscarPorId(8));

        } catch (IllegalArgumentException e) {
            System.out.println("ERRO: " + e.getMessage());
        }

//=================================================================================================

        System.out.println("\n--- TESTE 30: Remover contato com ID inválido ---");

        Contato contatoIdInvalido = new Contato("Contato inválido");
        contatoIdInvalido.setId(0);

        try {
            contatoService.remover(contatoIdInvalido);
            System.out.println("ERRO: contato com ID inválido foi removido.");
        } catch (IllegalArgumentException e) {
            System.out.println("SUCESSO: remoção bloqueada.");
            System.out.println(e.getMessage());
        }

//=================================================================================================

        System.out.println("\n--- TESTE 30: Remover contato com ID inválido ---");

        Contato contatoIdInvalidoRemocao = new Contato("Contato inválido");
        contatoIdInvalidoRemocao.setId(0);

        try {
            contatoService.remover(contatoIdInvalidoRemocao);
            System.out.println("ERRO: contato com ID inválido foi removido.");
        } catch (IllegalArgumentException e) {
            System.out.println("SUCESSO: remoção bloqueada.");
            System.out.println(e.getMessage());
        }

//=================================================================================================

        System.out.println("\n--- TESTE 31: Remover contato inexistente ---");

        Contato contatoNaoCadastrado = new Contato("Contato inexistente");
        contatoNaoCadastrado.setId(999);

        try {
            contatoService.remover(contatoNaoCadastrado);
            System.out.println("ERRO: contato inexistente foi removido.");
        } catch (IllegalArgumentException e) {
            System.out.println("SUCESSO: remoção bloqueada.");
            System.out.println(e.getMessage());
        }

//=================================================================================================

        System.out.println("\n--- TESTE 32: Remover recurso com ID inválido ---");

        Recurso recursoIdInvalido = new Recurso(
                0, "Recurso inválido", "Recurso de teste"
        );

        try {
            recursoService.remover(recursoIdInvalido);
            System.out.println("ERRO: recurso com ID inválido foi removido.");
        } catch (IllegalArgumentException e) {
            System.out.println("SUCESSO: remoção bloqueada.");
            System.out.println(e.getMessage());
        }

//=================================================================================================

        System.out.println("\n--- TESTE 33: Remover recurso inexistente ---");

        Recurso recursoNaoCadastrado = new Recurso(
                999, "Recurso inexistente", "Recurso de teste"
        );

        try {
            recursoService.remover(recursoNaoCadastrado);
            System.out.println("ERRO: recurso inexistente foi removido.");
        } catch (IllegalArgumentException e) {
            System.out.println("SUCESSO: remoção bloqueada.");
            System.out.println(e.getMessage());
        }

//=================================================================================================

        // Resultado final
        System.out.println("\n--- RESULTADO FINAL ---");
        System.out.println(
                "Total de reservas cadastradas: "
                        + agendaService.listarTodos().size()
        );

        agendaService.listarTodos().forEach(System.out::println);

    }

}
