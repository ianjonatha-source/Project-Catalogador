public class Cataloga {
        public String nome;
        public String tipo;
        public String categoria;
        public String local;
        public String diretorio; //mostrar o caminho/diretorio do arquivo caso esteja no armazenamento do PC
        public int id;

        public Cataloga(String nome, String tipo, String categoria, String local, String diretorio) {
                this.nome = nome;
                this.tipo = tipo;
                this.categoria = categoria;
                this.local = local; // onde ta o arquivo
                this.diretorio = diretorio; // o caminho até o arquivo

        }

        public Cataloga(int id, String nome, String tipo, String categoria, String local, String diretorio) {
                this(nome, tipo, categoria, local, diretorio);
                this.id = id;
        }
}