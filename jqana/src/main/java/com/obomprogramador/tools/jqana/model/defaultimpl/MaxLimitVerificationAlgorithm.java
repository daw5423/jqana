package com.obomprogramador.tools.jqana.model.defaultimpl;

import com.obomprogramador.tools.jqana.model.LimitVerificationAlgorithm;

public class MaxLimitVerificationAlgorithm implements
		LimitVerificationAlgorithm {

	private double maxLimit;
	public MaxLimitVerificationAlgorithm(double maxLimit) {
		super();
		this.maxLimit = maxLimit;
	}

	@Override
	public boolean verify(double value) {
		boolean returnCode = false;
		if (value > maxLimit) {
			returnCode = true;
		}
		return returnCode;
	}

}
