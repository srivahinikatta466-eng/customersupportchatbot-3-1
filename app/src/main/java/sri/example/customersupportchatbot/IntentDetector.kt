package sri.example.customersupportchatbot

class IntentDetector {

    fun getResponse(userMessage: String): String {

        val message = userMessage
            .lowercase()
            .trim()

        return when {

            // ================= GREETING =================

            containsAny(
                message,
                "hi",
                "hello",
                "hey",
                "good morning",
                "good afternoon",
                "good evening"
            ) -> {
                """
                👋 Hello!

                Welcome to Customer Support.

                I can help you with:
                • Orders
                • Delivery
                • Payments
                • Refunds
                • Returns
                • Cancellations

                What would you like help with?
                """.trimIndent()
            }


            // ================= ORDER TRACKING =================

            containsAny(
                message,
                "track my order",
                "track order",
                "order tracking",
                "order status",
                "where is my order",
                "where's my order",
                "my order"
            ) -> {
                """
                📦 ORDER TRACKING

                I can help you check your order status.

                Please provide your Order ID to continue.

                Example:
                ORD12345
                """.trimIndent()
            }


            // ================= DELIVERY =================

            containsAny(
                message,
                "delivery",
                "shipping",
                "shipment",
                "deliver",
                "arrive",
                "delivery time",
                "when will it arrive"
            ) -> {
                """
                🚚 DELIVERY INFORMATION

                Standard delivery usually takes 3–7 business days.

                Delivery time may vary depending on your location and the shipping method.

                For tracking information, please provide your Order ID.
                """.trimIndent()
            }


            // ================= PAYMENT =================

            containsAny(
                message,
                "payment failed",
                "payment problem",
                "payment issue",
                "transaction failed",
                "card payment",
                "payment"
            ) -> {
                """
                💳 PAYMENT SUPPORT

                If your payment failed, please check:

                • Internet connection
                • Card/payment details
                • Available balance
                • Bank transaction status

                You can then try the payment again.

                If money was deducted but the order was not placed, please contact support with your transaction details.
                """.trimIndent()
            }


            // ================= REFUND =================

            containsAny(
                message,
                "refund",
                "money back",
                "refund status",
                "get my money back"
            ) -> {
                """
                💰 REFUND SUPPORT

                Refunds are processed after the request is approved.

                The amount is normally returned to the original payment method.

                Please provide your Order ID if you need help checking a refund.
                """.trimIndent()
            }


            // ================= RETURN =================

            containsAny(
                message,
                "return",
                "return product",
                "return item",
                "send product back",
                "replace product",
                "replacement"
            ) -> {
                """
                🔄 RETURN & REPLACEMENT

                You can request a return or replacement according to the applicable return policy.

                Please provide your Order ID and briefly explain the reason for the return.
                """.trimIndent()
            }


            // ================= CANCELLATION =================

            containsAny(
                message,
                "cancel order",
                "cancel my order",
                "cancel order please",
                "order cancellation",
                "cancellation"
            ) -> {
                """
                ❌ ORDER CANCELLATION

                If your order has not been shipped, it may be possible to cancel it.

                Please provide your Order ID so we can guide you further.
                """.trimIndent()
            }


            // ================= ACCOUNT =================

            containsAny(
                message,
                "account",
                "login",
                "log in",
                "sign in",
                "password",
                "forgot password"
            ) -> {
                """
                🔐 ACCOUNT SUPPORT

                I can help with basic account-related questions.

                If you forgot your password, use the "Forgot Password" option on the login screen.

                For account-specific problems, please contact customer support.
                """.trimIndent()
            }


            // ================= HUMAN SUPPORT =================

            containsAny(
                message,
                "human",
                "agent",
                "customer service",
                "talk to someone",
                "speak to someone",
                "real person"
            ) -> {
                """
                👨‍💼 CUSTOMER SERVICE

                I understand that you may want to speak with a support representative.

                Please contact the customer service team for direct assistance.
                """.trimIndent()
            }


            // ================= GENERAL HELP =================

            containsAny(
                message,
                "help",
                "what can you do",
                "how can you help",
                "support"
            ) -> {
                """
                🤖 HOW I CAN HELP

                I can assist you with:

                📦 Order tracking
                🚚 Delivery information
                💳 Payment problems
                💰 Refunds
                🔄 Returns & replacements
                ❌ Order cancellation
                🔐 Account support

                Type your question or select one of the quick actions below.
                """.trimIndent()
            }


            // ================= THANK YOU =================

            containsAny(
                message,
                "thank you",
                "thanks",
                "thank u"
            ) -> {
                """
                😊 You're welcome!

                I'm happy to help.

                Is there anything else you need?
                """.trimIndent()
            }


            // ================= GOODBYE =================

            containsAny(
                message,
                "bye",
                "goodbye",
                "see you",
                "that's all"
            ) -> {
                """
                👋 Goodbye!

                Thank you for contacting Customer Support.

                Have a great day!
                """.trimIndent()
            }


            // ================= FALLBACK =================

            else -> {
                """
                🤔 I'M NOT SURE I UNDERSTOOD

                I can currently help with:

                📦 Orders
                🚚 Delivery
                💳 Payments
                💰 Refunds
                🔄 Returns
                ❌ Cancellations
                🔐 Account support

                Please try asking your question in a different way.
                """.trimIndent()
            }
        }
    }


    // =====================================================
    // KEYWORD MATCHING
    // =====================================================

    private fun containsAny(
        message: String,
        vararg keywords: String
    ): Boolean {

        return keywords.any { keyword ->
            message.contains(keyword)
        }
    }
}