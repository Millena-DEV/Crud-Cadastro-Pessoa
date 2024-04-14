
package br.com.Bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.entidades.Pessoa;
import br.com.entidades.Professor;
import br.com.jpautil.JPAUtil;

@SessionScoped
@ManagedBean(name = "professorBean")
public class ProfessorBean {

    private Professor prof = new Professor();
    private List<Professor> professores;
    private List<Pessoa> pessoas;
    public ProfessorBean() {
       
    }

    public Professor getProf() {
        return prof;
    }

    public void setProf(Professor prof) {
        this.prof = prof;
    }

    public String salva() {

        EntityManager em = JPAUtil.getEntityManager();
        try {
            // Inicia uma transação com o banco de dados.
            em.getTransaction().begin();
            // Verifica se a pessoa ainda não está salva no banco de dados.
            if (prof.getIdProfessor() == null) {
                //Salva os dados da pessoa.
                em.persist(prof);
            } else {
                //Atualiza os dados da pessoa.
                prof = em.merge(prof);
            }
            // Finaliza a transação.
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        this.professores = null;
        return "ListarProfessores";
    }

    public List<Professor> getProfessores() {

        if (this.professores == null) {
            EntityManager em = JPAUtil.getEntityManager();
            Query q = em.createQuery("select a from Professor a",
                    Professor.class);
            this.professores = q.getResultList();
            em.close();
        }
        return professores;
    }

    public void excluir(Professor professor) {

        if (professor.getIdProfessor() != null) {
            EntityManager em = JPAUtil.getEntityManager();

            System.out.println(professor.getIdProfessor() + ", " + professor.getNome());
            em.getTransaction().begin();
            professor = em.merge(professor);
            em.remove(professor);
            em.getTransaction().commit();
            em.close();
        }

        this.professores = null;

    }

    public String buscaProfessor(Professor prof) {
        EntityManager em = JPAUtil.getEntityManager();

        em.getTransaction().begin();
        this.prof = em.find(Professor.class, prof.getIdProfessor());
        em.getTransaction().commit();

        em.close();

        return "/professor/Prof_Orientacao_Aluno";

    }

    public String alterar(Professor p) {
        this.prof = p;
        return "CadastrarProfessor";
    }

    public String cadastrarNovo() {
        prof = new Professor();
        return "/professor/CadastrarProfessor";
    }

    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    public void setPessoas(List<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }

    public List<Pessoa> getPessoasOrientados() {
        return this.prof.getAlunos();
    }
}
