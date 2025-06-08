#  UDP Chat Application

A real-time chat application implemented using Java and UDP protocol. This application supports both broadcast and private messaging between multiple clients through a central server.

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.java.com) [![UDP](https://img.shields.io/badge/Protocol-UDP-blue?style=for-the-badge)](https://en.wikipedia.org/wiki/User_Datagram_Protocol) [![License](https://img.shields.io/badge/License-MIT-yellow.svg?style=for-the-badge)](https://opensource.org/licenses/MIT)

## ✨ Features

- 📡 Real-time messaging using UDP protocol
- 👥 Support for multiple concurrent clients
- 📢 Broadcast messaging to all connected clients
- 🔒 Private messaging between specific clients
- ⌨️ Simple command-line interface
- 🛑 Graceful server shutdown mechanism

## 📁 Project Structure

```
src/
├── client/
│   ├── Main.java           # Client entry point and user interface
│   ├── Client.java         # Client implementation
│   └── ClientRunnable.java # Handles incoming messages
└── server/
    ├── Main.java           # Server entry point
    ├── Server.java         # Server implementation
    └── ServerThread.java   # Handles client connections and message broadcasting
```

## 🚀 Prerequisites

- ⚙️ Java Development Kit (JDK) 8 or higher
- 📚 Basic understanding of networking concepts

## 🏃‍♂️ How to Run

### 🖥️ Starting the Server

1. Navigate to the project directory
2. Compile the server code:
   ```bash
   javac src/server/*.java
   ```
3. Run the server:
   ```bash
   java -cp src server.Main
   ```

### 👤 Starting a Client

1. In a new terminal, compile the client code:
   ```bash
   javac src/client/*.java
   ```
2. Run the client:
   ```bash
   java -cp src client.Main
   ```

## 📝 Usage

1. 👤 When starting a client, you'll be prompted to enter your name
2. 📢 To send a broadcast message, simply type your message and press Enter
3. 🔒 To send a private message, use the format: `@recipient message`
4. 🚪 To exit the chat, type `exit`

## 🔧 Technical Details

- 🔌 The server runs on port 5000 by default
- 📡 Uses UDP protocol for communication
- 💬 Supports both broadcast and private messaging
- 🔄 Implements thread-safe message handling
- ⚠️ Includes error handling for network operations

## 📨 Message Format

- 📢 Broadcast messages: `broadcast:sender: message`
- 🔒 Private messages: `private:recipient:sender: message`
- 🛑 Server shutdown message: `server_shutdown`

## ⚠️ Limitations

- 💾 No message persistence
- 🔐 No encryption
- 👤 No user authentication
- ✅ No message delivery confirmation

## 🤝 Contributing

Feel free to submit issues and enhancement requests!

## 📄 License

This project is open source and available under the MIT License. 