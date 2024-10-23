package com.dot.osore.core.chat.dto;

import static java.util.stream.Collectors.groupingBy;

import com.dot.osore.core.chat.entity.Chat;
import java.time.LocalDateTime;
import java.util.List;

public record ChattingContentList(
        LocalDateTime date,
        List<ChattingContent> chatsByDate
) {
    public static List<ChattingContentList> from(List<Chat> chats) {
        return chats.stream()
                .collect(groupingBy(Chat::getCreatedAt))
                .entrySet().stream()
                .map(entry -> new ChattingContentList(
                        entry.getKey(),
                        entry.getValue().stream()
                                .map(chat -> new ChattingContent(
                                        chat.getMessage(),
                                        chat.getSender()
                                ))
                                .toList()
                ))
                .toList();
    }
}
