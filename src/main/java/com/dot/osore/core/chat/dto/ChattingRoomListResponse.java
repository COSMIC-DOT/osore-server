package com.dot.osore.core.chat.dto;

import java.util.List;

public record ChattingRoomListResponse(
        List<ChattingRoomResponse> chattingRoomList
) {
}

