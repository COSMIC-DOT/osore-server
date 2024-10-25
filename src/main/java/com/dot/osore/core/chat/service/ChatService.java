package com.dot.osore.core.chat.service;

import com.dot.osore.core.chat.constant.ChatSender;
import com.dot.osore.core.chat.dto.ChatRequest;
import com.dot.osore.core.chat.dto.ChattingRoomResponse;
import com.dot.osore.core.chat.dto.EmbeddingRequest;
import com.dot.osore.core.chat.entity.Chat;
import com.dot.osore.core.chat.entity.ChattingRoom;
import com.dot.osore.core.chat.repository.ChatRepository;
import com.dot.osore.core.chat.repository.ChattingRoomRepository;
import com.dot.osore.core.note.entity.Note;
import com.dot.osore.core.note.service.NoteService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChattingRoomRepository chattingRoomRepository;
    private final NoteService noteService;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * 채팅방을 생성하는 메서드
     *
     * @param noteId 노트 아이디
     * @return 채팅방 아이디
     */
    public Long createChatRoom(Long noteId) {
        Note note = noteService.getNoteById(noteId);
        ChattingRoom chatRoom = chattingRoomRepository.save(ChattingRoom.builder().title("Chat").note(note).build());
        createChatEmbedding(chatRoom.getId(), note.getUrl());
        return chatRoom.getId();
    }

    /**
     * AI 서버에 embedding 요청하는 메서드
     *
     * @param roomId 채팅방 아이디
     * @param url 노트 URL
     */
    private void createChatEmbedding(Long roomId, String url) {
        EmbeddingRequest requestBody = new EmbeddingRequest(roomId, url);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<EmbeddingRequest> requestEntity = new HttpEntity<>(requestBody, headers);

        String aiServerUrl = "http://host.docker.internal:8000/api/embedding";

        restTemplate.exchange(
                aiServerUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );
    }

    /**
     * 채팅방 목록을 가져오는 메서드
     *
     * @param noteId 노트 아이디
     * @return 채팅방 목록
     */
    @Transactional
    public List<ChattingRoomResponse> getChattingRoomList(Long noteId) {
        List<ChattingRoom> chattingRooms = chattingRoomRepository.findByNoteIdOrderByCreatedAtDesc(noteId);
        return ChattingRoomResponse.from(chattingRooms);
    }

    /**
     * 채팅을 보내는 메서드
     *
     * @param roomId 채팅 방 아이디
     * @param chat   채팅 내용
     */
    @Transactional
    public void sendChat(Long roomId, String chat) {
        String message = sendChatMessage(roomId, chat);

        ChattingRoom chattingRoom = chattingRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("해당 채팅방을 찾을 수 없습니다."));

        if (chattingRoom.getTitle().equals("Chat")) {
            setNewTitle(chat, chattingRoom);
        }

        chatRepository.save(Chat.builder()
                .chattingRoom(chattingRoom).chat(chat).sender(ChatSender.USER).build());
        chatRepository.save(Chat.builder()
                .chattingRoom(chattingRoom).chat(message).sender(ChatSender.SORE).build());
    }

    private void setNewTitle(String chat, ChattingRoom chattingRoom) {
        String[] titleWords = chat.split(" ");
        if (titleWords.length < 3) {
            chattingRoom.setTitle(chat);
            chattingRoomRepository.save(chattingRoom);
        }
        else {
            String title = titleWords[0] + " " + titleWords[1] + " " + titleWords[2];
            chattingRoom.setTitle(title);
            chattingRoomRepository.save(chattingRoom);
        }
    }

    /**
     * AI 서버에 채팅을 보내는 메서드
     *
     * @param roomId 채팅방 아이디
     *
     */
    private String sendChatMessage(Long roomId, String chat) {
        ChatRequest requestBody = new ChatRequest(roomId, chat);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<ChatRequest> requestEntity = new HttpEntity<>(requestBody, headers);

        String aiServerUrl = "http://host.docker.internal:8000/api/chat";

        ResponseEntity<String> response = restTemplate.exchange(
                aiServerUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        String responseBody = response.getBody();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);
            return rootNode.path("answer").asText();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
