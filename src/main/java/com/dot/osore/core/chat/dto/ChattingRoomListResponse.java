package com.dot.osore.core.chat.dto;

import com.dot.osore.core.chat.entity.ChattingRoom;
import java.util.List;

public record ChattingRoomListResponse(
        Long chatRoomId,
        String title,
        List<ChattingContentList> chats
) {

    public static List<ChattingRoomListResponse> from(List<ChattingRoom> chattingRooms) {
        return chattingRooms.stream()
                .map(chattingRoom -> new ChattingRoomListResponse(
                        chattingRoom.getId(),
                        chattingRoom.getNote().getTitle(),
                        ChattingContentList.from(chattingRoom.getChats())
                ))
                .toList();
    }
}
