/*
 * 	Copyright 2025 The Casibase Authors. All Rights Reserved.
 *
 * 	Licensed under the Apache License, Version 2.0 (the "License");
 * 	you may not use this file except in compliance with the License.
 * 	You may obtain a copy of the License at
 *
 * 	     http://www.apache.org/licenses/LICENSE-2.0
 *
 * 	Unless required by applicable law or agreed to in writing, software
 * 	distributed under the License is distributed on an "AS IS" BASIS,
 * 	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 	See the License for the specific language governing permissions and
 * 	limitations under the License.
 */

package com.example.util;

import java.util.Random;


public class Util {

    public static String getRandomCode(int length) {
        byte[] stdNums = "0123456789".getBytes();
        byte[] result = new byte[length];
        Random r = new Random(System.nanoTime());
        for (int i = 0; i < length; i++) {
            result[i] = stdNums[r.nextInt(stdNums.length)];
        }
        return new String(result);
    }

    public static String getRandomName(String prefix) {
        return prefix + "-" + getRandomCode(6);
    }

}
