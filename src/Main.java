import dao.DisciplinaDAO;
import dao.impl.DisciplinaDAOImp;
import db.DB;
import model.Disciplina;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        DisciplinaDAO dao = new DisciplinaDAOImp(DB.getConexao());

        System.out.println("Inserir");
        Disciplina d1 = new Disciplina("POO II", 80);
        Disciplina d2 = new Disciplina("BD", 60);
        dao.insert(d1);
        dao.insert(d2);

        System.out.println("\nAche todos");
        List<Disciplina> lista = dao.findAll();
        lista.forEach(System.out::println);

        System.out.println("\nAche por ID (id=" + d1.getIdDisciplina() + ") ===");
        Disciplina encontrada = dao.findById(d1.getIdDisciplina());
        System.out.println(encontrada);

        System.out.println("\n Atualize ");
        d1.setNomeDisciplina("POO I - Atualizado");
        d1.setCargaHoraria(90);
        dao.update(d1);
        System.out.println("Após atualizar: " + dao.findById(d1.getIdDisciplina()));

        System.out.println("\nDeletar (id=" + d2.getIdDisciplina() + ") ===");
        dao.deleteById(d2.getIdDisciplina());
        System.out.println("Após deletar:");
        dao.findAll().forEach(System.out::println);

        DB.closeConnection();
        System.out.println("\nConexão finalizada.");
    }
}
