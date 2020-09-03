package com.eventmanager.pachanga.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class HashBuilderTest {

	private String senha = "1234";

	@Test
	void criacaoSenhaTest() {
		String senhaHash = HashBuilder.gerarSenha(senha);
		assertEquals(160, senhaHash.length());
		assertNotEquals(senhaHash, senha);
	}

	@Test
	void compararCertoSenha() {
		String senhaLogin = HashBuilder.gerarSenha(senha);
		boolean senhaCorreta = HashBuilder.compararSenha(senha, senhaLogin);
		assertEquals(true, senhaCorreta);
	}

	@Test
	void compararErradaSenha() {
		String senhaLogin = HashBuilder.gerarSenha("123");
		boolean senhaCorreta = HashBuilder.compararSenha(senha, senhaLogin);
		assertEquals(false, senhaCorreta);
	}

}
