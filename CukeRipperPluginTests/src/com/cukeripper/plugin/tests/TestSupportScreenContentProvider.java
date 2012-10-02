package com.cukeripper.plugin.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import northwoods.cukeripper.utils.StepAction;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.cukeripper.plugin.views.ICukeParsingListener;
import com.cukeripper.plugin.views.SupportScreenTreeContentProvider;

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
	public void itGetsTheCorrectElements() {
		Object[] actualActions = supportScreenTreeContentProvider
				.getElements(g_statement);

		int expectedNumberOfActions = g_statement.getAllActions().size();
		assertThat(actualActions, is(notNullValue()));
		assertThat(actualActions.length, is(expectedNumberOfActions));

		for (int i = 0; i < expectedNumberOfActions; i++) {
			StepAction actualAction = (StepAction) actualActions[i];
			StepAction expectedAction = g_statement.getAllActions().get(i);
			assertThat(actualAction, is(notNullValue()));
			assertThat(actualAction, is(expectedAction));
		}

		assertThat("", is(""));

	}
}
