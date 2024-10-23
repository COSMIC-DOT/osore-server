package com.dot.osore.core.chat.dto;

import com.dot.osore.core.chat.constant.ChatSender;

public record ChattingContent(
        String message,
        ChatSender sender
) {
}
