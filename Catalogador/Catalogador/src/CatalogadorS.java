import java.util.InputMismatchException;
import java.util.Scanner;


public class CatalogadorS {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Catalogo catalogo = new Catalogo();

        int opcao;
        do {
            System.out.println("\n|| CATALOGADOR ||");
            System.out.println("1 - listar arquivos");
            System.out.println("2 - adicionar arquivo");
            System.out.println("3 - modificar arquivo");
            System.out.println("4 - remover arquivo");
            System.out.println("5 - vincular armazenamento em nuvem");
            System.out.println("6 - teste do bloco de notas - teste de salvamento"); // cria o arquivo de save da lista
            System.out.println("0 - sair");
            System.out.print("Escolha uma opção: ");
            opcao = lerInt(scanner);

            switch (opcao) {
                case 1:
                    catalogo.listFile();
                    break;
                case 2:
                    catalogo.addFile(scanner);
                    break;
                case 3:
                    catalogo.modFile(scanner);
                    break;
                case 4:
                    catalogo.removeFile(scanner);
                    break;
                case 5:
                    catalogo.SyncDrive();
                    break;
                case 6:
                    System.out.println("esta funcionalidade de teste está removida indefinidamente");
                    break;
                case 0:
                    System.out.println("\nSaindo...");
                    break;
                default:
                    System.out.println("\nOpção inválida.\n");
            }
        } while (opcao != 0);
    }

    public static int lerInt(Scanner scanner) {
        while (true) {
            try {
                int n = scanner.nextInt();
                scanner.nextLine();
                return n;
            } catch (InputMismatchException e) {
                System.out.print("Entrada inválida: digite um número inteiro: ");
                scanner.nextLine();
            }
        }
    }
}