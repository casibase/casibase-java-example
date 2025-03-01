# Casibase Java Example

This project demonstrates how to interact with the Casibase API using Java. It provides examples of sending messages and handling streaming responses from the AI model.

## Main Features

- Send messages to Casibase AI
- Handle streaming responses
- Process AI responses in real-time

## API Functions

### addMessage(String text)
Sends a message to the Casibase API.
```java
addMessage("hi"); // Sends "hi" to the AI
```

### getLastMessageName()
Retrieves the name of the last message in the chat history.
```java
String lastMessageName = getLastMessageName();
```

### getMessageAnswer(String messageName)
Gets the AI's response for a specific message. Handles streaming response with real-time output.
```java
getMessageAnswer(lastMessageName); // Prints AI response in real-time
```

## Usage Example

```java
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
```

## Response Handling

The API uses Server-Sent Events (SSE) for streaming responses. The response is processed in real-time, and the output is printed as it arrives. The stream automatically terminates when receiving a "data: end" message.