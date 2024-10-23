package com.dot.osore.core.chat.service;

import com.dot.osore.core.chat.constant.ChatSender;
import com.dot.osore.core.chat.dto.ChattingRoomListResponse;
import com.dot.osore.core.chat.dto.ChattingRoomResponse;
import com.dot.osore.core.chat.entity.Chat;
import com.dot.osore.core.chat.entity.ChattingRoom;
import com.dot.osore.core.chat.repository.ChatRepository;
import com.dot.osore.core.chat.repository.ChattingRoomRepository;
import com.dot.osore.core.note.entity.Note;
import com.dot.osore.core.note.service.NoteService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChattingRoomRepository chattingRoomRepository;
    private final NoteService noteService;

    /**
     * 채팅방을 생성하는 메서드
     *
     * @param noteId 노트 아이디
     * @return 채팅방 아이디
     */
    public Long createChatRoom(Long noteId) {
        Note note = noteService.getNoteById(noteId);
        ChattingRoom chatRoom = chattingRoomRepository.save(ChattingRoom.builder().title("Chat").note(note).build());
        return chatRoom.getId();
    }

    /**
     * 채팅방 목록을 가져오는 메서드
     *
     * @param noteId 노트 아이디
     * @return 채팅방 목록
     */
    @Transactional
    public List<ChattingRoomResponse> getChattingRoomList(Long noteId) {
        List<ChattingRoom> chattingRooms = chattingRoomRepository.findByNoteId(noteId);
        return ChattingRoomResponse.from(chattingRooms);
    }

    /**
     * 채팅을 보내는 메서드
     *
     * @param roomId 채팅 방 아이디
     * @param chat   채팅 내용
     */
    public void sendChat(Long roomId, String chat) {
        ChattingRoom chattingRoom = chattingRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("해당 채팅방을 찾을 수 없습니다."));
        chatRepository.save(Chat.builder()
                .chattingRoom(chattingRoom).chat(chat).sender(ChatSender.USER).build());
    }
}
