import React, { useState } from 'react';
import TestLabel from 'components/TestLabel/TestLabel';

type Comment = {
  commentId: number;
  nickname: string;
  content: string;
  createdAt: string;
  isOwner: boolean;
};

type TestCommentSectionProps = {
  comments: Comment[];
  onAddComment: (content: string) => void;
  isUserLoggedIn: boolean;
  onLoginRequest: () => void;
  myNickname: string;
  onEditClick: (commentId: number, content: string) => void;
  onDeleteClick: (commentId: number) => void;
};

const TestCommentSection: React.FC<TestCommentSectionProps> = ({
  comments,
  onAddComment,
  isUserLoggedIn,
  onLoginRequest,
  myNickname,
  onEditClick,
  onDeleteClick,
}) => {
  const [newComment, setNewComment] = useState('');
  const [editCommentId, setEditCommentId] = useState<number | null>(null);
  const [editCommentContent, setEditCommentContent] = useState('');

  const handleAddComment = () => {
    if (newComment.trim()) {
      onAddComment(newComment);
      setNewComment('');
    }
  };

  const handleEditComment = (commentId: number, content: string) => {
    setEditCommentId(commentId);
    setEditCommentContent(content);
  };

  const handleUpdateComment = () => {
    if (editCommentContent.trim() && editCommentId !== null) {
      onEditClick(editCommentId, editCommentContent);
      setEditCommentId(null);
      setEditCommentContent('');
    }
  };

  const handleCancelEdit = () => {
    setEditCommentId(null);
    setEditCommentContent('');
  };

  return (
    <div className="w-full flex flex-col p-4 rounded-md bg-white shadow-md">
      <div className="w-full relative flex items-center border rounded-md">
        <div className="w-[92%] flex">
          <textarea
            className="flex-1 w-full p-2 resize-none min-h-[5rem] h-[3rem] max-h-[3rem] overflow-y-auto outline-none"
            value={newComment}
           onChange={(e) => setNewComment(e.target.value)}
            placeholder="댓글을 입력하세요..."
            rows={1}
            onKeyPress={(e) => {
              if (e.key === 'Enter' && !e.shiftKey) {
                e.preventDefault();
                handleAddComment();
              }
            }}
            onInput={(e) => {
              e.currentTarget.style.height = 'auto';
              e.currentTarget.style.height = `${e.currentTarget.scrollHeight}px`;
          }}
            />
        </div>
        <button
          className="absolute top-0 right-0 py-7 px-3"
          onClick={isUserLoggedIn ? handleAddComment : onLoginRequest}
        >
          댓글 추가
        </button>
      </div>
      <div>
        {comments.map((comment) => (
          <div key={comment.commentId} className="py-2 border-b last:border-none">
            {editCommentId === comment.commentId ? (
              <div className="relative flex w-full border rounded-md">
                <textarea
                  className="w-[93%] p-2 resize-none outline-none"
                  value={editCommentContent}
                  onChange={(e) => setEditCommentContent(e.target.value)}
                />
                <div className="flex flex-col">
                  <button
                    className="px-4 py-2 border-b"
                    onClick={handleUpdateComment}
                  >
                    수정
                  </button>
                  <button
                    className="px-5 py-2"
                    onClick={handleCancelEdit}
                  >
                    취소
                  </button>
                </div>
              </div>
            ) : (
              <div className="relative py-3 flex flex-col gap-2">
                <div className="flex items-center gap-4">
                  <TestLabel text={comment.nickname} />
                  <span className="text-xs text-gray-500">{new Date(comment.createdAt).toLocaleString()}</span>
                </div>
                <p className="whitespace-pre-wrap break-all">{comment.content}</p>
                {comment.nickname === myNickname && (
                  <div className="absolute flex gap-2 top-0 right-0">
                    <button
                      className="px-2 py-1 text-xs text-gray-500 border-r hover:text-black"
                      onClick={() => handleEditComment(comment.commentId, comment.content)}
                    >
                      수정
                    </button>
                    <button
                      className="pr-2 py-1 text-xs text-gray-500 hover:text-black"
                      onClick={() => onDeleteClick(comment.commentId)}
                    >
                      삭제
                    </button>
                  </div>
                )}
              </div>
            )}
          </div>
        ))}
      </div>
    </div>
  );
};

export default TestCommentSection;
