
package br.com.Bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.entidades.Escola;
import br.com.jpautil.JPAUtil;



@SessionScoped
@ManagedBean(name = "escolaBean")
public class EscolaBean {

    private Escola escola = new Escola();
    private List<Escola> escolas;

    public EscolaBean() {
        escola.setId(null);
    }

    public Escola getEscola() {
        return escola;
    }

    public void setEscola(Escola escola) {
        this.escola = escola;
    }

    public String salva() {

        EntityManager em = JPAUtil.getEntityManager();
        try {
            // Inicia uma transação com o banco de dados.
            em.getTransaction().begin();
            // Verifica se a pessoa ainda não está salva no banco de dados.
            if (escola.getId() == null) {
                //Salva os dados da pessoa.
                em.persist(escola);
            } else {
                //Atualiza os dados da pessoa.
                escola = em.merge(escola);
            }
            // Finaliza a transação.
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        this.escolas = null;
        return "ListarEscolas";
    }

    public List<Escola> getEscolas() {

        if (this.escolas == null) {
            EntityManager em = JPAUtil.getEntityManager();
            Query e = em.createQuery("select e from Escola e",
                    Escola.class);
            this.escolas = e.getResultList();
            em.close();
        }
        return escolas;
    }

    public void excluir(Escola escola) {

        if (escola.getId() != null) {
            EntityManager em = JPAUtil.getEntityManager();
            em.getTransaction().begin();
            escola = em.merge(escola);
            em.remove(escola);
            em.getTransaction().commit();
            em.close();
        }

        this.escolas = null;

    }

    public String alterar(Escola e) {
        this.escola = e;
        return "CadastrarEscola";
    }

    public String cadastrarNovo() {
        escola = new Escola();
        return "/escola/CadastrarEscola";
    }
    
}
