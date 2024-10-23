package com.dot.osore.core.chat.dto;

import com.dot.osore.core.chat.entity.ChattingRoom;
import java.util.List;

public record ChattingRoomResponse(
        Long chatRoomId,
        String title,
        List<ChattingContentList> chats
) {

    public static List<ChattingRoomResponse> from(List<ChattingRoom> chattingRooms) {
        return chattingRooms.stream()
                .map(chattingRoom -> new ChattingRoomResponse(
                        chattingRoom.getId(),
                        chattingRoom.getTitle(),
                        ChattingContentList.from(chattingRoom.getChats())
                ))
                .toList();
    }
}
