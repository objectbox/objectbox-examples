/*
 * Copyright 2017 ObjectBox Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.objectbox.example.util;

import java.util.Random;

/**
 * Helper class to generate a pre-determined set of random values (strings/ints) .
 */
public class RandomValues {

    // Fixed seed so we generate the same set of strings every time.
    public static final long SEED = -2662502316022774L;
    private static final int MIN_LENGTH = 5;
    private static final int MAX_LENGTH = 500;

    // limit to a fixed set of chars
    private static final char[] CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
            'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    /**
     * Creates the same random sequence of strings.
     */
    public static String[] createFixedRandomStrings(int count) {
        return createFixedRandomStrings(count, MIN_LENGTH, MAX_LENGTH);
    }

    public static String[] createFixedRandomStrings(int count, int minLength, int maxLength) {
        String[] strings = new String[count];
        Random random = new Random(SEED);
        for (int i = 0; i < count; i++) {
            strings[i] = createRandomString(random, minLength, maxLength);
        }
        return strings;
    }

    public static String createRandomString(Random random, int minLength, int maxLength) {
        int length = minLength + random.nextInt(maxLength - minLength);
        return createRandomString(random, length);
    }

    public static String createRandomString(Random random, int length) {
        char[] chars = new char[length + 4];
        for (int i = 0; i < length; ) {
            int intVal = random.nextInt();
            chars[i++] = CHARS[((intVal & 0xff) % CHARS.length)];
            chars[i++] = CHARS[(((intVal >> 8) & 0xff) % CHARS.length)];
            chars[i++] = CHARS[(((intVal >> 16) & 0xff) % CHARS.length)];
            chars[i++] = CHARS[(((intVal >> 24) & 0xff) % CHARS.length)];
        }
        return new String(chars, 0, length);
    }

    /**
     * Creates the same random sequence of indexes. To be used to select strings by {@link
     * #createFixedRandomStrings(int)}.
     */
    public static int[] createFixedRandomInts(int count, int maxInt) {
        int[] ints = new int[count];
        Random random = new Random(SEED);
        for (int i = 0; i < count; i++) {
            ints[i] = random.nextInt(maxInt + 1);
        }
        return ints;
    }
}
