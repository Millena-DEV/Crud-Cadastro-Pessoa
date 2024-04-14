package br.com.Bean;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import br.com.entidades.Endereco;
import br.com.entidades.Escola;
import br.com.entidades.Pessoa;
import br.com.jpautil.JPAUtil;


@ViewScoped
@SessionScoped
@ManagedBean(name = "pessoaBean")
public class PessoaBean {

	private Pessoa pessoa = new Pessoa();
	private List<Pessoa> pessoas;
	private Endereco endereco = new Endereco();
	private List<Endereco> enderecos;
	
     
    public PessoaBean() {
    }

	public Pessoa getPessoa() {
		return pessoa;
	}
	
	public Endereco getEndereco() {
		return endereco;
	}
	
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
 
	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}
	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos =enderecos;
	}

	public List<Pessoa> getPessoas() {
		if (this.pessoas == null) {
			EntityManager em = JPAUtil.getEntityManager();
			Query query = em.createQuery("SELECT p FROM Pessoa p", Pessoa.class);
			this.pessoas = query.getResultList();
			em.close();
		}
		return this.pessoas;
	     
	}
	public String salva() {
	    EntityManager em = JPAUtil.getEntityManager();
	    EntityTransaction transaction = em.getTransaction();
	    try {
	        transaction.begin();

	       
	        if (pessoa.getEndereco() == null) {
	           
	            pessoa.setEndereco(endereco);
	        }

	      
	        if (endereco.getIdendereco() == null) {
	         
	            em.persist(endereco);
	        }

	 
	        if (pessoa.getIdPessoa() != null) {
	           
	            pessoa = em.merge(pessoa);
	        } else {
	       
	            em.persist(pessoa);
	        }
	        transaction.commit();

	       
	        return "Pessoa salva com sucesso!";
	    } catch (Exception e) {
	     
	        if (transaction.isActive()) {
	            transaction.rollback();
	        }
	      
	        e.printStackTrace();
	        
	        return "Erro ao salvar pessoa: " + e.getMessage();
	    } finally {
	        em.close();
	    }
	}

	public void excluir(Pessoa pessoa) {

        if (pessoa.getIdPessoa() != null) {
            EntityManager em = JPAUtil.getEntityManager();
            em.getTransaction().begin();
          pessoa = em.merge(pessoa);
          endereco = em.merge(endereco);
            em.remove(pessoa);
            em.remove(endereco);
            em.getTransaction().commit();
            em.close();
        }

        this.pessoas = null;

    }

    public String alterar(Pessoa p) {
    	this.pessoa = p;
        //this.pessoa =p;
        return "CadastrarPessoa";
    }


    public String  cadastrarNovo() {
      pessoa = new Pessoa();
      endereco = new Endereco();
        return "/pessoa/CadastrarPessoa";
    }
    

}
