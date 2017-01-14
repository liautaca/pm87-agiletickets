package br.com.caelum.agiletickets.models;

import static com.google.common.collect.Lists.newArrayList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Weeks;

@Entity
public class Espetaculo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	private String descricao;

	@Enumerated(EnumType.STRING)
	private TipoDeEspetaculo tipo;

	@ManyToOne
	private Estabelecimento estabelecimento;

	@OneToMany(mappedBy = "espetaculo")
	private List<Sessao> sessoes = newArrayList();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoDeEspetaculo getTipo() {
		return tipo;
	}

	public void setTipo(TipoDeEspetaculo tipo) {
		this.tipo = tipo;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public List<Sessao> getSessoes() {
		return sessoes;
	}

	/**
	 * Esse metodo eh responsavel por criar sessoes para o respectivo
	 * espetaculo, dado o intervalo de inicio e fim, mais a periodicidade.
	 * 
	 * O algoritmo funciona da seguinte forma: - Caso a data de inicio seja
	 * 01/01/2010, a data de fim seja 03/01/2010, e a periodicidade seja DIARIA,
	 * o algoritmo cria 3 sessoes, uma para cada dia: 01/01, 02/01 e 03/01.
	 * 
	 * - Caso a data de inicio seja 01/01/2010, a data fim seja 31/01/2010, e a
	 * periodicidade seja SEMANAL, o algoritmo cria 5 sessoes, uma a cada 7
	 * dias: 01/01, 08/01, 15/01, 22/01 e 29/01.
	 * 
	 * Repare que a data da primeira sessao é sempre a data inicial.
	 * 
	 * @param espetaculo
	 */
	public List<Sessao> criaSessoes(LocalDate inicio, LocalDate fim, LocalTime horario, Periodicidade periodicidade) {
		// ALUNO: Não apague esse metodo. Esse sim será usado no futuro! ;)
		List<Sessao> lista = new ArrayList<Sessao>();

		if (periodicidade.equals(Periodicidade.DIARIA)) {
			int intervaloDeDias = Days.daysBetween(inicio, fim).getDays();

			populaListaDiaria(inicio, horario, lista, intervaloDeDias);

		} else {
			int intervalorDeSemamas = Weeks.weeksBetween(inicio, fim).getWeeks();

			populaListaSemanal(inicio, horario, lista, intervalorDeSemamas);
		}
		return lista;
	}

	public void populaListaSemanal(LocalDate inicio, LocalTime horario, List<Sessao> lista, int intervalorDeSemamas) {
		for (int i = 0; i <= intervalorDeSemamas; i++) {
			Sessao sessao = criaSessao(inicio, horario, criaDateTimeSemanal(inicio, horario, i));
			lista.add(sessao);

		}
	}

	public void populaListaDiaria(LocalDate inicio, LocalTime horario, List<Sessao> lista, int intervaloDeDias) {
		for (int i = 0; i <= intervaloDeDias; i++) {
			Sessao sessao = criaSessao(inicio, horario, criaDateTimeDiario(inicio, horario, i));
			lista.add(sessao);
		}
	}

	public DateTime criaDateTimeSemanal(LocalDate inicio, LocalTime horario, int i) {
		LocalDate dataDaSemana = inicio.plusWeeks(i);
		DateTime dataComHorario = dataDaSemana.toDateTime(horario);
		return dataComHorario;
	}

	public Sessao criaSessao(LocalDate inicio, LocalTime horario, DateTime data) {
		Sessao sessao = new Sessao();
		sessao.setEspetaculo(this);
		sessao.setDuracaoEmMinutos(180);
		sessao.setTotalIngressos(100);
		sessao.setPreco(new BigDecimal(50.0));
		sessao.setInicio(data);
		return sessao;
	}

	public DateTime criaDateTimeDiario(LocalDate inicio, LocalTime horario, int qtdDiasParaAdd) {
		LocalDate dataEvento = inicio.plusDays(qtdDiasParaAdd);
		DateTime dataComHorario = dataEvento.toDateTime(horario);
		return dataComHorario;
	}

	public boolean Vagas(int qtd, int min) {
		// ALUNO: Não apague esse metodo. Esse sim será usado no futuro! ;)
		int totDisp = 0;

		for (Sessao s : sessoes) {
			if (s.getIngressosDisponiveis() < min)
				return false;
			totDisp += s.getIngressosDisponiveis();
		}

		if (totDisp >= qtd)
			return true;
		else
			return false;
	}

	public boolean Vagas(int qtd) {
		// ALUNO: Não apague esse metodo. Esse sim será usado no futuro! ;)
		int totDisp = 0;

		for (Sessao s : sessoes) {
			totDisp += s.getIngressosDisponiveis();
		}

		if (totDisp >= qtd)
			return true;
		else
			return false;
	}

}
