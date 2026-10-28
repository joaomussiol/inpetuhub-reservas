
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

        // Repositórios e serviços
        ContatoService contatoService =
                new ContatoService(new ContatoMemoryRepository());

        RecursoService recursoService =
                new RecursoService(new RecursoMemoryRepository());

        AgendaService agendaService =
                new AgendaService(new AgendaMemoryRepository());

        // Dados de teste
        Contato contato1 = new Contato("Maria");
        Contato contato2 = new Contato("João");

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

        Agenda reservaAtualizada = new Agenda(
                1,
                contato1,
                sala,
                LocalDateTime.of(2026, 11, 10, 16, 0),
                LocalDateTime.of(2026, 11, 10, 17, 0)
        );

        try {
            agendaService.gravar(reservaAtualizada);
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
            agendaService.gravar(reservaComConflito);
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

        // Resultado final
        System.out.println("\n--- RESULTADO FINAL ---");
        System.out.println(
                "Total de reservas cadastradas: "
                        + agendaService.listarTodos().size()
        );

        agendaService.listarTodos().forEach(System.out::println);

    }
}
