package com.cukeripper.plugin.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import northwoods.cukeripper.utils.CukeScreen;
import northwoods.cukeripper.utils.LoadedCukes;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.cukeripper.plugin.views.ICukeParsingListener;
import com.cukeripper.plugin.views.providers.content.SupportScreenTreeContentProvider;

public class TestSupportScreenContentProvider extends BaseParseTesting {

	private SupportScreenTreeContentProvider supportScreenTreeContentProvider;

	@Before
	public void Setup() {
		super.Setup();

		supportScreenTreeContentProvider = new SupportScreenTreeContentProvider(
				Mockito.mock(ICukeParsingListener.class), reader);
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	@Test
	public void itGetsTheCorrectElementsForStatement() {
		Object[] actualScreens = supportScreenTreeContentProvider
				.getElements(null);

		int expectedNumberOfScreens = screenNames.length;
		assertThat(actualScreens, is(notNullValue()));
		assertThat(actualScreens.length, is(expectedNumberOfScreens));

		for (int i = 0; i < expectedNumberOfScreens; i++) {
			CukeScreen actualScreen = (CukeScreen) actualScreens[i];
			CukeScreen expectedScreen = (CukeScreen) LoadedCukes.getScreens()
					.toArray()[i];
			assertThat(actualScreen, is(notNullValue()));
			assertThat(actualScreen, is(expectedScreen));
		}

		assertThat("", is(""));

	}
}
