package com.miniblog.back.query.service;

import com.miniblog.back.common.exception.NotFoundException;
import com.miniblog.back.query.dto.internal.CommentDTO;
import com.miniblog.back.query.dto.internal.LikeHistoryDTO;
import com.miniblog.back.query.dto.internal.PostDetailDTO;
import com.miniblog.back.query.dto.response.PostDetailResponseDTO;
import com.miniblog.back.query.repository.QueryRepository;
import com.miniblog.back.query.util.PostLimit;
import com.miniblog.back.query.util.SortType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryService {
    private final QueryRepository queryRepository;

    @Transactional(readOnly = true)
    public PostDetailResponseDTO getPostDetail(Long postId) {

        PostDetailDTO post = queryRepository.getPostDetail(postId)
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));
        List<CommentDTO> comments = queryRepository.getCommentsByPostId(postId);
        List<LikeHistoryDTO> likeHistories = queryRepository.getLikeHistoriesByPostId(postId);

        return PostDetailResponseDTO.of(post, comments, likeHistories);
    };

    @Transactional(readOnly = true)
    public List<PostDetailDTO> getPostsByLatestByMemberId(int page) {
        return queryRepository.getPostDetailListByCondition(null, page, PostLimit.DEFAULT.getValue(), SortType.LATEST);
    }

    @Transactional(readOnly = true)
    public List<PostDetailDTO> getPostsByViewsByMemberId(int page) {
        return queryRepository.getPostDetailListByCondition(null ,page, PostLimit.DEFAULT.getValue(), SortType.VIEWS);
    }

    @Transactional(readOnly = true)
    public List<PostDetailDTO> getPostsByLikes(int page) {
        return queryRepository.getPostDetailListByCondition(null ,page, PostLimit.DEFAULT.getValue(), SortType.LIKES);
    }

    @Transactional(readOnly = true)
    public List<PostDetailDTO> getPostsByLatestByMemberId(Long memberId, int page) {
        return queryRepository.getPostDetailListByCondition(memberId, page, PostLimit.SMALL.getValue(), SortType.LATEST);
    }

    @Transactional(readOnly = true)
    public List<PostDetailDTO> getPostsByViewsByMemberId(Long memberId, int page) {
        return queryRepository.getPostDetailListByCondition(memberId, page, PostLimit.SMALL.getValue(), SortType.VIEWS);
    }

    @Transactional(readOnly = true)
    public List<PostDetailDTO> getPostsByLikes(Long memberId, int page) {
        return queryRepository.getPostDetailListByCondition(memberId, page, PostLimit.SMALL.getValue(), SortType.LIKES);
    }

    @Transactional(readOnly = true)
    public List<PostDetailDTO> getLikedPostsByMemberId(Long memberId, int page) {
        List<Long> postIds = queryRepository.findLikeHistoriesByMemberId(memberId, page, PostLimit.SMALL.getValue(), SortType.LATEST);
        return queryRepository.getPostDetailListByIds(postIds);
    }

    @Transactional(readOnly = true)
    public List<PostDetailDTO> getCommentedPostsByMemberId(Long memberId, int page) {
        List<Long> postIds = queryRepository.findCommentsByMemberId(memberId, page, PostLimit.SMALL.getValue(), SortType.LATEST);
        return queryRepository.getPostDetailListByIds(postIds);
    }

    @Transactional(readOnly = true)
    public List<PostDetailDTO> searchFullTextPostsByMemberId(String keyword, int page) {
        return queryRepository.searchWithFullTestAndCondition(keyword, null, page, PostLimit.DEFAULT.getValue());
    }

    @Transactional(readOnly = true)
    public List<PostDetailDTO> searchFullTextPostsByMemberId(String keyword, Long memberId, int page) {
        return queryRepository.searchWithFullTestAndCondition(keyword, memberId, page, PostLimit.DEFAULT.getValue());
    }
}
