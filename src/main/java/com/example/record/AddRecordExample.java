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

package com.example.record;

import com.example.util.Util;
import org.casbin.casibase.config.Config;
import org.casbin.casibase.entity.Record;
import org.casbin.casibase.service.RecordService;
import org.casbin.casibase.util.http.CasibaseResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class AddRecordExample {

    private static final String TEST_CASIBASE_ENDPOINT = "https://demo-admin.casibase.com";
    private static final String TEST_CLIENT_ID = "af6b5aa958822fb9dc33";
    private static final String TEST_CLIENT_SECRET = "8bc3010c1c951c8d876b1f311a901ff8deeb93bc";
    private static final String TEST_CASIBASE_ORGANIZATION = "casbin";
    private static final String TEST_CASIBASE_APPLICATION = "app-casibase";

    // add-record
    public static void main(String[] args) {
        String name = Util.getRandomName("record");
        Record record = new Record(
                "casbin",
                name,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                TEST_CASIBASE_ORGANIZATION,
                "120.85.97.21",
                "admin",
                "POST",
                "/api/add-store",
                "add-store",
                "",
                "en",
                "",
                "{\"status\":\"ok\",\"msg\":\"\"}",
                true,
                "",
                "",
                "",
                "",
                true
        );

        Config config = new Config(TEST_CASIBASE_ENDPOINT, TEST_CLIENT_ID, TEST_CLIENT_SECRET, TEST_CASIBASE_ORGANIZATION, TEST_CASIBASE_APPLICATION);
        RecordService recordService = new RecordService(config);
        try {
            CasibaseResponse<String, Object> resp = recordService.addRecord(record);
            if (!Objects.equals(resp.getStatus(), "ok")) {
                throw new RuntimeException(String.format("Failed fetching: %s", resp.getMsg()));
            }
            System.out.println(resp.getData());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
