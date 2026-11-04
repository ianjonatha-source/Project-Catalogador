import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Catalogo {
    private final ConectorBD bd = new ConectorBD();

    public void listFile() {
        try {
            List<Cataloga> arquivos = bd.listar();
            if (arquivos.isEmpty()) {
                System.out.println("\nNenhum arquivo catalogado.\n");
                return;
            }
            System.out.print("\n");
            for (Cataloga c : arquivos) {
                System.out.println("id:" + c.id
                        + " - nome: " + c.nome
                        + " | tipo: " + c.tipo
                        + " | categoria: " + c.categoria
                        + " | local: " + c.local
                        + " | diretorio: " + c.diretorio);
            }
            System.out.print("\n");
        } catch (SQLException e) {
            System.out.println("Erro ao listar: " + e.getMessage());
        }
    }

    public void addFile(Scanner scanner) {
        System.out.print("nome do arquivo: ");
        String nome = scanner.nextLine();
        System.out.print("tipo do arquivo: ");
        String tipo = scanner.nextLine();
        System.out.print("categoria do arquivo: ");
        String categoria = scanner.nextLine();
        System.out.print("local de armazenamento do arquivo (HD, Dropbox, Drive): ");
        String local = scanner.nextLine();
        System.out.print("diretorio (caminho até o arquivo) do arquivo: ");
        String diretorio = scanner.nextLine();

        if (local.equalsIgnoreCase("HD")) {
            // apenas verifica se o arquivo existe com base no diretorio
            try {
                if (!Files.exists(Path.of(diretorio))) {
                    System.out.println("O arquivo não foi encontrado nesse caminho.");
                    return;
                }
            } catch (InvalidPathException e) {
                System.out.println("Caminho inválido.");
                return;
            }
        } else if (local.equalsIgnoreCase("Dropbox")) {
            // verifica se o Dropbox está conectado e se o arquivo existe
        } else if (local.equalsIgnoreCase("Drive")) {
            // mesma história do de cima
        } else {
            System.out.print("O arquivo não foi identificado ou não existe \npor favor selecione um arquivo valido\n");
            return;
        }

        try {
            bd.inserir(new Cataloga(nome, tipo, categoria, local, diretorio));
            System.out.println("Arquivo adicionado!\n");
        } catch (SQLException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }

    public void modFile(Scanner scanner) { // modifica nome, local, diretorio, etc
        try {
            if (bd.listar().isEmpty()) {
                System.out.println("\nNenhum arquivo para modificar.");
                return;
            }

            listFile();
            System.out.print("\nid do arquivo que deseja modificar: ");
            int id = CatalogadorS.lerInt(scanner);

            Cataloga arquivo = bd.buscarPorId(id);
            if (arquivo == null) {
                System.out.println("\nid inválido.");
                return;
            }

            System.out.println("\no que deseja alterar?");
            System.out.println("1 - nome");
            System.out.println("2 - tipo");
            System.out.println("3 - categoria");
            System.out.println("4 - local");
            System.out.println("5 - diretorio");
            System.out.print("Escolha uma opção: ");
            int campo = CatalogadorS.lerInt(scanner);

            if (campo < 1 || campo > 5) {
                System.out.println("\nOpção inválida.\n");
                return;
            }

            System.out.print("\nnovo valor: ");
            String valor = scanner.nextLine();

            switch (campo) {
                case 1: arquivo.nome = valor; break;
                case 2: arquivo.tipo = valor; break;
                case 3: arquivo.categoria = valor; break;
                case 4: arquivo.local = valor; break;
                case 5: arquivo.diretorio = valor; break;
            }

            bd.atualizar(arquivo);
            System.out.println("Arquivo atualizado!\n");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar: " + e.getMessage());
        }
    }

    public void removeFile(Scanner scanner) { // remove o arquivo do catálogo
        try {
            if (bd.listar().isEmpty()) {
                System.out.println("\nNenhum arquivo para remover.");
                return;
            }

            listFile();
            System.out.print("id do arquivo que deseja remover: ");
            int id = CatalogadorS.lerInt(scanner);

            Cataloga arquivo = bd.buscarPorId(id);
            if (arquivo == null) {
                System.out.println("id inválido.");
                return;
            }

            System.out.print("Remover \"" + arquivo.nome + "\" do catálogo? (s/n): ");
            String resposta = scanner.nextLine();

            if (resposta.equalsIgnoreCase("s")) {
                bd.remover(id);
                System.out.println("Arquivo removido do catálogo!\n");
            } else {
                System.out.println("Remoção cancelada.\n");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao remover: " + e.getMessage());
        }
    }

    public void SyncDrive() {
        System.out.print("\nFalta implementar\n");
    }
}