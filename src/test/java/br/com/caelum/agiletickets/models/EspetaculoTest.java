package br.com.caelum.agiletickets.models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Locale;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;
import org.testng.Assert;

public class EspetaculoTest {

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(5));
	}

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(6));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(15));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(5, 3));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(10, 3));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(5, 3));
	}

	private Sessao sessaoComIngressosSobrando(int quantidade) {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(quantidade * 2);
		sessao.setIngressosReservados(quantidade);

		return sessao;
	}

	@Test
	public void deveCriar1SessaoParaDataIguaisEPeriodicidadeDiaria() {
		LocalDate inicio = new LocalDate();
		LocalDate fim = new LocalDate(inicio);
		LocalTime horario = new LocalTime();
		Periodicidade diaria = Periodicidade.DIARIA;

		Espetaculo espetaculo = new Espetaculo();
		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario,
				diaria);

		Assert.assertEquals(1, sessoes.size());
	}

	@Test
	public void deveCriar1SessaoParaDataIguaisEPeriodicidadeSemanal() {
		LocalDate inicio = new LocalDate();
		LocalDate fim = new LocalDate(inicio);
		LocalTime horario = new LocalTime();
		Periodicidade semanal = Periodicidade.SEMANAL;

		Espetaculo espetaculo = new Espetaculo();
		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario,
				semanal);

		Assert.assertEquals(1, sessoes.size());
	}

	@Test
	public void deveCriar11SessoesPara10DiasEPeriodicidadeDiaria() {
		LocalDate inicio = new LocalDate();
		LocalDate fim = inicio.plusDays(10);
		LocalTime horario = new LocalTime();
		Periodicidade periodicidade = Periodicidade.DIARIA;

		Espetaculo espetaculo = new Espetaculo();
		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario,
				periodicidade);

		Assert.assertEquals(11, sessoes.size());
	}

	@Test
	public void deveCriar2SessoesPara10DiasEPeriodicidadeSemanal() {
		LocalDate inicio = new LocalDate();
		LocalDate fim = inicio.plusDays(10);
		LocalTime horario = new LocalTime();
		Periodicidade periodicidade = Periodicidade.SEMANAL;

		Espetaculo espetaculo = new Espetaculo();
		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario,
				periodicidade);

		Assert.assertEquals(sessoes.size(), 2);
	}

	@Test
	public void deveCriar2SessoesPara14DiasEPeriodicidadeSemanal() {
		LocalDate inicio = new LocalDate();
		LocalDate fim = inicio.plusDays(13);
		LocalTime horario = new LocalTime();
		Periodicidade periodicidade = Periodicidade.SEMANAL;

		Espetaculo espetaculo = new Espetaculo();
		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario,
				periodicidade);

		Assert.assertEquals(sessoes.size(), 3);
		for (int i = 0; i < 2; i++) {
			Assert.assertSame(sessoes.get(i).getEspetaculo(), espetaculo);			
		}
		Assert.assertEquals(sessoes.get(0).getDia(), inicio.toString(DateTimeFormat.shortDate().withLocale(
				new Locale("pt", "BR"))));
		Assert.assertEquals(sessoes.get(1).getDia(), inicio.plusDays(7).toString(DateTimeFormat.shortDate().withLocale(
				new Locale("pt", "BR"))));
		

	}

	@Test
	public void naoDeveCriarSessoesQuandoDataInicioForMaiorQueDataFimEPeriodicidadeDiaria() {
		LocalDate fim = new LocalDate();
		LocalDate inicio = fim.plusDays(1);
		LocalTime horario = new LocalTime();
		Periodicidade periodicidade = Periodicidade.DIARIA;

		Espetaculo espetaculo = new Espetaculo();
		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario,
				periodicidade);

		Assert.assertEquals(sessoes.size(), 0);
	}

}
