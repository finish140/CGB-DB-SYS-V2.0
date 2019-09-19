package com.tedu.cgb.team.test;

import org.junit.Test;

public class TestWithoutSpring {
	
	@Test
	public void testEmail() {
		String regex = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
		String email = "adaeq2_da@asa.dfs";
		System.out.println(email.matches(regex));
	}
}
