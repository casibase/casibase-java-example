package com.example;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import okhttp3.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CasibaseApiExample {
    private static final String CHAT_ID = "chat_5p1w7l";
    private static final String BASE_URL = "https://ai-admin.casibase.com";
    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new Gson();

    public static void main(String[] args) {
        try {
            // Send message
            addMessage("hi");
            String lastMessageName = getLastMessageName();

            // Get AI response
            if (lastMessageName != null) {
                getMessageAnswer(lastMessageName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addMessage(String text) throws IOException {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("owner", "admin");
        String messageId = UUID.randomUUID().toString();
        requestBody.put("name", "message_" + messageId);
        ZonedDateTime now = ZonedDateTime.now();
        String formattedTime = now.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        requestBody.put("createdTime", formattedTime);
        requestBody.put("organization", "casbin");
        requestBody.put("user", "admin");
        requestBody.put("chat", CHAT_ID);
        requestBody.put("replyTo", "");
        requestBody.put("author", "admin");
        requestBody.put("text", text);
        requestBody.put("isHidden", false);
        requestBody.put("isDeleted", false);
        requestBody.put("isAlerted", false);
        requestBody.put("isRegenerated", false);
        requestBody.put("fileName", "");

        String jsonBody = gson.toJson(requestBody);

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/add-message")
                .post(RequestBody.create(jsonBody, MediaType.parse("text/plain;charset=UTF-8")))
                .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:135.0) Gecko/20100101 Firefox/135.0")
                .addHeader("Accept", "*/*")
                .addHeader("Accept-Language", "null;q=0.9,en;q=0.8")
                .addHeader("Accept-Encoding", "gzip, deflate, br, zstd")
                .addHeader("Referer", BASE_URL + "/chat/" + CHAT_ID)
                .addHeader("Content-Type", "text/plain;charset=UTF-8")
                .addHeader("Origin", BASE_URL)
                .addHeader("Connection", "keep-alive")
                .addHeader("Cookie", "casibase_session_id=b8bd6aa12898ca70d7303f152973e931")
                .addHeader("Sec-Fetch-Dest", "empty")
                .addHeader("Sec-Fetch-Mode", "cors")
                .addHeader("Sec-Fetch-Site", "same-origin")
                .addHeader("Priority", "u=0")
                .addHeader("Pragma", "no-cache")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("TE", "trailers")
                .build();

        client.newCall(request).execute();
    }

    private static String getLastMessageName() throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL + "/api/get-messages").newBuilder();
        urlBuilder.addQueryParameter("owner", "admin");
        urlBuilder.addQueryParameter("chat", CHAT_ID);

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .get()
                .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:135.0) Gecko/20100101 Firefox/135.0")
                .addHeader("Accept", "*/*")
                .addHeader("Accept-Language", "null;q=0.9,en;q=0.8")
                .addHeader("Accept-Encoding", "gzip, deflate, br, zstd")
                .addHeader("Referer", BASE_URL + "/chat/" + CHAT_ID)
                .addHeader("Connection", "keep-alive")
                .addHeader("Cookie", "casibase_session_id=b8bd6aa12898ca70d7303f152973e931")
                .addHeader("Sec-Fetch-Dest", "empty")
                .addHeader("Sec-Fetch-Mode", "cors")
                .addHeader("Sec-Fetch-Site", "same-origin")
                .addHeader("Priority", "u=4")
                .addHeader("Pragma", "no-cache")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("TE", "trailers")
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JsonObject jsonResponse = gson.fromJson(responseBody, JsonObject.class);
        JsonArray data = jsonResponse.getAsJsonArray("data");

        if (data != null && data.size() > 0) {
            JsonElement lastMessage = data.get(data.size() - 1);
            return lastMessage.getAsJsonObject().get("name").getAsString();
        }

        return null;
    }

    private static void getMessageAnswer(String messageName) throws IOException {
        String messageId = "admin/" + messageName;
        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL + "/api/get-message-answer").newBuilder();
        urlBuilder.addQueryParameter("id", messageId);

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .get()
                .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:135.0) Gecko/20100101 Firefox/135.0")
                .addHeader("Accept", "text/event-stream")
                .addHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2")
                .addHeader("Accept-Encoding", "gzip, deflate, br, zstd")
                .addHeader("Connection", "keep-alive")
                .addHeader("Referer", BASE_URL + "/chat/" + CHAT_ID)
                .addHeader("Cookie", "casibase_session_id=b8bd6aa12898ca70d7303f152973e931")
                .addHeader("Sec-Fetch-Dest", "empty")
                .addHeader("Sec-Fetch-Mode", "cors")
                .addHeader("Sec-Fetch-Site", "same-origin")
                .addHeader("Priority", "u=4")
                .addHeader("Pragma", "no-cache")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("TE", "trailers")
                .build();

        Response response = client.newCall(request).execute();

        ResponseBody body = response.body();
        if (body == null) return;

        BufferedReader reader = new BufferedReader(new InputStreamReader(body.byteStream()));
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("data:")) {
                    if (line.equals("data: end")) break;
                    System.out.print(line.substring(15, line.length() - 2));
                }
            }
        } finally {
            reader.close();
        }
    }
}