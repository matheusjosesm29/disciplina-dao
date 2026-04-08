package dao.impl;

import dao.DisciplinaDAO;
import db.DB;
import db.DbException;
import model.Disciplina;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaDAOImp implements DisciplinaDAO {

    private Connection conn;

    public DisciplinaDAOImp(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Disciplina obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                "INSERT INTO disciplina (nomedisciplina, cargahoraria) VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );
            st.setString(1, obj.getNomeDisciplina());
            st.setInt(2, obj.getCargaHoraria());

            int linhasAfetadas = st.executeUpdate();

            if (linhasAfetadas > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    obj.setIdDisciplina(rs.getInt(1));
                }
                DB.closeResultSet(rs);
                System.out.println("Disciplina inserida. ID gerado: " + obj.getIdDisciplina());
            } else {
                throw new DbException("Erro: nenhuma linha foi inserida.");
            }
        } catch (SQLException e) {
            throw new DbException("Erro ao inserir disciplina: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Disciplina obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                "UPDATE disciplina SET nomedisciplina = ?, cargahoraria = ? WHERE iddisciplina = ?"
            );
            st.setString(1, obj.getNomeDisciplina());
            st.setInt(2, obj.getCargaHoraria());
            st.setInt(3, obj.getIdDisciplina());

            int linhasAfetadas = st.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Disciplina atualizada. Linhas afetadas: " + linhasAfetadas);
            } else {
                System.out.println("Nenhuma disciplina encontrada com o ID: " + obj.getIdDisciplina());
            }
        } catch (SQLException e) {
            throw new DbException("Erro ao atualizar disciplina: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                "DELETE FROM disciplina WHERE iddisciplina = ?"
            );
            st.setInt(1, id);

            int linhasAfetadas = st.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Disciplina deletada. ID: " + id);
            } else {
                System.out.println("Nenhuma disciplina encontrada com o ID: " + id);
            }
        } catch (SQLException e) {
            throw new DbException("Erro ao deletar disciplina: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Disciplina findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                "SELECT * FROM disciplina WHERE iddisciplina = ?"
            );
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                return instanciarDisciplina(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DbException("Erro ao buscar disciplina por ID: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Disciplina> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                "SELECT * FROM disciplina ORDER BY iddisciplina"
            );
            rs = st.executeQuery();

            List<Disciplina> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(instanciarDisciplina(rs));
            }
            return lista;
        } catch (SQLException e) {
            throw new DbException("Erro ao buscar todas as disciplinas: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Disciplina instanciarDisciplina(ResultSet rs) throws SQLException {
        Disciplina d = new Disciplina();
        d.setIdDisciplina(rs.getInt("iddisciplina"));
        d.setNomeDisciplina(rs.getString("nomedisciplina"));
        d.setCargaHoraria(rs.getInt("cargahoraria"));
        return d;
    }
}
