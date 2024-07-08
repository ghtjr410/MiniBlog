import React, { useCallback, useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import Modal from 'components/Modal/Modal';
import CommonHeaderSection from 'sections/CommonHeaderSection/CommonHeaderSection';
import CommonDropDownSection from 'sections/CommonDropDownSection/CommonDropDownSection';
import { combinedLogoutHandler } from 'services/LogOutService/logOutService';
import useNavigateHelper from 'utils/NavigationUtil/navigationUtil';
import { checkPostOwner, fetchPostById, isLiked, clickPostLike, deleteMyPost, addComment, fetchPostByIdWithAuth, deleteComment, editComment } from 'services/Auth/authService';
import TestForm from 'forms/TestForm/TestForm';
import { HeaderProvider, useHeader } from 'services/HeaderService/HeaderService';
import { useAuth } from 'hooks/useAuthHook';

type Comment = {
  commentId: number;
  nickname: string;
  content: string;
  createdAt: string;
  isOwner: boolean;
};

const TestPostContainer: React.FC = () => {
  const { navigateToCreateNickname, navigateToLogin, navigateToUserBlog, navigateToCreatePost, navigateToHome, navigateToEditEditPost } = useNavigateHelper();
  const { dropdownOpen, toggleDropdown } = useHeader();
  const [logOutModalOpen, setLogOutModalOpen] = useState(false);
  const [isLoginUser, setIsLoginUser] = useState(false);
  const [refresh, setRefresh] = useState(false);
  const [blogNickname, setBlogNickname] = useState<string>('');
  const [thisPostId, setThisPostId] = useState<string>('');
  const [postContent, setPostContent] = useState<any>(null);
  const [isOwner, setIsOwner] = useState<boolean>(false);
  const [likeCount, setLikeCount] = useState<string>('0');
  const [hasLiked, setHasLiked] = useState<boolean>(false);
  const [comments, setComments] = useState<Comment[]>([]);
  const [loginRequiredModalOpen, setLoginRequiredModalOpen] = useState<boolean>(false);
  const [deleteModalOpen, setDeleteModalOpen] = useState<boolean>(false);
  const [commentDeleteModalOpen, setCommentDeleteModalOpen] = useState<boolean>(false); // 댓글 삭제 모달 상태 추가
  const [commentEditModalOpen, setCommentEditModalOpen] = useState<boolean>(false); // 댓글 수정 모달 상태 추가
  const { postId, nickname } = useParams<{ postId: string; nickname: string }>();

  const { hasNickname, myNickname, nicknameModalOpen, setNicknameModalOpen } = useAuth();
  const navigate = useNavigate();

  const handleCheckPostOwner = async (postId: string) => {
    if (postId) {
      const ownerStatus = await checkPostOwner(postId);
      setIsOwner(ownerStatus);
      if (ownerStatus) {
        console.log('소유자입니다. 추가 작업을 수행합니다.');
      } else {
        console.log('소유자가 아닙니다. 다른 작업을 수행합니다.');
      }
    }
  };

  const fetchPostData = async (postId: string) => {
    try {
      let data;
      if (hasNickname) {
        data = await fetchPostByIdWithAuth(postId);
      } else {
        data = await fetchPostById(postId);
      }

      if (data) {
        const post = data;
        setPostContent(post);
        setLikeCount(post.likeCount || '0');
        setComments(post.comments || []);

        if (hasNickname) {
          const likeStatus = await isLiked(postId);
          setHasLiked(likeStatus);
        }
      } else {
        console.error('Post data is empty or undefined');
      }

    } catch (error) {
      console.error('Error fetching post:', error);
    }
  };

  useEffect(() => {
    console.log("게시글 ID:", postId, "닉네임:", nickname);
    if (nickname) setBlogNickname(nickname);
    if (postId) {
      setThisPostId(postId);
      fetchPostData(postId);
    }
  }, [postId, nickname]);

  useEffect(() => {
    if (thisPostId) {
      handleCheckPostOwner(thisPostId);
    }
  }, [thisPostId, refresh]);

  const nicknameModalConfirm = () => {
    navigateToCreateNickname();
  };

  const handleLogOut = async () => {
    const result = await combinedLogoutHandler(navigateToHome);
    if (result) {
      setLogOutModalOpen(true);
    }
  };

  const logOutModalConfirm = () => {
    setRefresh(prev => !prev);
    window.location.reload();
  };

  const handleHeaderClick = () => {
    if (blogNickname) {
      navigateToUserBlog(blogNickname);
    }
  };

  const handleLikeClick = useCallback(async () => {
    if (!hasNickname) {
      setLoginRequiredModalOpen(true);
      return;
    }

    try {
      await clickPostLike(thisPostId);
      fetchPostData(thisPostId);
    } catch (error) {
      console.error('Error liking post:', error);
    }
  }, [thisPostId, hasNickname]);

  const handleDeleteClick = useCallback(async () => {
    try {
      await deleteMyPost(thisPostId);
      setDeleteModalOpen(true);
    } catch (error) {
      console.error('Error deleting post:', error);
    }
  }, [thisPostId]);

  const deleteModalConfirm = () => {
    setDeleteModalOpen(false);
    navigateToHome();
  };

  const handleEditClick = () => {
    navigateToEditEditPost(thisPostId);
    
  };

  const handleAddComment = useCallback(async (content: string) => {
    if (!hasNickname) {
      setLoginRequiredModalOpen(true);
      return;
    }
    try {
      await addComment(thisPostId, content);
      fetchPostData(thisPostId);
    } catch (error) {
      console.error('Error adding comment:', error);
    }
  }, [thisPostId, hasNickname]);

  const handleEditComment = useCallback(async (commentId: number, content: string) => {
    console.log(`Edit Comment: ${commentId}, ${content}`);
    try {
      await editComment(commentId, content);
      // 댓글 수정 후 새로고침
      setCommentEditModalOpen(true);
      fetchPostData(thisPostId);
    } catch (error) {
      console.error('Error editing comment:', error);
    }
  }, [thisPostId]);

  const handleDeleteComment = useCallback(async (commentId: number) => {
    try {
      await deleteComment(commentId);
      setCommentDeleteModalOpen(true); // 댓글 삭제 성공 시 모달 열기
      fetchPostData(thisPostId); // 댓글 삭제 후 데이터 갱신
    } catch (error) {
      console.error('Error deleting comment:', error);
    }
  }, [thisPostId]);

  return (
    <HeaderProvider>
      <div className="max-w-screen-2xl mx-auto">
        <CommonHeaderSection
          title={`${blogNickname || ''}'s blog`}
          onHeaderClick={handleHeaderClick}
          hasNickname={hasNickname}
          nickname={myNickname}
          onLoginClick={navigateToLogin}
          onNicknameClick={toggleDropdown}
        />
        {dropdownOpen && (
          <CommonDropDownSection
            onMyPageClick={() => myNickname && navigateToUserBlog(myNickname)}
            onCreatePostClick={navigateToCreatePost}
            onLogoutClick={handleLogOut}
            onDeleteAccountClick={navigateToHome}
          />
        )}

        {postContent ? (
          <TestForm 
            title={postContent.title || ''} 
            nickname={postContent.nickname || ''} 
            date={postContent.createdAt || ''} 
            content={postContent.content || ''} 
            likeCount={likeCount} 
            isOwner={isOwner}
            onLikeClick={handleLikeClick}
            hasLiked={hasLiked}
            onDeleteClick={handleDeleteClick}
            onEditClick={handleEditClick} // 추가된 prop 전달
            comments={comments}
            onAddComment={handleAddComment}
            isUserLoggedIn={hasNickname}
            onLoginRequest={() => setLoginRequiredModalOpen(true)}
            onEditComment={handleEditComment}
            onDeleteComment={handleDeleteComment}
            myNickname={myNickname || ''}
          />
        ) : (
          <div>Loading...</div>
        )}

        <Modal
          isOpen={nicknameModalOpen}
          onClose={() => setNicknameModalOpen(false)}
          onConfirm={nicknameModalConfirm}
          message="닉네임 생성은 필수 입니다."
        />
        <Modal
          isOpen={logOutModalOpen}
          onClose={() => setLogOutModalOpen(false)}
          onConfirm={logOutModalConfirm}
          message="로그아웃 성공"
        />
        <Modal
          isOpen={loginRequiredModalOpen}
          onClose={() => setLoginRequiredModalOpen(false)}
          onConfirm={navigateToLogin}
          message="로그인 후 이용 가능합니다."
        />
        <Modal
          isOpen={deleteModalOpen}
          onClose={() => setDeleteModalOpen(false)}
          onConfirm={deleteModalConfirm}
          message="게시물이 삭제되었습니다."
        />
        <Modal
          isOpen={commentDeleteModalOpen}
          onClose={() => setCommentDeleteModalOpen(false)}
          onConfirm={() => setCommentDeleteModalOpen(false)}
          message="댓글이 성공적으로 삭제되었습니다."
        />
        <Modal
          isOpen={commentEditModalOpen}
          onClose={() => setCommentEditModalOpen(false)}
          onConfirm={() => setCommentEditModalOpen(false)}
          message="댓글이 성공적으로 수정되었습니다."
        />
      </div>
    </HeaderProvider>
  );
};

export default TestPostContainer;