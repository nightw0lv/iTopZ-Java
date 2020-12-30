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

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Nightwolf
 * iToPz Discord: https://discord.gg/KkPms6B5aE
 * @Author Rationale
 * Base structure credits goes on Rationale Discord: Rationale#7773
 *
 * Vote Donation System
 * Script website: https://itopz.com/
 * Script version: 1.0
 * Pack Support: Mobius 5.5 Etinas Fate
 *
 * Personal Donate Panels: https://www.denart-designs.com/
 * Free Donate panel: https://itopz.com/
 */
public class Json
{
    private final Map<String, String> data = new HashMap<>();

    /**
     * set body of json array
     *
     * @param text string
     */
    public Json(String text)
    {
        for (String s : text.replaceAll("[{}\"]", "").replace("result:", "").split(","))
        {
            if (s == null)
                continue;
            String[] d = s.split(":");
            if (d[0] == null)
                continue;
            try
            {
                data.put(d[0].trim(), d[1].trim());
            }
            catch(IndexOutOfBoundsException ioobe)
            {
                System.out.println(ioobe.getMessage());
            }
        }
    }

    /**
     * return string value from map
     *
     * @param key String
     * @return string
     */
    public String getString(String key)
    {
        return data.containsKey(key) ? String.valueOf(data.getOrDefault(key, "")) : null;
    }

    /**
     * return integer value from map
     *
     * @param key String
     * @return int
     */
    public Integer getInteger(String key)
    {
        return data.containsKey(key) ? Integer.parseInt(data.getOrDefault(key, "-2")) : -2;
    }

    /**
     * Return boolean value from map
     *
     * @param key String
     * @return boolean
     */
    public boolean getBoolean(String key)
    {
        return data.containsKey(key) ? Boolean.parseBoolean(data.getOrDefault(key, "false")) : false;
    }

    /**
     * Return long value from map
     *
     * @param key String
     * @return long
     */
    public long getLong(String key)
    {
        return data.containsKey(key) ? Long.parseLong(data.getOrDefault(key, "-1")) : -1;
    }
}
