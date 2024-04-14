package br.com.Bean;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.entidades.Endereco;
import br.com.entidades.Pessoa;
import br.com.jpautil.JPAUtil;


@ViewScoped
@SessionScoped
@ManagedBean(name = "enderecoBean")
public class EnderecoBean {

	private Endereco endereco = new Endereco();
	private List<Endereco> enderecos;
     
    public EnderecoBean() {
    }

	public Endereco getEndereco() {
		return endereco;
	}
	

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public List<Endereco> getEnderecos() {
	    if (this.enderecos == null) {
	        EntityManager em = JPAUtil.getEntityManager();
	        Query query = em.createQuery("SELECT e FROM Endereco e", Endereco.class);
	        this.enderecos = query.getResultList();
	        em.close();
	    }
	    return this.enderecos;
	}


    public String salva() {

        EntityManager em = JPAUtil.getEntityManager();
        try {
            // Inicia uma transação com o banco de dados.
            em.getTransaction().begin();
            // Verifica se a pessoa ainda não está salva no banco de dados.
            if (endereco.getCep() != null) {
                //Atualiza os dados da pessoa.
                endereco = em.merge(endereco);
               
            } else {
                //Salva os dados da pessoa.
                em.persist(endereco);
               
            }
            // Finaliza a transação.
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        this.enderecos = null;
        return "ListarAlunos";
    }

   /* public void excluir(Pessoa pessoa) {

        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        pessoa = em.merge(pessoa);
        em.remove(pessoa);
        em.getTransaction().commit();
        em.close();

        this.enderecos = null;

    }
*/
    public String alterar(Endereco e) {
        this.endereco = e;
        return "CadastrarPessoa";
    }

    public String cadastrarNovo() {
      endereco = new Endereco();
        return "/pessoa/CadastrarPessoa";
    }

}
