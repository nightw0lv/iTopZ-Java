/*
 * Copyright (c) 2021 iTopZ
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package itopz.com.util;

import itopz.com.Configurations;

/**
 * @Author Nightwolf
 * iToPz Discord: https://discord.gg/KkPms6B5aE
 * @Author Rationale
 * Base structure credits goes on Rationale Discord: Rationale#7773
 *
 * Vote Donation System
 * Script website: https://itopz.com/
 * Script version: 1.0
 * Pack Support: Mobius 7.0 Prelude Of War
 *
 * Personal Donate Panels: https://www.denart-designs.com/
 * Free Donate panel: https://itopz.com/
 */
public enum URL
{
    ITOPZ_GLOBAL_URL("https://itopz.com/check/" + Configurations.ITOPZ_SERVER_API_KEY + "/" + Configurations.ITOPZ_SERVER_ID + "/"),
    ITOPZ_INDIVIDUAL_URL("https://itopz.com/check/" + Configurations.ITOPZ_SERVER_API_KEY + "/" + Configurations.ITOPZ_SERVER_ID + "/%IP%"),
    ITOPZ_SERVER_URL("https://itopz.com/info/" + Configurations.ITOPZ_SERVER_ID),
    ITOPZ_URL("https://itopz.com/"),
    DISCORD("https://discord.gg/KkPms6B5aE"),
    DENART_DESIGNS("https://www.denart-designs.com");

    private final String _text;

    /**
     * @param text String
     */
    URL(final String text)
    {
        _text = text;
    }

    /**
     * (non-Javadoc)
     *
     * @see Enum#toString()
     */
    @Override
    public String toString()
    {
        return _text;
    }
}