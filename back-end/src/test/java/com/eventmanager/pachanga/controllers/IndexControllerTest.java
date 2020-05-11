package com.eventmanager.pachanga.controllers;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import com.eventmanager.pachanga.services.UsuarioService;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value=IndexController.class)
public class IndexControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UsuarioService userService;

	@Test
	public void HelloWorldTest() throws Exception{
		String uri = "/";
		String expected = "Hello World";

		mockMvc.perform(get(uri))
        	.andExpect(status().isOk())
        	.andExpect(content().string(expected));
	}
	
}
