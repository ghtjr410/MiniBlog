

///////////////////////////////////////////////////////////
// src/containers/TestContainer/TestCommonLogicContainer.tsx
import React, { useEffect, useState, useRef, useCallback } from 'react';
import Modal from 'components/Modal/Modal';
import CommonHeaderSection from 'sections/CommonHeaderSection/CommonHeaderSection';
import CommonDropDownSection from 'sections/CommonDropDownSection/CommonDropDownSection';
import { combinedLogoutHandler } from 'services/LogOutService/logOutService';
import useNavigateHelper from 'utils/NavigationUtil/navigationUtil';
import { deleteAccount, getAllPosts } from 'services/Auth/authService';
import MainCardForm from 'forms/MainCardForm/MainCardForm';
import { HeaderProvider, useHeader } from 'services/HeaderService/HeaderService';
import { useAuth } from 'hooks/useAuthHook';

const TestMainContainer: React.FC = () => {
  const { navigateToCreateNickname, navigateToLogin, navigateToUserBlog, navigateToCreatePost, navigateToHome, navigateToPost } = useNavigateHelper();
  const { dropdownOpen, toggleDropdown } = useHeader();
  const { hasNickname, myNickname, nicknameModalOpen, setNicknameModalOpen } = useAuth();
  const [logOutModalOpen, setLogOutModalOpen] = useState(false); // 로그아웃 모달 상태
  const [refresh, setRefresh] = useState(false); // 리프레시 상태
  const [posts, setPosts] = useState<any[]>([]); // 게시글 데이터를 저장할 상태
  const [page, setPage] = useState(0); // 현재 페이지 번호
  const [isLoading, setIsLoading] = useState(false); // 로딩 상태 추가
  const [hasMore, setHasMore] = useState(true); // 더 가져올 데이터가 있는지 확인
  const observerRef = useRef<IntersectionObserver | null>(null); // 옵저버 레퍼런스
  const loadMoreRef = useRef<HTMLDivElement | null>(null); // 더 가져오기 레퍼런스
  const [deleteAccountModalOpen, setDeleteAccountModalOpen] = useState(false);

  type PostData = { // allpost 데이터 타입
    postList?: any[];
  };

  const isValidPostList = (data: PostData) => { // allpost 데이터 더 있는지 없는지
    return data && data.postList && Array.isArray(data.postList) && data.postList.length > 0;
  };

  const fetchPosts = useCallback(async (page: number) => {
    setIsLoading(true); // 로딩 시작
    const data = await getAllPosts(page); // 최신 게시글 40개 요청
    if (isValidPostList(data)) { // 데이터가 있다면
      setPosts(prevPosts => (page === 0 ? data.postList : [...prevPosts, ...data.postList]));
    } else { // 데이터가 없다면
      setHasMore(false);
      console.log('No more data available.');
    }
    setIsLoading(false); // 로딩 끝
  }, []);

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

  useEffect(() => {
    console.log("초기 작업하는 곳");
    fetchPosts(0);
  }, [refresh, fetchPosts]);

  const handleHeaderClick = () => {
    navigateToHome();
  };

  useEffect(() => {
    if (isLoading || !hasMore) return;

    const observer = new IntersectionObserver(entries => {
      if (entries[0].isIntersecting) {
        setPage(prevPage => prevPage + 1);
      }
    });

    if (loadMoreRef.current) {
      observer.observe(loadMoreRef.current);
    }
    observerRef.current = observer;

    return () => {
      if (observerRef.current) {
        observerRef.current.disconnect();
      }
    };
  }, [isLoading, hasMore]);

  useEffect(() => {
    if (page > 0) {
      fetchPosts(page);
    }
  }, [page, fetchPosts]);

  const extractFirstImageUrl = (content: string) => {
    const imgTagMatch = content.match(/<img[^>]+src="([^">]+)"/);
    return imgTagMatch ? imgTagMatch[1] : '';
  };

  const handlePostClick = (postId: number, nickname: string) => {
    console.log(`게시글 ID : ${postId} / 유저 닉네임 : ${nickname}`);
    navigateToPost(postId, nickname);
  };

  const handleNicknameClick = (nickname: string) => {
    console.log("닉네임:", nickname);
    navigateToUserBlog(nickname);
  };

  const handleDeleteAccountClick = () => {
    console.log("회원탈퇴 클릭");
    setDeleteAccountModalOpen(true);
  };
  const deleteAccountModalConfirm = async (inputNickname?: string) => {
    if (inputNickname) {
      console.log("회원탈퇴 진행: " + inputNickname);
      await deleteAccount(inputNickname);
      window.location.reload();
    } else {
      console.log("닉네임이 입력되지 않았습니다.");
    }
  };

  return (
    <HeaderProvider>
      <div className="max-w-screen-2xl mx-auto">
        <CommonHeaderSection
          title={"MiniBlog"}
          hasNickname={hasNickname}
          nickname={myNickname}
          onHeaderClick={handleHeaderClick}
          onLoginClick={navigateToLogin}
          onNicknameClick={toggleDropdown}
        />
        {dropdownOpen && (
          <CommonDropDownSection
            onMyPageClick={() => myNickname && navigateToUserBlog(myNickname)}
            onCreatePostClick={navigateToCreatePost}
            onLogoutClick={handleLogOut}
            onDeleteAccountClick={handleDeleteAccountClick}
          />
        )}

        <div className="mt-6 grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 gap-6">
          {posts.map(post => (
            <MainCardForm
              key={post.postId}
              postId={post.postId}
              nickname={post.nickname}
              title={post.title}
              content={post.content.replace(/(<([^>]+)>)/gi, '')} // 태그 제거
              createdAt={post.createdAt}
              commentsCount={post.comments.length}
              likeCount={post.likeCount}
              imageUrl={extractFirstImageUrl(post.content)} // 첫 번째 이미지 URL 추출
              onPostClick={handlePostClick}
              onNicknameClick={handleNicknameClick}
            />
          ))}
        </div>
        <div ref={loadMoreRef} className="h-10"></div>

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
          isOpen={deleteAccountModalOpen}
          onClose={() => setDeleteAccountModalOpen(false)}
          onConfirm={deleteAccountModalConfirm}
          message="회원탈퇴를 위해 닉네임을 입력해주세요."
          showInput={true}
        />
      </div>
    </HeaderProvider>
  );
};

export default TestMainContainer;
