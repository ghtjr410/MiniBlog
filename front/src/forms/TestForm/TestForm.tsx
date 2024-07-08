import React from 'react';
import TestTitleSection from 'sections/TestTitleSection/TestTitleSection';
import TestUserInfoSection from 'sections/TestUserInfoSection/TestUserInfoSection';
import TestContentSection from 'sections/TestContentSection/TestContentSection';
import TestCommentSection from 'sections/TestCommentSection/TestCommentSection'; // 댓글 섹션 추가

type Comment = {
  commentId: number;
  nickname: string;
  content: string;
  createdAt: string;
  isOwner: boolean;
};

type TestFormProps = {
  title: string;
  nickname: string;
  date: string;
  content: string;
  likeCount: string;
  isOwner: boolean;
  onLikeClick: () => void;
  hasLiked: boolean;
  onDeleteClick: () => void;
  onEditClick: () => void; // 새로운 prop 추가
  comments: Comment[]; // 댓글 데이터 추가
  onAddComment: (content: string) => void; // 댓글 추가 함수
  isUserLoggedIn: boolean;  // 로그인 상태를 확인하기 위한 prop
  onLoginRequest: () => void;  // 로그인 요청을 위한 prop
  onEditComment: (commentId: number, content: string) => void; // 댓글 수정 함수 추가
  onDeleteComment: (commentId: number) => void; // 댓글 삭제 함수 추가
  myNickname: string;
};

const TestForm: React.FC<TestFormProps> = ({
  title,
  nickname,
  date,
  content,
  likeCount,
  isOwner,
  onLikeClick,
  hasLiked,
  onDeleteClick,
  onEditClick,
  comments,
  onAddComment,
  isUserLoggedIn,
  onLoginRequest,
  onEditComment,
  onDeleteComment,
  myNickname
}) => {
  return (
    <div className="max-w-2xl mx-auto bg-gray-100 p-8 rounded-lg flex flex-col gap-4">
      <TestTitleSection text={title} />
      <TestUserInfoSection 
        nickname={nickname} 
        date={date} 
        likeCount={likeCount} 
        isOwner={isOwner} 
        onLikeClick={onLikeClick}
        hasLiked={hasLiked}
        onDeleteClick={onDeleteClick}
        onEditClick={onEditClick}
      />
      <TestContentSection content={content} />
      <TestCommentSection 
        comments={comments} 
        onAddComment={onAddComment} 
        isUserLoggedIn={isUserLoggedIn}
        onLoginRequest={onLoginRequest}
        onEditClick={onEditComment} // 수정 함수 전달
        onDeleteClick={onDeleteComment} // 삭제 함수 전달
        myNickname={myNickname}
      /> {/* 댓글 섹션 추가 */}
    </div>
  );
};

export default TestForm;