/*
 * Copyright (c) 2017, Adam <Adam@sigterm.info>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package timetracking;

public enum Varbits
{
	/**
	 * Transmog controllers for farming
	 */
	FARMING_4771(4771),
	FARMING_4772(4772),
	FARMING_4773(4773),
	FARMING_4774(4774),
	FARMING_4775(4775),
	FARMING_7904(7904),
	FARMING_7905(7905),
	FARMING_7906(7906),
	FARMING_7907(7907),
	FARMING_7908(7908),
	FARMING_7909(7909),
	FARMING_7910(7910),
	FARMING_7911(7911),
	FARMING_7912(7912),

	/**
	 * Transmog controllers for grapes
	 */
	GRAPES_4953(4953),
	GRAPES_4954(4954),
	GRAPES_4955(4955),
	GRAPES_4956(4956),
	GRAPES_4957(4957),
	GRAPES_4958(4958),
	GRAPES_4959(4959),
	GRAPES_4960(4960),
	GRAPES_4961(4961),
	GRAPES_4962(4962),
	GRAPES_4963(4963),
	GRAPES_4964(4964),

	/**
	 * Automatically weed farming patches
	 */
	AUTOWEED(5557),
	;

	/**
	 * The raw varbit ID.
	 */
	private final int id;

	Varbits(int id)
	{
		this.id = id;
	}

	public int getId()
	{
		return id;
	}
}
