package com.housing.back.service;


import com.housing.back.common.JwtUtils;
import com.housing.back.dto.request.comment.CommentRequestDto;
import com.housing.back.dto.request.comment.DeleteCommentRequestDto;
import com.housing.back.dto.request.comment.EditCommentRequestDto;
import com.housing.back.dto.response.post.CreateCommentResponseDto;
import com.housing.back.dto.response.post.DeleteCommentResponseDto;
import com.housing.back.dto.response.post.EditCommentResponseDto;
import com.housing.back.entity.CommentEntity;
import com.housing.back.entity.NickNameEntity;
import com.housing.back.entity.PostEntity;
import com.housing.back.repository.CommentRepository;
import com.housing.back.repository.NicknameRepository;
import com.housing.back.repository.PostRepository;


import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private NicknameRepository nicknameRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Transactional
    public ResponseEntity<CreateCommentResponseDto> createComment(HttpServletRequest request, CommentRequestDto commentRequestDto) {
        String token = request.getHeader("Authorization").substring(7);
        String userId = jwtUtils.extractUserId(token);

        // 닉네임 변환
        Optional<NickNameEntity> nicknameEntityOptional = nicknameRepository.findByUserId(userId);
        if (!nicknameEntityOptional.isPresent()) {
            throw new RuntimeException("닉네임을 찾을 수 없습니다.");
        }

        String nickname = nicknameEntityOptional.get().getNickname();

        // 포스트 찾기
        Optional<PostEntity> postEntityOptional = postRepository.findById(commentRequestDto.getPostId());
        if (!postEntityOptional.isPresent()) {
            throw new RuntimeException("포스트를 찾을 수 없습니다.");
        }

        PostEntity postEntity = postEntityOptional.get();

        // 댓글 생성
        CommentEntity comment = new CommentEntity();
        comment.setNickname(nickname);
        comment.setContent(commentRequestDto.getContent());
        comment.setPost(postEntity);
        commentRepository.save(comment);

        return CreateCommentResponseDto.success(nickname, commentRequestDto.getContent());
    }

    @Transactional
    public ResponseEntity<DeleteCommentResponseDto> deleteComment(HttpServletRequest request, DeleteCommentRequestDto deleteCommentRequestDto) {
        String token = request.getHeader("Authorization").substring(7);
        String userId = jwtUtils.extractUserId(token);

        Optional<NickNameEntity> nicknameEntityOptional = nicknameRepository.findByUserId(userId);
        if (!nicknameEntityOptional.isPresent()) {
            throw new RuntimeException("닉네임을 찾을 수 없습니다.");
        }

        String nickname = nicknameEntityOptional.get().getNickname();

        Optional<CommentEntity> commentEntityOptional = commentRepository.findById(deleteCommentRequestDto.getCommentId());
        if (!commentEntityOptional.isPresent()) {
            throw new RuntimeException("댓글을 찾을 수 없습니다.");
        }

        CommentEntity commentEntity = commentEntityOptional.get();

        if (!commentEntity.getNickname().equals(nickname)) {
            throw new RuntimeException("권한이 없습니다.");
        }

        commentRepository.delete(commentEntity);

        return DeleteCommentResponseDto.success("댓글이 성공적으로 삭제되었습니다.");
    }

    @Transactional
    public ResponseEntity<EditCommentResponseDto> editComment(HttpServletRequest request, EditCommentRequestDto editCommentRequestDto) {
        String token = request.getHeader("Authorization").substring(7);
        String userId = jwtUtils.extractUserId(token);

        Optional<NickNameEntity> nicknameEntityOptional = nicknameRepository.findByUserId(userId);
        if (!nicknameEntityOptional.isPresent()) {
            throw new RuntimeException("닉네임을 찾을 수 없습니다.");
        }

        String nickname = nicknameEntityOptional.get().getNickname();

        Optional<CommentEntity> commentEntityOptional = commentRepository.findById(editCommentRequestDto.getCommentId());
        if (!commentEntityOptional.isPresent()) {
            throw new RuntimeException("댓글을 찾을 수 없습니다.");
        }

        CommentEntity commentEntity = commentEntityOptional.get();

        if (!commentEntity.getNickname().equals(nickname)) {
            throw new RuntimeException("권한이 없습니다.");
        }

        commentEntity.setContent(editCommentRequestDto.getContent());
        commentRepository.save(commentEntity);

        return EditCommentResponseDto.success(editCommentRequestDto.getContent());
    }

}