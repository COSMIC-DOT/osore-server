package com.dot.osore.core.chat.dto;

import static java.util.stream.Collectors.groupingBy;

import com.dot.osore.core.chat.entity.Chat;
import java.time.format.DateTimeFormatter;
import java.util.List;

public record ChattingContentList(
        String date,
        List<ChattingContent> chatsByDate
) {
    public static List<ChattingContentList> from(List<Chat> chats) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return chats.stream()
                .collect(groupingBy(chat ->chat.getCreatedAt().toLocalDate().format(formatter)))
                .entrySet().stream()
                .map(entry -> new ChattingContentList(
                        entry.getKey(),
                        entry.getValue().stream()
                                .map(chat -> new ChattingContent(
                                        chat.getChat(),
                                        chat.getSender()
                                ))
                                .toList()
                ))
                .toList();
    }
}
