package com.dot.osore.core.chat.dto;

import java.util.List;

public record CreateChattingRoomResponse(
        Long chatRoomId,
        List<ChattingRoomResponse> chattingRoomList
) {
}
