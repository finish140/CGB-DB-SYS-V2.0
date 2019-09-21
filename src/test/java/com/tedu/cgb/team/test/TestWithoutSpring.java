package com.tedu.cgb.team.test;

import org.junit.Test;

public class TestWithoutSpring {
	
	@Test
	public void testRegex() {
		String regex = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
		String email = "adaeq2_da@asa.dfs";
		System.out.println(email.matches(regex));
		
		
		regex = "(?!^0*(\\.0{1,2})?$)^\\d{1,13}(\\.\\d{1,2})?$";
		String price = "4315.22";
		System.out.println(price.matches(regex));
		
		regex = "(https?:\\/\\/)?([\\da-z\\.\\/-]+)\\.(jpg|bmp|gif|jpeg|png)?/*";
		String img = "http://localhost/ghja/nk-lca.jpg/";
		System.out.println(img.matches(regex));
	}
}
