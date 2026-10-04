package sri.example.customersupportchatbot

data class ChatMessage(
    val message: String,
    val isUser: Boolean,
    val time: String
)