import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConectorBD {
    private static final String URL =
            "jdbc:mysql://localhost:3306/catalogador?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8";
    private static final String USUARIO = "root";
    private static final String SENHA = "voidspecter"; // troque pela senha do root do MySQL

    // abre a conexão com o banco - quem chama fecha
    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }

    public void inserir(Cataloga c) throws SQLException {
        String sql = "INSERT INTO arquivos (nome, tipo, categoria, local_arquivo, diretorio) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.nome);
            ps.setString(2, c.tipo);
            ps.setString(3, c.categoria);
            ps.setString(4, c.local);
            ps.setString(5, c.diretorio);
            ps.executeUpdate();
        }
    }

    public List<Cataloga> listar() throws SQLException {
        List<Cataloga> lista = new ArrayList<>();
        String sql = "SELECT * FROM arquivos ORDER BY id";
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(montar(rs));
            }
        }
        return lista;
    }

    public Cataloga buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM arquivos WHERE id = ?";
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return montar(rs);
                }
            }
        }
        return null; // não existe esse id
    }

    public void atualizar(Cataloga c) throws SQLException {
        String sql = "UPDATE arquivos SET nome = ?, tipo = ?, categoria = ?, local_arquivo = ?, diretorio = ? WHERE id = ?";
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.nome);
            ps.setString(2, c.tipo);
            ps.setString(3, c.categoria);
            ps.setString(4, c.local);
            ps.setString(5, c.diretorio);
            ps.setInt(6, c.id);
            ps.executeUpdate();
        }
    }

    public void remover(int id) throws SQLException {
        String sql = "DELETE FROM arquivos WHERE id = ?";
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // transforma uma linha do banco num objeto Cataloga
    private Cataloga montar(ResultSet rs) throws SQLException {
        return new Cataloga(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("tipo"),
                rs.getString("categoria"),
                rs.getString("local_arquivo"),
                rs.getString("diretorio"));
    }
}