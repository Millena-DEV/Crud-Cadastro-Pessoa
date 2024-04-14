
package br.com.entidades;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
@Entity
@SequenceGenerator(name = "seq_prof", sequenceName = "seq_prof", allocationSize = 1, initialValue = 1)
public class Professor  extends Pessoa {
	private static final long serialVersionUID = 1L;
	@SequenceGenerator(name = "seq_prof", sequenceName = "seq_prof", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long idProfessor;
    private String titulacao;
    private Integer anoContratacao;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
   @JoinColumn(name = "idPessoa")
    private List<Pessoa> pessoas;
    
    
    public Long getIdProfessor() {
		return idProfessor;
	}

	public void setIdProfessor(Long idProfessor) {
		this.idProfessor = idProfessor;
	}

	public Integer getAnoContratacao() {
		return anoContratacao;
	}

	public void setAnoContratacao(Integer anoContratacao) {
		this.anoContratacao = anoContratacao;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

    public String getTitulacao() {
        return titulacao;
    }

    public void setTitulacao(String titulacao) {
        this.titulacao = titulacao;
    }

    public Integer getAnocontratacao() {
        return anoContratacao;
    }

    public void setAnocontratacao(Integer anoContratacao) {
        this.anoContratacao = anoContratacao;
    }

    public List<Pessoa> getAlunos() {
        return pessoas;
    }

    public void adicionaAluno(Pessoa pessoa) {
        this.pessoas.add(pessoa);
    }

    public void removeAluno(Pessoa aluno) {
        this.pessoas.remove(aluno);
    }

}
