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

## AddRecordExample：Automatic winding add Record example

`AddRecordExample` demonstrates how to add a new record using the Casibase API. Unlike the basic usage, this example specifically sets the `needCommit` parameter to `true`, meaning that the record will be automatically committed to the blockchain after being added, thereby achieving trustworthy data storage. No additional operations are required, and the record is immediately stored on the chain, making it suitable for scenarios with higher requirements for data compliance and traceability.

## Record Add Example

In addition to basic message interaction, this project also provides an example for adding a record.  
In `AddRecordExample`, a new record is added via the Casibase API with the `needCommit` parameter set to `true`. This means the record will be automatically committed to the blockchain after being added, ensuring trusted and tamper-proof storage without extra steps.  
This approach is ideal for scenarios that require higher standards of data compliance and traceability.

```java
// Add a record that is automatically committed to the blockchain
Record record = new Record(
    // ...other parameters...
    true // needCommit, auto-commit to blockchain
    // ...other parameters...
);
recordService.addRecord(record);
```