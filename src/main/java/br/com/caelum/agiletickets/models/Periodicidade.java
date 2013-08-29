package br.com.caelum.agiletickets.models;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Weeks;

public enum Periodicidade {
	DIARIA {
		@Override
		public List<Sessao> criarSessoes(Espetaculo espetaculo,
				LocalDate inicio, LocalDate fim, LocalTime horario) {
			List<Sessao> sessoes = new ArrayList<Sessao>();
			Days dias = Days.daysBetween(inicio, fim);
			for (int i = 0; i <= dias.getDays(); i++) {
				LocalDate inicioSessao = inicio.plusDays(i);
				criarSessao(espetaculo, sessoes, inicioSessao, horario);
			}
			return sessoes;
		}
	},
	SEMANAL {
		@Override
		public List<Sessao> criarSessoes(Espetaculo espetaculo,
				LocalDate inicio, LocalDate fim, LocalTime horario) {
			List<Sessao> sessoes = new ArrayList<Sessao>();
			Weeks semanas = Weeks.weeksBetween(inicio, fim);
			for (int i = 0; i <= semanas.getWeeks(); i++) {
				LocalDate inicioSessao = inicio.plusWeeks(i);
				criarSessao(espetaculo, sessoes, inicioSessao, horario);
			}
			return sessoes;
		}
	};

	public abstract List<Sessao> criarSessoes(Espetaculo espetaculo,
			LocalDate inicio, LocalDate fim, LocalTime horario);

	private static void criarSessao(Espetaculo espetaculo,
			List<Sessao> sessoes, LocalDate data, LocalTime horario) {
		Sessao sessao = new Sessao();
		sessao.setEspetaculo(espetaculo);
		sessao.setInicio(data.toDateTime(horario));
		sessoes.add(sessao);
	}
}